package net.luminis.research.collatz.service.creation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import net.luminis.research.collatz.calculator.CollatzCalculator;
import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.domain.provider.CollatzDomainProvider;
import net.luminis.research.collatz.service.creation.CollatzCreationService;

import com.google.inject.Inject;

public class StandardCollatzCreationService implements CollatzCreationService {

	private final CollatzCalculator calculator;
	private final CollatzDomainProvider provider;

	@Inject
	public StandardCollatzCreationService(CollatzCalculator calculator, CollatzDomainProvider provider) {
		this.calculator = calculator;
		this.provider = provider;
	}

	@Override
	public CollatzDomain createDomain(Integer low, Integer high) {
		Map<Integer, Integer> range = createRange(low, high);
		CollatzDomain domain = provider.getInstance();
		for (Entry<Integer, Integer> entry : range.entrySet()) {
			domain.imageOf(entry.getKey(), entry.getValue());
		}
		return domain;
	}

	private Map<Integer, Integer> createRange(Integer low, Integer high) {
		Map<Integer, Integer> images = new HashMap<Integer, Integer>();
		PriorityQueue<Integer> toProcess = initialElements(low, high);
		while (toProcess.size() > 0) {
			Integer element = toProcess.poll();
			Integer image = calculator.successorTo(element);
			images.put(element, image);
			if (!images.containsKey(image)) {
				toProcess.offer(image);
			}
		}
		return images;
	}

	private PriorityQueue<Integer> initialElements(Integer low, Integer high) {
		PriorityQueue<Integer> initialElements = new PriorityQueue<Integer>();
		for (int element = low; element <= high; element++) {
			initialElements.add(element);
		}
		return initialElements;
	}
}
