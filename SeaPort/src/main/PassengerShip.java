package main;

public class PassengerShip extends Ship {
	private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
	
	public PassengerShip(int numberOfPassengers, int numberOfRooms, int numberOfOccupiedRooms) {
		this.numberOfPassengers = numberOfPassengers;
		this.numberOfRooms = numberOfRooms;
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
	}
	
	//Attempts to book a room. Returns 1 if successful, -1 if booking would put ship over capacity.
	public int bookRoom(int passengers, int rooms) {
		if (numberOfOccupiedRooms + rooms <= numberOfRooms) {
			numberOfPassengers += passengers;
			numberOfOccupiedRooms += rooms;
			return 1;
		}
		else {
			return -1;
		}
	}
	
	//Removes the specified number of passengers if possible and returns 1.
	//If this would result in less than 0 occupied rooms or passengers, return -1.
	public int disembark(int passengers, int rooms) {
		if (numberOfOccupiedRooms - rooms >= 0 && numberOfPassengers - passengers >= 0) {
			numberOfPassengers -= passengers;
			numberOfOccupiedRooms -= rooms;
			return 1;
		}
		else {
			return -1;
		}
	}
}
