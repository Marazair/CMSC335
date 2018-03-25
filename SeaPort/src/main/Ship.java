package main;

import java.util.ArrayList;

public class Ship extends Thing {
	private PortTime arrivalTime, docktime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
	
	public Ship(int index, int parent, String name, double draft, double length, double weight, double width) {
		super(index, parent, name);
		this.draft = draft;
		this.length = length;
		this.weight = weight;
		this.width = width;
	}
}
