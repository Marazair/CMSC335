/*
 * File: Ship.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for SeaPort objects.
 */

package main;

import java.util.*;

public class SeaPort extends Thing implements Sorter {
	
	private ArrayList<Dock> docks;
	private ArrayList<Ship> queue, ships;
	private ArrayList<Person> persons;
	
	public SeaPort(Scanner sc) {
		super(sc);
		
		docks = new ArrayList<Dock>();
		ships = new ArrayList<Ship>();
		queue = new ArrayList<Ship>();
		persons = new ArrayList<Person>();
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
		if(comparator instanceof NameComparator) {
			Collections.sort(queue, comparator);
			Collections.sort(ships, comparator);
		}
		else
			Collections.sort(queue, comparator);
		
	}
}
