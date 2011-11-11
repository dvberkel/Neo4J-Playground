package net.luminis.research.neo4j.util.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.luminis.research.neo4j.util.GrapDbModifcationException;
import net.luminis.research.neo4j.util.GraphDbModification;

import org.neo4j.graphdb.GraphDatabaseService;

public class CompoundGraphDbModification implements GraphDbModification{
	private final List<GraphDbModification> modifications = new ArrayList<GraphDbModification>();

	public CompoundGraphDbModification(GraphDbModification... graphDbModification) {
		modifications.addAll(Arrays.asList(graphDbModification));
	}

	@Override
	public void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException {
		for (GraphDbModification modification : modifications) {
			modification.perform(aGraphDb);
		}
	}

}
