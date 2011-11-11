package net.luminis.research.neo4j.util;

import org.neo4j.graphdb.GraphDatabaseService;

public interface GraphDbModification {

	void perform(GraphDatabaseService aGraphDb) throws GrapDbModifcationException;

}
