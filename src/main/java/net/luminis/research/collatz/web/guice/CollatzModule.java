package net.luminis.research.collatz.web.guice;

import net.luminis.research.collatz.calculator.CollatzCalculator;
import net.luminis.research.collatz.calculator.impl.StandardCollatzCalculator;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;
import net.luminis.research.collatz.domain.provider.impl.Neo4JCollatzDomainProvider;
import net.luminis.research.collatz.service.creation.CollatzCreationService;
import net.luminis.research.collatz.service.creation.impl.StandardCollatzCreationService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class CollatzModule extends AbstractModule {
	private static final String GRAPH_DB_LOCATION = "graph database location";
	private volatile GraphDatabaseService graphdb;

	@Override
	protected void configure() {
		bind(CollatzCreationService.class).to(StandardCollatzCreationService.class);
		bind(CollatzCalculator.class).to(StandardCollatzCalculator.class);
		bind(CollatzDomainProvider.class).to(Neo4JCollatzDomainProvider.class);
		bind(String.class).annotatedWith(Names.named(GRAPH_DB_LOCATION)).toInstance("graphdb/collatz");
	}

	@SuppressWarnings("unused")
	@Provides
	private synchronized GraphDatabaseService provideGraphDatabaseService(
		@Named(GRAPH_DB_LOCATION) final String graphDbLocation) {
		if (graphdb == null) {
			graphdb = new EmbeddedGraphDatabase(graphDbLocation);
		}
		return graphdb;
	}

}
