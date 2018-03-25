package main;

public class Room extends Thing {
	private int maximumOccupancy;
	private boolean occupied;
	
	public Room(int index, int parent, String name, int maximumOccupancy, boolean occupied) {
		super(index, parent, name);
		this.maximumOccupancy = maximumOccupancy;
		this.occupied = occupied;
	}
}
