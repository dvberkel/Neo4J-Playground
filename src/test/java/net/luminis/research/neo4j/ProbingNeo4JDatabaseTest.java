package net.luminis.research.neo4j;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
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