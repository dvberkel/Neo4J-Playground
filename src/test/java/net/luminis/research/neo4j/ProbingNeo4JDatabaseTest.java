package net.luminis.research.neo4j;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class ProbingNeo4JDatabaseTest {
	private static final String GRAPH_DB_PATH = "src/test/resources/graphdbs/starting";
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		graphDb = new EmbeddedGraphDatabase(GRAPH_DB_PATH);
		registerShutdownHook(graphDb);
	}

	@Test
	public void graphDbShouldBeInitialized() {
		assertNotNull(graphDb);
	}

	@AfterClass
	public static void setupDatabase() {
		graphDb.shutdown();
	}

	private static void registerShutdownHook(final GraphDatabaseService aGraphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				aGraphDb.shutdown();
			}
		});
	}
}