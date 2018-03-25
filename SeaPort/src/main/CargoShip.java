package main;

import java.util.*;

public class CargoShip extends Ship {
	
	private double cargoValue, cargoVolume, cargoWeight;

	public CargoShip(int index, int parent, String name, double draft, double length, 
			double weight, double width, double cargoWeight, double cargoVolume, double cargoValue) {
		
		super(index, parent, name, draft, length, weight, width);
		this.cargoValue = cargoValue;
		this.cargoVolume = cargoVolume;
		this.cargoWeight = cargoWeight;
	}
	
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
}
