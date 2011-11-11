package net.luminis.research.neo4j.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.luminis.research.neo4j.service.GrapDbModificationService;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;

public class AbstractGraphDbModificationServiceTest {

	@Test
	public void shouldExist() {
		assertNotNull(AbstractGraphDbModificationService.class);
	}

	@Test
	public void shouldImplementGraphDbModificationService() {
		AbstractGraphDbModificationService service = new AbstractGraphDbModificationService(){
			@Override
			public GraphDatabaseService getGraphDb() {
				return null;
			}};
		assertTrue(service instanceof GrapDbModificationService);
	}

	@Test
	public void shouldProvideAMeansToRetrieveAGraphDb() {
		AbstractGraphDbModificationService service = new AbstractGraphDbModificationService() {

			@Override
			public GraphDatabaseService getGraphDb() {
				return null;
			}};

		assertNull(service.getGraphDb());
	}

	@Test
	public void afterSuccesfullModificationCallbackShouldBeProvided() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new AbstractGraphDbModificationService() {

			@Override
			public GraphDatabaseService getGraphDb() {
				return null;
			}

			@Override
			public void afterSuccesfullModification() {
				spy.called = true;
			}
		};

		service.performInTransaction(new NullModification());

		assertTrue(spy.called);
	}

	@Test
	public void afterModificationExceptionCallbackShouldBeProvided() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new AbstractGraphDbModificationService() {

			@Override
			public GraphDatabaseService getGraphDb() {
				return null;
			}

			@Override
			public void afterModificationException() {
				spy.called = true;
			}
		};

		service.performInTransaction(new ExceptionModification());

		assertTrue(spy.called);
	}

	class CallbackSpy {
		public boolean called = false;
	}

	class NullModification implements GraphDbModification {

		@Override
		public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
			// Do Nothing
		}
	}

	class ExceptionModification implements GraphDbModification {

		@Override
		public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
			throw new GrapDbModifcationException();
		}

	}
}
