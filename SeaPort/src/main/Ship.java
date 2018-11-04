package main;

import java.util.*;

public class Ship extends Thing {
	private PortTime arrivalTime, docktime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
	
	public Ship(Scanner sc) {
		super(sc);
		
		jobs = new ArrayList<Job>();
		
		if(sc.hasNextDouble()) weight = sc.nextDouble();
		if(sc.hasNextDouble()) length = sc.nextDouble();
		if(sc.hasNextDouble()) width = sc.nextDouble();
		if(sc.hasNextDouble()) draft = sc.nextDouble();
	}
}
