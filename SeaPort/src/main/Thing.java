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
	
	public int compareTo(Thing o) {
		// TODO Auto-generated method stub
		return 0;
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
