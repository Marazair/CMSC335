/*
 * File: Sorter.java
 * Date: 11/16/2018
 * Author: Nicholas Mills
 * Purpose: Provide an interface for objects with lists
 * 			to take a comparator and sort said lists.
 */
package main;

import java.util.Comparator;

public interface Sorter {
	public void sort(Comparator<Thing> comparator);
}
