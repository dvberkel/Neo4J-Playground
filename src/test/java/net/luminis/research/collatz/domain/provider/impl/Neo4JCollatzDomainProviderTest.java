package net.luminis.research.collatz.domain.provider.impl;

import java.io.File;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class Neo4JCollatzDomainProviderTest {
	private static final String GRAPH_DB_PATH = "src/test/resources/graphdbs/provider";
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		deleteDir(GRAPH_DB_PATH);
		graphDb = new EmbeddedGraphDatabase(GRAPH_DB_PATH);
		registerShutdownHook(graphDb);
	}

	private Neo4JCollatzDomainProvider provider;

	@Before
	public void createNeo4JCollatzDomainProvider() {
		provider = new Neo4JCollatzDomainProvider(graphDb);
	}

	@Test
	public void shouldExist() {
		assertNotNull(Neo4JCollatzDomainProvider.class);
	}

	@Test
	public void shouldImplementCollatzDomainProvider() {
		assertTrue(provider instanceof CollatzDomainProvider);
	}

	@Test
	public void shouldReturnCollatzDomainWhichAreIdentical() {
		CollatzDomain domainA = provider.getInstance();
		CollatzDomain domainB = provider.getInstance();

		assertSame(domainA, domainB);
	}

	@AfterClass
	public static void tearDownDatabase() {
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
