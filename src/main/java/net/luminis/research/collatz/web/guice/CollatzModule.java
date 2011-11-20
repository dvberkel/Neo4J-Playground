package net.luminis.research.collatz.web.guice;

import net.luminis.research.collatz.web.CollatzServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CollatzModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CollatzServlet.class).in(Singleton.class);
	}

}
