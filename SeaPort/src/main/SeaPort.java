package main;

import java.util.*;

public class SeaPort extends Thing {
	
	private ArrayList<Dock> docks;
	private ArrayList <Ship> queue, ships;
	private ArrayList<Person> persons;
	
	public SeaPort(Scanner sc) {
		super(sc);
	}
	
	public void addDock(Dock dock) {
		docks.add(dock);
	}
}
