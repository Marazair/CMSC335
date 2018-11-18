package main;

import java.util.Comparator;

public class IndexComparator implements Comparator<Thing> {

	@Override
	public int compare(Thing t1, Thing t2) {
		return t1.compareTo(t2);
	}

}
