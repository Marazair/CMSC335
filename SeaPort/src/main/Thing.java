package main;

public class Thing implements Comparable<Thing> {

	private int index, parent;
	private String name;
	
	public Thing(int index, int parent, String name) {
		this.name = name;
		this.index = index;
		this.parent = parent;
	}
	
	public int compareTo(Thing o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
