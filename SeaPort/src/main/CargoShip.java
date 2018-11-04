package main;

import java.util.*;

public class CargoShip extends Ship {
	
	private double cargoValue, cargoVolume, cargoWeight;
	
	public CargoShip(Scanner sc) {
		super(sc);
		if(sc.hasNextDouble()) cargoWeight = sc.nextDouble();
		if(sc.hasNextDouble()) cargoVolume = sc.nextDouble();
		if(sc.hasNextDouble()) cargoValue = sc.nextDouble();
	}
	
	public void loadCargo(double value, double volume, double weight) {
		cargoValue += value;
		cargoVolume += volume;
		cargoWeight += weight;
	}
	
	public void unloadCargo(double value, double volume, double weight) {
		cargoValue -= value;
		cargoVolume -= volume;
		cargoWeight -= weight;
	}
	
	public String toString() {
		String st = "Cargo Ship: " + super.toString();
		return st;
	}
}
