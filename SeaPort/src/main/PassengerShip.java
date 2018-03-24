package main;

public class PassengerShip extends Ship {
	private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
	
	public PassengerShip (int numberOfPassengers, int numberOfRooms, int numberOfOccupiedRooms) {
		this.numberOfPassengers = numberOfPassengers;
		this.numberOfRooms = numberOfRooms;
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
	}
	
	public int bookRoom(int passengers, int rooms) {
		if(numberOfOccupiedRooms + rooms <= numberOfRooms) {
			numberOfPassengers += passengers;
			numberOfOccupiedRooms += rooms;
			return 1;
		}
		else {
			return -1;
		}
	}
}
