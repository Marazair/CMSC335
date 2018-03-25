package main;

import java.util.*;

public class Ship extends Thing {
	private PortTime arrivalTime, docktime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
	
	public Ship(int index, int parent, String name, double weight, double length, double width, double draft) {
		super(index, parent, name);
		this.draft = draft;
		this.length = length;
		this.weight = weight;
		this.width = width;
	}
	
	public Ship(Scanner sc) {
		super(sc);
		if(sc.hasNextDouble()) weight = sc.nextDouble();
		if(sc.hasNextDouble()) length = sc.nextDouble();
		if(sc.hasNextDouble()) width = sc.nextDouble();
		if(sc.hasNextDouble()) draft = sc.nextDouble();
	}
}
