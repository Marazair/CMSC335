/*
 * File: Ship.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Ship objects.
 */

package main;

import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

import main.Job.Status;

public class Ship extends Thing implements Sorter {
	private PortTime arrivalTime, docktime;
	private double draft, length, weight, width;
	private ArrayList<Job> jobs;
	private Dock dock;
	private SeaPort port;
	
	
	public Ship(Scanner sc) throws NoSuchObject {
		super(sc);
		
		jobs = new ArrayList<Job>();
		
		if(sc.hasNextDouble()) weight = sc.nextDouble();
		if(sc.hasNextDouble()) length = sc.nextDouble();
		if(sc.hasNextDouble()) width = sc.nextDouble();
		if(sc.hasNextDouble()) draft = sc.nextDouble();
		
		try {
			dock = World.getDockByIndex(getParent());
			port = World.getPortByIndex(dock.getParent());
			port.getAvailableDocks().remove(dock);
		} catch (NoSuchObject nse) {
			port = World.getPortByIndex(getParent());
		}
	}
	
	public void undock() {
		if (dock != null)
			port.undockShip(this);
	}
	
	public Dock getDock() {
		return dock;
	}
	
	public SeaPort getPort() {
		return port;
	}
	
	public void assignDock(Dock dock) {
		this.dock = dock;
	}
	
	public void addJob(Job job) {
		jobs.add(job);
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
	
	public boolean jobsComplete() {
		boolean complete = true;
		
		for(Job j:jobs) {
			if(j.status != Status.CANCELLED && j.status != Status.DONE)
				complete = false;
		}
		
		return complete;
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(jobs, comparator);
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		DefaultMutableTreeNode dimensionsNode = new DefaultMutableTreeNode("Dimensions");
		DefaultMutableTreeNode jobsNode = new DefaultMutableTreeNode("Jobs");
		
		node.add(dimensionsNode);
		node.add(jobsNode);
		
		dimensionsNode.add(new DefaultMutableTreeNode("Draft: " + draft));
		dimensionsNode.add(new DefaultMutableTreeNode("Length: " + length));
		dimensionsNode.add(new DefaultMutableTreeNode("Width: " + width));
		dimensionsNode.add(new DefaultMutableTreeNode("Weight: " + weight));
		
		for(Job j:jobs)
			jobsNode.add(j.createNode());
		
		return node;
	}
}
