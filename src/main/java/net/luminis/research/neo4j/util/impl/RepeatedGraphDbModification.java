package net.luminis.research.neo4j.util.impl;

import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.neo4j.graphdb.GraphDatabaseService;

public class RepeatedGraphDbModification implements GraphDbModification {
	private final int repeatCount;
	private final GraphDbModification modification;

	public RepeatedGraphDbModification(int repeatCount, GraphDbModification modification) {
		this.repeatCount = repeatCount;
		this.modification = modification;
	}

	@Override
	public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
		for (int index = 0; index < repeatCount; index++) {
			modification.perform(aGraphDb);
		}
	}

}
