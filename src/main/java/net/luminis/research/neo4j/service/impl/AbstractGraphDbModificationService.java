package net.luminis.research.neo4j.service.impl;

import net.luminis.research.neo4j.service.GrapDbModificationService;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.neo4j.graphdb.GraphDatabaseService;

public abstract class AbstractGraphDbModificationService implements GrapDbModificationService {

	@Override
	public void performInTransaction(GraphDbModification modification) {
		afterModificationException();
		afterSuccesfullModification();
	}

	public abstract GraphDatabaseService getGraphDb();

	public void afterModificationException() {
		// TODO Auto-generated method stub
	}

	public void afterSuccesfullModification() {
		// Do nothing
	}


}
