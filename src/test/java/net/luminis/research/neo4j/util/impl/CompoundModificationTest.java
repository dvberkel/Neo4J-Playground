package net.luminis.research.neo4j.util.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;

public class CompoundModificationTest {

	@Test
	public void shouldExist() {
		assertNotNull(CompoundGraphDbModification.class);
	}

	@Test
	public void shouldBeAnInstanceOfGraphDbModification() {
		CompoundGraphDbModification modification = new CompoundGraphDbModification();
		assertTrue(modification instanceof GraphDbModification);
	}

	@Test
	public void shouldPerformModificationsSequenctially() throws GrapDbModifcationException {
		final SequentialSpy spy = new SequentialSpy();
		GraphDbModification modification = new CompoundGraphDbModification(
			new GraphDbModification(){
				@Override
				public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
					spy.firstCalled = true;
				}
			}, new GraphDbModification() {
				@Override
				public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
					spy.secondCalled = true;
					throw new GrapDbModifcationException();
				}
			}, new GraphDbModification() {
				@Override
				public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
					spy.lastCalled = true;
				}
			}
		);

		modification.perform(null);

		assertTrue(spy.firstCalled);
		assertTrue(spy.secondCalled);
		assertFalse(spy.lastCalled);
	}

	class SequentialSpy {
		public boolean firstCalled  = false;
		public boolean secondCalled = false;
		public boolean lastCalled = false;
	}

}
