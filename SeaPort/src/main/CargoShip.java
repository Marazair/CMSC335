package main;

public class CargoShip extends Ship {
	private double cargoValue, cargoVolume, cargoWeight;
	
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
