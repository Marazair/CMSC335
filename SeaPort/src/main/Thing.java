/*
 * File: Thing.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Thing objects.
 */

package main;

import java.util.Scanner;

public class Thing implements Comparable<Thing> {

	private int index, parent;
	private String name;
	
	public Thing() {
		name = "";
		index = 0;
		parent = 0;
	}
	
	public Thing(Scanner sc) {
		if (sc.hasNext()) name = sc.next();
		if (sc.hasNextInt()) index = sc.nextInt();
		if (sc.hasNextInt()) parent = sc.nextInt();
	}
	
	public int compareTo(Thing thing) {
		return this.index - thing.getIndex();
	}
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getParent() {
		return parent;
	}
	
	public String toString() {
		String st = name + " " + index;
		return st;
	}
}
