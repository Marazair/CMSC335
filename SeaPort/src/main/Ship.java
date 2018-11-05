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
	
	public ArrayList<Job> getJobs() {
		return jobs;
	}
	
	public String toString() {
		String st = super.toString();
		if (jobs.size() == 0) 
	         return st;
	    for (Job mj: jobs) st += "\n       - " + mj;
	    return st;
	}

	public PortTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(PortTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public PortTime getDocktime() {
		return docktime;
	}

	public void setDocktime(PortTime docktime) {
		this.docktime = docktime;
	}

	public double getDraft() {
		return draft;
	}

	public double getLength() {
		return length;
	}

	public double getWeight() {
		return weight;
	}

	public double getWidth() {
		return width;
	}
}
