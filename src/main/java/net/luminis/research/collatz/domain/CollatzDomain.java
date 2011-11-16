package net.luminis.research.collatz.domain;

import java.util.List;

public interface CollatzDomain {

	public List<Integer> elements();

	public void imageOf(Integer element, Integer image);

	public List<Integer> pathOf(Integer element);

}