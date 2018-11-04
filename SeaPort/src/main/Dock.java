package main;

import java.util.Scanner;

public class Dock extends Thing {
	private Ship ship;
	
	public Dock(Scanner sc) {
		super(sc);
	}
	
	public void addShip(Ship ship) {
		this.ship = ship;
	}
	
	public String toString() {
		String st = "Dock: " + super.toString();
		if(ship != null)
			st += "\n    Ship: " + ship;
		return st;
	}
	
}
