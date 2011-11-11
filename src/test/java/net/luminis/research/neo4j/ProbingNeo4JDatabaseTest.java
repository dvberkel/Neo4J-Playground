package net.luminis.research.neo4j;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class ProbingNeo4JDatabaseTest {
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		graphDb = new EmbeddedGraphDatabase("src/test/resources/graphdbs/starting");
	}

	@Test
	public void graphDbShouldBeInitialized() {
		assertNotNull(graphDb);
	}

	@AfterClass
	public static void setupDatabase() {
		graphDb.shutdown();
	}

}