package net.luminis.research.neo4j.service;

import net.luminis.research.neo4j.util.GraphDbModification;

public interface GrapDbModificationService {

	void performInTransaction(GraphDbModification modification);

}
