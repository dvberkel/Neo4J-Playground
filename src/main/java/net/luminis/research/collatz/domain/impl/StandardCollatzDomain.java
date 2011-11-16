package net.luminis.research.collatz.domain.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.luminis.research.collatz.domain.CollatzDomain;

public class StandardCollatzDomain implements CollatzDomain {

	private final List<Integer> elements;

	public StandardCollatzDomain(List<Integer> elements) {
		this.elements = new ArrayList<Integer>();
		this.elements.addAll(elements);
	}

	@Override
	public List<Integer> elements() {
		return Collections.unmodifiableList(elements);
	}

}
