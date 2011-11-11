package net.luminis.research.neo4j.service.impl;

import net.luminis.research.neo4j.service.GrapDbModificationService;
import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

public abstract class AbstractGraphDbModificationService implements GrapDbModificationService {

	@Override
	public void performInTransaction(GraphDbModification modification) {
		GraphDatabaseService graphDb = getGraphDb();
		Transaction tx = graphDb.beginTx();
		try {
			modification.perform(graphDb);
			tx.success();
			afterSuccesfullModification();
		} catch (GrapDbModifcationException e) {
			tx.failure();
			afterModificationException();
		} finally {
			tx.finish();
			afterModificationAttempt();
		}
	}

	public abstract GraphDatabaseService getGraphDb();

	public void afterModificationException() {
		// Do nothing
	}

	public void afterSuccesfullModification() {
		// Do nothing
	}

	void afterModificationAttempt() {
		// Do nothing

	}


}
