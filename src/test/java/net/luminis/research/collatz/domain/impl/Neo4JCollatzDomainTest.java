package net.luminis.research.collatz.domain.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.neo4j.service.GrapDbModificationService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class Neo4JCollatzDomainTest {
	private static final String GRAPH_DB_PATH = "src/test/resources/graphdbs/collatz";
	private static GraphDatabaseService graphDb;

	@BeforeClass
	public static void createGraphDatabase() {
		deleteDir(GRAPH_DB_PATH);
		graphDb = new EmbeddedGraphDatabase(GRAPH_DB_PATH);
		registerShutdownHook(graphDb);
	}

	private Neo4JCollatzDomain domain;

	@Before
	public void createCollatzDomain() {
		domain = new Neo4JCollatzDomain(graphDb);
	}

	@Test
	public void shouldExist() {
		assertNotNull(Neo4JCollatzDomain.class);
	}

	@Test
	public void shouldBeInstanceOfCollatzDomain() {
		assertTrue(domain instanceof CollatzDomain);
	}

	@Test
	public void shouldBeInstanceOfGraphDbModificationService() {
		assertTrue(domain instanceof GrapDbModificationService);
	}

	@Test
	public void shouldReturnGraphDatabaseService() {
		assertSame(graphDb, domain.getGraphDb());
	}

	@Test
	public void shouldAcceptImages() {
		domain.imageOf(1, 4);

		assertTrue(domain.elements().containsAll(Arrays.asList(new Integer[] { 1, 4 })));
	}

	@Test
	public void shouldOnlyCreateElementsOnce() {
		domain.imageOf(1, 4);
		domain.imageOf(1, 4);
		List<Integer> elements = domain.elements();

		elements.removeAll(Arrays.asList(new Integer[] { 4 }));

		assertEquals(Integer.valueOf(1), Integer.valueOf(elements.size()));
	}

	@Test
	public void shouldReturnPathOfElement() {
		domain.imageOf(1, 4);
		domain.imageOf(2, 1);
		domain.imageOf(4, 2);

		List<Integer> path = domain.pathOf(4);

		assertEquals(Arrays.asList(new Integer[] { 4, 2, 1 }), path);
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
