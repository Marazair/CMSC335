package main;

import java.util.*;

public class World extends Thing {
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
	public World(Scanner sc) throws NoSuchObject {
		time = new PortTime(0);
		ports = new ArrayList<SeaPort>();
		
		while (sc.hasNextLine()) {
			process(sc.nextLine());
		}
	}
	
	public void process(String str) throws NoSuchObject {
		Scanner sc = new Scanner(str);
		if(!sc.hasNext()) {
			sc.close();
			return;
		}
		switch(sc.next()) {
		case "port": 
			addPort(new SeaPort(sc));
			break;
		case "dock":
			addDock(new Dock(sc));
			break;
		case "pship":
			addShip(new PassengerShip(sc));
			break;
		case "cship":
			addShip(new CargoShip(sc));
			break;
		case "person":
			addPerson(new Person(sc));
			break;
		}
	}
	
	public int getTime() {
		return time.getTime();
	}
	
	public void addPort(SeaPort port) {
		ports.add(port);
	}
	
	public void addDock(Dock dock) throws NoSuchObject {
		int index = dock.getParent();
		
		getPortByIndex(index).addDock(dock);
	}
	
	public void addShip(Ship ship) throws NoSuchObject {
		int index = ship.getParent();
		
		try {
			getPortByIndex(index).addShip(ship);
		}
		catch (NoSuchObject nse) {}
		
		Dock dock = getDockByIndex(index);
		dock.addShip(ship);
		getPortByIndex(dock.getParent()).addShip(ship);
	}
	
	public void addPerson(Person person) throws NoSuchObject {
		int index = person.getParent();
		
		getPortByIndex(index).addPerson(person);
		
	}
	
	public SeaPort getPortByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			if (msp.getIndex() == x)
				return msp;
		throw new NoSuchObject("Port", x);
	}
	
	public Dock getDockByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Dock md: msp.getDocks())
				if (md.getIndex() == x)
					return md;
		
		throw new NoSuchObject("Dock", x);
	}
	
	public Ship getShipByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
	         for (Ship ms: msp.getShips())
	            if (ms.getIndex() == x) 
	               return ms;
			
		throw new NoSuchObject("Ship", x);
	}
	
	public SeaPort removePort(SeaPort port) {
		int location = ports.indexOf(port);
		
		if(location != -1) {
			ports.remove(location);
			return port;
		}
		else {
			return null;
		}
	}
}
