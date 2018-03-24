package main;

import java.util.*;

public class CargoShip extends Ship {
	private ArrayList<Cargo> cargoHold;
	
	CargoShip(ArrayList<Cargo> cargoHold) {
		this.cargoHold = cargoHold;
	}
	
	public void loadCargo(Cargo cargo) {
		cargoHold.add(cargo);
	}
	
	public Cargo unloadCargo(Cargo cargo) {
		int location = cargoHold.indexOf(cargo);
		
		if(location != -1) {
			cargoHold.remove(location);
			return cargo;
		}
		else {
			return null;
		}
	}
	
	public double cargoValue() {
		double totalValue = 0;
		for (int x = 0; x < cargoHold.size(); x++) {
			totalValue += cargoHold.get(x).getValue();
		}
		
		return totalValue;
	}
	
	public double cargoWeight() {
		double totalWeight = 0;
		
		for (int x = 0; x < cargoHold.size(); x++) {
			totalWeight += cargoHold.get(x).getWeight();
		}
		
		return totalWeight;
	}
	
	public double cargoVolume() {
		double totalVolume = 0;
		
		for (int x = 0; x < cargoHold.size(); x++) {
			totalVolume += cargoHold.get(x).getVolume();
		}
		
		return totalVolume;
	}
}
