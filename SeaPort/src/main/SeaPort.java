package main;

import java.util.*;

public class SeaPort extends Thing {
	
	private ArrayList<Dock> docks;
	private ArrayList <Ship> queue, ships;
	private ArrayList<Person> persons;
	
	public SeaPort(int index, int parent, String name) {
		super(index, parent, name);
		// TODO Auto-generated constructor stub
	}
	
	public SeaPort(Scanner sc) {
		super(sc);
	}
}
