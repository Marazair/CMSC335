package main;

import java.util.*;

public class PassengerShip extends Ship {
	
	private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
	
	public PassengerShip(int index, int parent, String name, double draft, double length, double weight, double width,
			int numberOfPassengers, int numberOfRooms, int numberOfOccupiedRooms) {
		
		super(index, parent, name, draft, length, weight, width);
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
		this.numberOfPassengers = numberOfPassengers;
		this.numberOfRooms = numberOfRooms;
	}
	
	public PassengerShip(Scanner sc) {
		super(sc);
		if(sc.hasNextInt()) numberOfPassengers = sc.nextInt();
		if(sc.hasNextInt()) numberOfRooms = sc.nextInt();
		if(sc.hasNextInt()) numberOfOccupiedRooms = sc.nextInt();
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
