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
	public void graphDbModificationShouldHaveAPerformMethod() throws GrapDbModifcationException {
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

	@Test(expected=GrapDbModifcationException.class)
	public void graphDbModificationIsAllowedToThrowException() throws GrapDbModifcationException {
		GraphDbModification modification = new GraphDbModification() {

			@Override
			public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
				throw new GrapDbModifcationException();

			}
		};

		modification.perform(null);
	}

	class PerformSpy {
		public boolean called = false;
	}
}
