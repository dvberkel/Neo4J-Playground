package net.luminis.research.collatz.web.guice;

import net.luminis.research.collatz.calculator.CollatzCalculator;
import net.luminis.research.collatz.calculator.impl.StandardCollatzCalculator;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;
import net.luminis.research.collatz.domain.provider.impl.StandardCollatzDomainProvider;
import net.luminis.research.collatz.service.creation.CollatzCreationService;
import net.luminis.research.collatz.service.creation.impl.StandardCollatzCreationService;

import com.google.inject.AbstractModule;

public class CollatzModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CollatzCreationService.class).to(StandardCollatzCreationService.class);
		bind(CollatzCalculator.class).to(StandardCollatzCalculator.class);
		bind(CollatzDomainProvider.class).to(StandardCollatzDomainProvider.class);
	}

}
