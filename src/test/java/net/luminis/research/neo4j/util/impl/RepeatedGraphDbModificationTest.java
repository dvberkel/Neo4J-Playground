package net.luminis.research.neo4j.util.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;

public class RepeatedGraphDbModificationTest {

	@Test
	public void shouldExist() {
		assertNotNull(RepeatedGraphDbModification.class);
	}

	@Test
	public void shouldBeAnInstanceOfGraphDbModification() {
		RepeatedGraphDbModification modification = new RepeatedGraphDbModification(0, null);
		assertTrue(modification instanceof GraphDbModification);
	}

	@Test
	public void performShouldPerformModificationANumberOfTimes() throws GrapDbModifcationException {
		Integer expectedNumberOfTimes = 3;
		final CounterSpy spy = new CounterSpy();
		GraphDbModification modification = new RepeatedGraphDbModification(expectedNumberOfTimes, new GraphDbModification() {

			@Override
			public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
				spy.numberOfCalls++;
			}
		});

		modification.perform(null);

		assertEquals(expectedNumberOfTimes, spy.numberOfCalls);
	}

	class CounterSpy {
		public Integer numberOfCalls = 0;
	}

}
