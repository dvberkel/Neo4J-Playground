package net.luminis.research.collatz.calculator.impl;

import net.luminis.research.collatz.calculator.CollatzCalculator;

public class StandardCollatzCalculator implements CollatzCalculator {

	@Override
	public Integer successorTo(Integer n) {
		if (n % 2 == 0)
			return n / 2;
		else
			return 3 * n + 1;
	}
}
