package net.luminis.research.collatz.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.luminis.research.collatz.domain.CollatzDomain;

public class StandardCollatzDomain implements CollatzDomain {

	private final SortedMap<Integer,Integer> images;

	public StandardCollatzDomain() {
		images = new TreeMap<Integer, Integer>();
	}

	@Override
	public List<Integer> elements() {
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(images.keySet());
		return result;
	}

	@Override
	public void imageOf(Integer element, Integer image) {
		images.put(element,image);
	}

	@Override
	public List<Integer> pathOf(Integer element) {
		List<Integer> path = new ArrayList<Integer>();
		path.add(element); int index = 0;
		while (path.get(index).intValue() != 1) {
			path.add(images.get(path.get(index)));
			index++;
		}
		return path;
	}

}
