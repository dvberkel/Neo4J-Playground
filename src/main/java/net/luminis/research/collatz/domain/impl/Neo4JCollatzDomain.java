package net.luminis.research.collatz.domain.impl;

import java.util.ArrayList;
import java.util.List;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.neo4j.service.impl.AbstractGraphDbModificationService;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;
import net.luminis.research.neo4j.util.impl.CompoundGraphDbModification;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.Index;

public class Neo4JCollatzDomain extends AbstractGraphDbModificationService implements CollatzDomain {
	private static final String INTEGER_VALUE_KEY = "integer-value";
	private final GraphDatabaseService graphDb;
	private static final String INTEGER_NODE_INDEX_NAME = "integer-nodes-index";

	public Neo4JCollatzDomain(GraphDatabaseService graphDb) {
		this.graphDb = graphDb;
	}

	@Override
	public List<Integer> elements() {
		ArrayList<Integer> elements = new ArrayList<Integer>();
		for (Node node : graphDb.getAllNodes()) {
			if (node.hasProperty(INTEGER_VALUE_KEY)) {
				elements.add((Integer) node.getProperty(INTEGER_VALUE_KEY));
			}
		}
		return elements;
	}

	@Override
	public void imageOf(Integer element, Integer image) {
		GraphDbModification modification = new CompoundGraphDbModification(new CreateIntegerNode(element),
			new CreateIntegerNode(image), new CreateSuccessor(element, image));
		performInTransaction(modification);
	}

	@Override
	public List<Integer> pathOf(Integer element) {
		Index<Node> index = graphDb.index().forNodes(INTEGER_NODE_INDEX_NAME);
		Node currentNode = index.get(INTEGER_VALUE_KEY, element).getSingle();
		Node finish = index.get(INTEGER_VALUE_KEY, Integer.valueOf(1)).getSingle();
		List<Integer> path = new ArrayList<Integer>();
		while (!currentNode.equals(finish)) {
			path.add((Integer) currentNode.getProperty(INTEGER_VALUE_KEY));
			for (Relationship relationship : currentNode.getRelationships(Direction.OUTGOING, Relation.SUCCESSORTO)) {
				currentNode = relationship.getEndNode();
			}
		}
		path.add((Integer) finish.getProperty(INTEGER_VALUE_KEY));
		return path;
	}

	@Override
	public GraphDatabaseService getGraphDb() {
		return graphDb;
	}

	private Index<Node> getIntegerNodeIndex() {
		if (!graphDb.index().existsForNodes(INTEGER_NODE_INDEX_NAME)) {
			performInTransaction(new CreateIntegerNodeIndex());
		}
		return graphDb.index().forNodes(INTEGER_NODE_INDEX_NAME);

	}

	enum Relation implements RelationshipType {
		SUCCESSORTO
	}

	class CreateIntegerNode implements GraphDbModification {

		private final Integer value;

		public CreateIntegerNode(Integer value) {
			this.value = value;
		}

		@Override
		public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
			Index<Node> index = getIntegerNodeIndex();
			if (index.get(INTEGER_VALUE_KEY, value).getSingle() == null) {
				Node node = aGraphDb.createNode();
				node.setProperty(INTEGER_VALUE_KEY, value);
				index.add(node, INTEGER_VALUE_KEY, value);
			}
		}

	}

	class CreateSuccessor implements GraphDbModification {

		private final Integer element;
		private final Integer image;

		public CreateSuccessor(Integer element, Integer image) {
			this.element = element;
			this.image = image;
		}

		@Override
		public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
			Index<Node> index = getIntegerNodeIndex();
			Node elementNode = index.get(INTEGER_VALUE_KEY, element).getSingle();
			if (!elementNode.hasRelationship(Direction.OUTGOING, Relation.SUCCESSORTO)) {
				Node imageNode = index.get(INTEGER_VALUE_KEY, image).getSingle();
				elementNode.createRelationshipTo(imageNode, Relation.SUCCESSORTO);
			}
		}

	}

	class CreateIntegerNodeIndex implements GraphDbModification {

		@Override
		public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
			aGraphDb.index().forNodes(INTEGER_NODE_INDEX_NAME);
		}
	}
}
