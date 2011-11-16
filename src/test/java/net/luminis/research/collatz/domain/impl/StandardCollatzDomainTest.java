package net.luminis.research.collatz.domain.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

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

	@Test
	public void shouldReturnPathOfElement() {
		CollatzDomain domain = new StandardCollatzDomain();
		domain.imageOf(1, 4);
		domain.imageOf(2, 1);
		domain.imageOf(4, 2);

		List<Integer> path = domain.pathOf(4);

		assertEquals(Arrays.asList(new Integer[]{4,2,1}), path);
	}
}
