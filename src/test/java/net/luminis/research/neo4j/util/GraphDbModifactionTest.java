package net.luminis.research.neo4j.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;

public class GraphDbModifactionTest {

	@Test
	public void graphDbModificationShouldExist() {
		assertNotNull(GraphDbModification.class);
	}

	@Test
	public void graphDbModificationShouldHaveAPerformMethod() {
		final PerformSpy spy = new PerformSpy();
		GraphDbModification modification = new GraphDbModification() {
			@Override
			public void perform(GraphDatabaseService aGraphDb) {
				spy.called = true;
			}
		};

		modification.perform(null);

		assertTrue(spy.called);
	}

	class PerformSpy {
		public boolean called = false;
	}
}
