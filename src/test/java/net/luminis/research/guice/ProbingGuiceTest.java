package net.luminis.research.guice;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import scala.actors.threadpool.Arrays;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class ProbingGuiceTest {
	public static final String STANDARD = "standard";
	private Injector injector;

	@Before
	public void createInjector() {
		this.injector = Guice.createInjector(new ProbeModule());
	}

	@Test
	public void shouldBeAbleToCreateAnInjector() {
		assertNotNull(injector);
	}

	@Test
	public void shouldReturnInstances() {
		assertNotNull(injector.getInstance(Probe.class));
	}

	@Test
	public void shouldReturnBoundInstances() {
		Probe probe = injector.getInstance(Probe.class);

		String name = probe.name();

		assertEquals(STANDARD, name);
	}

	@Test
	public void shouldCreateEntireObjectGraph() {
		Machine machine = injector.getInstance(Machine.class);

		String log = machine.operate();

		assertEquals(STANDARD, log);
	}

	@Test
	public void namedInstanceBindings() {
		LuckyNumber luckyNumber = injector.getInstance(LuckyNumber.class);

		Integer fortune = luckyNumber.fortune();

		assertEquals(Integer.valueOf(37), fortune);
	}

	@Test
	public void providerMethods() {
		ComplexStructure structure = injector.getInstance(ComplexStructure.class);

		Integer rank = structure.ranking();

		assertEquals(Integer.valueOf(37), rank);
	}

	@Test
	public void providerBindings() {
		CompoundStructure structure = injector.getInstance(CompoundStructure.class);

		List<Integer> elements = structure.elements();

		assertEquals(Arrays.asList(new Integer[] { 1, 2, 3, 5, 8, 13 }), elements);
	}

	@Test
	public void scopeOfInstances() {
		Probe aProbe = injector.getInstance(Probe.class);
		Probe otherProbe = injector.getInstance(Probe.class);

		assertSame(aProbe, otherProbe);
	}
}

interface Probe {
	public String name();
}

class StandardProbe implements Probe {
	@Override
	public String name() {
		return ProbingGuiceTest.STANDARD;
	}
}

class SuperProbe implements Probe {
	@Override
	public String name() {
		return "super";
	}
}

interface Machine {
	public String operate();
}

class Scanner implements Machine {
	private final Probe probe;

	@Inject
	public Scanner(Probe probe) {
		this.probe = probe;
	}

	@Override
	public String operate() {
		return probe.name();
	}
}

class LuckyNumber {
	private final Integer fortune;

	@Inject
	public LuckyNumber(@Named("lucky number") Integer fortune) {
		this.fortune = fortune;
	}

	public Integer fortune() {
		return fortune;
	}
}

interface ComplexStructure {
	public Integer ranking();
}

interface CompoundStructure {
	public List<Integer> elements();
}

class CompoundStructureProvider implements Provider<CompoundStructure> {

	@Override
	public CompoundStructure get() {
		final Integer[] elements = new Integer[] { 1, 2, 3, 5, 8, 13 };
		return new CompoundStructure() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Integer> elements() {
				return Arrays.asList(elements);
			}
		};
	}

}

class ProbeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Probe.class).to(StandardProbe.class).in(Singleton.class);
		bind(Machine.class).to(Scanner.class);
		bind(Integer.class).annotatedWith(Names.named("lucky number")).toInstance(37);
		bind(CompoundStructure.class).toProvider(CompoundStructureProvider.class);
	}

	@Provides
	private ComplexStructure provideComplexStructure(@Named("lucky number") final Integer ranking) {
		return new ComplexStructure() {

			@Override
			public Integer ranking() {
				return ranking;
			}
		};
	}
}
