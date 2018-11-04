package main;

import java.util.*;

public class SeaPort extends Thing {
	
	private ArrayList<Dock> docks;
	private ArrayList<Ship> queue, ships;
	private ArrayList<Person> persons;
	
	public SeaPort(Scanner sc) {
		super(sc);
		
		docks = new ArrayList<Dock>();
		ships = new ArrayList<Ship>();
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

	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public ArrayList<Dock> getDocks() {
		return docks;
	}
}
