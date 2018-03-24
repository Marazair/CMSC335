package main;

public class Cargo {
	private double value;
	private double weight;
	private double volume;
	
	public Cargo(double value, double weight, double volume) {
		this.value = value;
		this.weight = weight;
		this.volume = volume;
	}
	
	public double getValue() {
		return value;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getVolume() {
		return volume;
	}
}
