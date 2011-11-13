package net.luminis.research.collatz.service.creation.impl;

import net.luminis.research.collatz.service.creation.CollatzCreationService;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StandardCollatzCreationServiceTest {
	@Test
	public void shouldExist() {
		assertNotNull(StandardCollatzCreationService.class);
	}

	@Test
	public void shouldImplementCollatzCreationService() {
		StandardCollatzCreationService service = new StandardCollatzCreationService();

		assertTrue(service instanceof CollatzCreationService);
	}

}
