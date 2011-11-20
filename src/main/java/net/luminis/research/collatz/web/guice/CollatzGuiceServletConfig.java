package net.luminis.research.collatz.web.guice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CollatzGuiceServletConfig implements ServletContextListener {
	public static final String INJECTOR_NAME = Injector.class.getName();

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.setAttribute(INJECTOR_NAME, getInjector());

	}

	private Injector getInjector() {
		return Guice.createInjector(new CollatzServletModule());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.removeAttribute(INJECTOR_NAME);
	}

}
