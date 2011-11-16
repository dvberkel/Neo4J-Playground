package net.luminis.research.collatz.service.creation.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.luminis.research.collatz.calculator.impl.StandardCollatzCalculator;
import net.luminis.research.collatz.service.creation.CollatzCreationService;

import org.junit.Test;

public class StandardCollatzCreationServiceTest {
	@Test
	public void shouldExist() {
		assertNotNull(StandardCollatzCreationService.class);
	}

	@Test
	public void shouldImplementCollatzCreationService() {
		StandardCollatzCreationService service = new StandardCollatzCreationService(new StandardCollatzCalculator());

		assertTrue(service instanceof CollatzCreationService);
	}

}
