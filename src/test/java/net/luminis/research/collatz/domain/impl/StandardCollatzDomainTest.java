package net.luminis.research.collatz.domain.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import net.luminis.research.collatz.domain.CollatzDomain;

import org.junit.Test;

public class StandardCollatzDomainTest {

	@Test
	public void shouldExist() {
		assertNotNull(StandardCollatzDomain.class);
	}

	@Test
	public void shouldBeInstanceOfCollatzDomain() {
		assertTrue(new StandardCollatzDomain() instanceof CollatzDomain);
	}

	@Test
	public void shouldAcceptImages() {
		CollatzDomain domain = new StandardCollatzDomain();

		domain.imageOf(1, 4);

		assertEquals(Arrays.asList(new Integer[]{1}), domain.elements());
	}



}
