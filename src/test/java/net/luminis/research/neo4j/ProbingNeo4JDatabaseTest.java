package net.luminis.research.neo4j;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProbingNeo4JDatabaseTest {
	private static final String GRAPH_DB_PATH = "src/test/resources/graphdbs/starting";
	private static final String NODE_INDEX_NAME = "specialNodes";
	private static final String IDENTIFIABLE_KEY = "find me";
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		deleteDir(GRAPH_DB_PATH);
		graphDb = new EmbeddedGraphDatabase(GRAPH_DB_PATH);
		registerShutdownHook(graphDb);
		createNodeAndIndex(graphDb);
		createRelationship(graphDb);
	}

	@Test
	public void graphDbShouldBeInitialized() {
		assertNotNull(graphDb);
	}

	@Test
	public void graphDbModificationsAreDemarcatedByATransaction() {
		Transaction tx = graphDb.beginTx();
		try {
			tx.success();
		} finally {
			tx.finish();
		}
	}

	@Test(expected = NotInTransactionException.class)
	public void nodeCreationOutsideTransactionFail() {
		graphDb.createNode();
	}

	@Test
	public void nodesCanHaveProperties() {
		String key = "message";
		String expectedMessage = "Hello World!";

		Transaction tx = graphDb.beginTx();
		try {
			Node node = graphDb.createNode();
			node.setProperty(key, expectedMessage);
			assertEquals(expectedMessage, node.getProperty(key));
			tx.success();
		} finally {
			tx.finish();
		}
	}

	private static enum RelationType implements RelationshipType {
		SUCCESOR, ON;
	}

	@Test
	public void nodesCanBeRelatedByARelationship() {
		Transaction tx = graphDb.beginTx();
		try {
			Node zero = graphDb.createNode();
			Node one = graphDb.createNode();

			zero.createRelationshipTo(one, RelationType.SUCCESOR);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	@Test
	public void countNodes() {
		assertTrue(countNodesOf(graphDb) > 0);
	}

	@Test
	public void removeNodesWithARelation() {
		int originalNodeCount = countNodesOf(graphDb);
		int removedNodeCount = 0;

		Transaction tx = graphDb.beginTx();
		try {
			Set<Node> toRemove = new HashSet<Node>();
			for (Node node : graphDb.getAllNodes()) {
				for (Relationship relationship : node.getRelationships(RelationType.ON)) {
					toRemove.add(relationship.getStartNode());
					toRemove.add(relationship.getEndNode());
					relationship.delete();
				}
			}
			for (Node node : toRemove) {
				node.delete();
				removedNodeCount++;
			}
			tx.success();
		} finally {
			tx.finish();
		}

		int finalNodeCount = countNodesOf(graphDb);
		assertEquals(Integer.valueOf(originalNodeCount), Integer.valueOf(finalNodeCount + removedNodeCount));
	}

	private int countNodesOf(GraphDatabaseService aGraphDb) {
		int nodeCount = 0;
		for (@SuppressWarnings("unused")
		Node node : aGraphDb.getAllNodes()) {
			nodeCount++;
		}
		return nodeCount;
	}

	@Test
	public void doesIndexExist() {
		IndexManager manager = graphDb.index();

		assertTrue(manager.existsForNodes(NODE_INDEX_NAME));
	}

	@Test
	public void findNodeWithAnIndex() {
		Index<Node> index = graphDb.index().forNodes(NODE_INDEX_NAME);

		IndexHits<Node> hits = index.get(IDENTIFIABLE_KEY, Boolean.TRUE);

		assertNotNull(hits.getSingle());
	}

	@AfterClass
	public static void setupDatabase() {
		graphDb.shutdown();
		deleteDir(GRAPH_DB_PATH);
	}

	private static void registerShutdownHook(final GraphDatabaseService aGraphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				aGraphDb.shutdown();
			}
		});
	}

	private static void createNodeAndIndex(GraphDatabaseService aGraphDb) {
		Transaction tx = aGraphDb.beginTx();
		try {
			Node special = createNode(aGraphDb);
			Index<Node> index = createIndex(special);
			index.add(special, IDENTIFIABLE_KEY, Boolean.TRUE);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	private static Index<Node> createIndex(Node special) {
		Index<Node> nodeIndex = graphDb.index().forNodes(NODE_INDEX_NAME);
		return nodeIndex;
	}

	private static Node createNode(GraphDatabaseService aGraphDb) {
		Node special = aGraphDb.createNode();
		special.setProperty(IDENTIFIABLE_KEY, Boolean.TRUE);
		return special;
	}

	private static void createRelationship(GraphDatabaseService aGraphDb) {
		Transaction tx = aGraphDb.beginTx();
		try {
			Node point = aGraphDb.createNode();
			Node plane = aGraphDb.createNode();

			point.createRelationshipTo(plane, RelationType.ON);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	private static boolean deleteDir(final String path) {
		return deleteDir(new File(path));
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (String fileName : children) {
				boolean success = deleteDir(new File(dir, fileName));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}
}