package net.luminis.research.collatz.web.beans;

import java.util.ArrayList;
import java.util.List;

public class CollatzPathBean {
	List<Integer> path = new ArrayList<Integer>();

	public List<Integer> getPath() {
		return path;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

}
