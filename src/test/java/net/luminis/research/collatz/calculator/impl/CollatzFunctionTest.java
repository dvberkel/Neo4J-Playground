package net.luminis.research.collatz.calculator.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.luminis.research.collatz.calculator.CollatzCalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CollatzFunctionTest {
	private final Integer expected;
	private final Integer input;

	public CollatzFunctionTest(Integer expected, Integer input) {
		this.expected = expected;
		this.input = input;
	}

	@Test
	public void calculateSuccessor() {
		CollatzCalculator calculator = new StandardCollatzCalculator();

		Integer result = calculator.successorTo(input);

		assertEquals(expected, result);
	}

	@Parameters
	public static Collection<Integer[]> data() {
		List<Integer[]> data = new ArrayList<Integer[]>();
		data.add(new Integer[] { 1, 2 });
		data.add(new Integer[] { 2, 4 });
		data.add(new Integer[] { 10, 3 });
		data.add(new Integer[] { 16, 5 });
		return data;
	}
}
