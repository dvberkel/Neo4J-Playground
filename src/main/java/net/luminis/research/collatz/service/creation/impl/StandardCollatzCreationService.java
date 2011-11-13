package net.luminis.research.collatz.service.creation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

import net.luminis.research.collatz.calculator.CollatzCalculator;
import net.luminis.research.collatz.calculator.impl.StandardCollatzCalculator;
import net.luminis.research.collatz.service.creation.CollatzCreationService;

public class StandardCollatzCreationService implements CollatzCreationService {

	private final CollatzCalculator calculator = new StandardCollatzCalculator();

	@Override
	public List<Integer> createRange(Integer low, Integer high) {
		PriorityQueue<Integer> toProcess = initialElements(low, high);
		SortedSet<Integer> visited = new TreeSet<Integer>();
		visited.addAll(toProcess);
		while (toProcess.size() > 0) {
			Integer image = calculator.successorTo(toProcess.poll());
			if (!visited.contains(image)) {
				toProcess.offer(image);
				visited.add(image);
			}
		}
		return asList(visited);
	}

	private PriorityQueue<Integer> initialElements(Integer low, Integer high) {
		PriorityQueue<Integer> initialElements = new PriorityQueue<Integer>();
		for (int element = low; element <= high; element++) {
			initialElements.add(element);
		}
		return initialElements;
	}

	private List<Integer> asList(SortedSet<Integer> visited) {
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(visited);
		return result;
	}

}
