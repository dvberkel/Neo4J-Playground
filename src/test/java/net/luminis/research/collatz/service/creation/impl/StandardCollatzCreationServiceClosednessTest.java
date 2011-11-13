package net.luminis.research.collatz.service.creation.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.luminis.research.collatz.service.creation.CollatzCreationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StandardCollatzCreationServiceClosednessTest {
	private final Integer low;
	private final Integer high;
	private final List<Integer> expected;

	public StandardCollatzCreationServiceClosednessTest(Integer low, Integer high, List<Integer> expected) {
		this.low = low;
		this.high = high;
		this.expected = expected;
	}

	@Test
	public void createdElements() {
		CollatzCreationService service = new StandardCollatzCreationService();

		assertEquals(expected, service.createRange(low, high));
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(createInputData(1, 2, 1, 2, 4));
		data.add(createInputData(1, 3, 1, 2, 3, 4, 5, 8, 10, 16));
		return data;
	}

	private static Object[] createInputData(Integer low, Integer high, Integer... expected) {
		return new Object[] { low, high, Arrays.asList(expected) };
	}
}
