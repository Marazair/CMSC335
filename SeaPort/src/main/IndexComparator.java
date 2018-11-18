/*
 * File: IndexComparator.java
 * Date: 11/14/2018
 * Author: Nicholas Mills
 * Purpose: Provide a comparator for sorting things by index.
 */

package main;

import java.util.Comparator;

public class IndexComparator implements Comparator<Thing> {

	@Override
	public int compare(Thing t1, Thing t2) {
		return t1.compareTo(t2);
	}

}
