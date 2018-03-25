package main;

import java.util.Scanner;

public class Thing implements Comparable<Thing> {

	private int index, parent;
	private String name;
	
	public Thing(int index, int parent, String name) {
		this.name = name;
		this.index = index;
		this.parent = parent;
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
	
	public void setName(String name) {
		this.name = name;
	}
}
