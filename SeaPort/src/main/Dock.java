package main;

public class Dock extends Thing {
	private Ship ship;
	
	public Dock() {
		ship = null;
	}
	
	public Dock(Ship ship) {
		this.ship = ship;
	}
	
	public int dockShip(Ship newShip) {
		if(ship == null) {
			ship = newShip;
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public void undockShip() {
		ship = null;
	}
}
