/*
 * File: Ship.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for SeaPort objects.
 */

package main;

import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class SeaPort extends Thing implements Sorter, Runnable {
	
	private LinkedList<Ship> queue;
	private ArrayList<Dock> docks;
	private ArrayList<Ship> ships;
	private ArrayList<Person> persons;
	
	public SeaPort(Scanner sc) {
		super(sc);
		
		docks = new ArrayList<Dock>();
		ships = new ArrayList<Ship>();
		queue = new LinkedList<Ship>();
		persons = new ArrayList<Person>();
		
		new Thread(this).start();
	}
	
	public void addDock(Dock dock) {
		docks.add(dock);
	}
	
	public void addShip(Ship ship) {
		ships.add(ship);
	}
	
	public void addPerson(Person person) {
		persons.add(person);
	}
	
	public void queueShip(Ship ship) {
		queue.add(ship);
	}
	
	public String toString () {
		String st = "\n\nSeaPort: " + super.toString();
		for (Dock md: docks) st += "\n  " + md + "\n";
		st += "\n\n --- List of all ships in queue:";
		for (Ship ms: queue ) st += "\n   > " + ms;
		st += "\n\n --- List of all ships:";
		for (Ship ms: ships) st += "\n   > " + ms;
		st += "\n\n --- List of all persons:";
		for (Person mp: persons) st += "\n   > " + mp;
		return st;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public ArrayList<Dock> getDocks() {
		return docks;
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(queue, comparator);
		Collections.sort(ships, comparator);
		Collections.sort(docks, comparator);
		Collections.sort(persons, comparator);
	}
	
	public void sortShip(Comparator<Ship> comparator) {
		Collections.sort(queue, comparator);
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		DefaultMutableTreeNode queueNode = new DefaultMutableTreeNode("Queue");
		DefaultMutableTreeNode docksNode = new DefaultMutableTreeNode("Docks");
		DefaultMutableTreeNode personsNode = new DefaultMutableTreeNode("Persons");
		
		node.add(queueNode);
		node.add(docksNode);
		node.add(personsNode);
		
		
		for(Ship s:queue)
			queueNode.add(s.createNode());
		for(Dock d:docks)
			docksNode.add(d.createNode());
		for(Person p:persons)
			personsNode.add(p.createNode());
			
		return node;
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {}
		
		int index = 0;
		
		while (!queue.isEmpty()) {
			for(Dock d:docks) {
				Ship ship = d.getShip();
				
				if (ship == null || ship.jobsComplete()) {
					ship = queue.pop();
					d.assignShip(ship);
				}
				
				for (Job j:ship.getJobs()) {
					Person worker = j.getWorker();
					if (worker == null) {
						worker = persons.get(index);
						index++;
						
						synchronized(j) {
							j.assignWorker(worker);
							j.notify();
						}
						
						
						if (index >= persons.size())
							index = 0;
					}
				}
			}
		}
	}
}
