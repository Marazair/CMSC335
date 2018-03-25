package main;

public class Cargo extends Thing {
	private double value;
	private double weight;
	private double volume;
	
	public Cargo(String name, int index, int parent, double value, double weight, double volume) {
		super(index, parent, name);
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
