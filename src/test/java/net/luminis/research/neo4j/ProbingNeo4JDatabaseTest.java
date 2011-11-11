package net.luminis.research.neo4j;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class ProbingNeo4JDatabaseTest {

	@Test
	public void setupDatabase() {
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("src/test/resources/graphdbs/starting");
		graphDb.shutdown();
	}

}