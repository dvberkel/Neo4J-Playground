package net.luminis.research.neo4j.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.junit.Test;

public class GraphDbModificationServiceTest {

	@Test
	public void shouldExist() {
		assertNotNull(GrapDbModificationService.class);
	}

	@Test
	public void shouldPerformAGraphDbModificationInATransaction() {
		final PerformInTransactionSpy spy = new PerformInTransactionSpy();
		GrapDbModificationService service = new GrapDbModificationService() {
			@Override
			public void performInTransaction(GraphDbModification modification) {
				spy.called = true;

			}
		};
		GraphDbModification modification = null;

		service.performInTransaction(modification);

		assertTrue(spy.called);
	}

	class PerformInTransactionSpy {
		public boolean called = false;
	}

}
