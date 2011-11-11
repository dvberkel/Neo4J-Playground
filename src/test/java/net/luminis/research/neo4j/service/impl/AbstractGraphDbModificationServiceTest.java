package net.luminis.research.neo4j.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.luminis.research.neo4j.service.GrapDbModificationService;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

public class AbstractGraphDbModificationServiceTest {
	private Transaction transaction;
	private GraphDatabaseService graphDb;

	@Before
	public void createMockGraphDatabase() {
		transaction = mock(Transaction.class);
		graphDb = mock(GraphDatabaseService.class);
		when(graphDb.beginTx()).thenReturn(transaction);
	}

	@Test
	public void mocksShouldBeInPlace() {
		assertNotNull(transaction);
		assertNotNull(graphDb);
		assertSame(transaction, graphDb.beginTx());
	}

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
		AbstractGraphDbModificationService service = new SpiedUponGraphDbModificationService(graphDb, spy);

		service.performInTransaction(new NullModification());

		assertEquals("BmSA", spy.toString());
	}

	@Test
	public void afterModificationExceptionCallbackShouldBeProvided() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new SpiedUponGraphDbModificationService(graphDb, spy);

		service.performInTransaction(new ExceptionModification());

		assertEquals("BMsA", spy.toString());
	}

	@Test
	public void beforModificationExceptionCallbackShouldBeProvided() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new SpiedUponGraphDbModificationService(graphDb, spy);

		service.performInTransaction(new ExceptionModification());

		assertEquals("BMsA", spy.toString());
	}

	@Test
	public void behaviourOfSuccesfullPerformInTransaction() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new SpiedUponGraphDbModificationService(graphDb, spy);

		service.performInTransaction(new NullModification());

		verify(graphDb).beginTx();
		verify(transaction).success();
		verify(transaction).finish();
	}

	@Test
	public void behaviourOfUnsuccesfullPerformInTransaction() {
		final CallbackSpy spy = new CallbackSpy();
		AbstractGraphDbModificationService service = new SpiedUponGraphDbModificationService(graphDb, spy);

		service.performInTransaction(new ExceptionModification());

		verify(graphDb).beginTx();
		verify(transaction).failure();
		verify(transaction).finish();
	}

	class CallbackSpy {
		public boolean beforeModificationAttemptCalled = false;
		public boolean afterModificationExceptionCalled = false;
		public boolean afterSuccesfulModificationCalled = false;
		public boolean afterModificationAttemptCalled = false;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(beforeModificationAttemptCalled ? 'B' : 'b');
			builder.append(afterModificationExceptionCalled ? 'M': 'm');
			builder.append(afterSuccesfulModificationCalled ? 'S': 's');
			builder.append(afterModificationAttemptCalled ? 'A': 'a');
			return builder.toString();
		}
	}

	class SpiedUponGraphDbModificationService extends AbstractGraphDbModificationService {
		private final GraphDatabaseService graphDatabase;
		private final CallbackSpy spy;

		public SpiedUponGraphDbModificationService(GraphDatabaseService graphDatabase, CallbackSpy spy) {
			this.graphDatabase = graphDatabase;
			this.spy = spy;
		}

		@Override
		public GraphDatabaseService getGraphDb() {
			return graphDatabase;
		}

		@Override
		public void beforeModificationAttempt() {
			spy.beforeModificationAttemptCalled = true;
		}

		@Override
		public void afterModificationException() {
			spy.afterModificationExceptionCalled = true;
		}

		@Override
		public void afterSuccesfullModification() {
			spy.afterSuccesfulModificationCalled = true;
		}

		@Override void afterModificationAttempt() {
			spy.afterModificationAttemptCalled = true;
		}
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
