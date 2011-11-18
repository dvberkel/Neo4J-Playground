package net.luminis.research.collatz.domain.provider.impl;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.domain.impl.Neo4JCollatzDomain;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;

import org.neo4j.graphdb.GraphDatabaseService;

public class Neo4JCollatzDomainProvider implements CollatzDomainProvider {
	private final GraphDatabaseService graphDb;
	private CollatzDomain instance;

	public Neo4JCollatzDomainProvider(GraphDatabaseService aGraphDb) {
		this.graphDb = aGraphDb;
	}

	@Override
	public synchronized CollatzDomain getInstance() {
		if (instance == null) {
			instance = new Neo4JCollatzDomain(graphDb);
		}
		return instance;
	}
}
