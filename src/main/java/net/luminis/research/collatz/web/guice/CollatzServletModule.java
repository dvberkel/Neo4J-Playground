package net.luminis.research.collatz.web.guice;

import net.luminis.research.collatz.web.CollatzServlet;

import com.google.inject.servlet.ServletModule;

public class CollatzServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/collatz").with(CollatzServlet.class);
	}
}
