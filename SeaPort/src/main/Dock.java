package main;

import java.util.Scanner;

public class Dock extends Thing {
	private Ship ship;
	
	public Dock(Scanner sc) {
		super(sc);
	}
	
	public void dockShip(Ship ship) {
		this.ship = ship;
	}
	
	
}
