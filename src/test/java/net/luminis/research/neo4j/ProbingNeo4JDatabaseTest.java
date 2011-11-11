package net.luminis.research.neo4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class ProbingNeo4JDatabaseTest {
	private static final String GRAPH_DB_PATH = "src/test/resources/graphdbs/starting";
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		deleteDir(GRAPH_DB_PATH);
		graphDb = new EmbeddedGraphDatabase(GRAPH_DB_PATH);
		registerShutdownHook(graphDb);
		createNode(graphDb);
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

	@Test(expected=NotInTransactionException.class)
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
		SUCCESOR;
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
		int nodeCount = 0;

		for (@SuppressWarnings("unused") Node node : graphDb.getAllNodes()) {
			nodeCount++;
		}

		assertTrue(nodeCount > 0);
	}

	@Test
	public void removeNodesWithARelation() {
		Transaction tx = graphDb.beginTx();
		try {
			for (Node node : graphDb.getAllNodes()) {
				for (Relationship relationship : node.getRelationships()) {
					relationship.delete();
				}
				node.delete();
			}
			tx.success();
		} finally {
			tx.finish();
		}
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

	private static void createNode(GraphDatabaseService aGraphDb) {
		Transaction tx = aGraphDb.beginTx();
		try {
			aGraphDb.createNode();
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