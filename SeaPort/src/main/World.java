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
		sc.close();
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
			SeaPort port = getPortByIndex(index);
			port.addShip(ship);
			port.queueShip(ship);
			return;
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
	
	public Person getPersonByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Person mp: msp.getPersons())
				if (mp.getIndex() == x)
					return mp;
		throw new NoSuchObject("Person", false, x);
	}
	
	public SeaPort getPortByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			if (msp.getIndex() == x)
				return msp;
		throw new NoSuchObject("Port", false, x);
	}
	
	public Dock getDockByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Dock md: msp.getDocks())
				if (md.getIndex() == x)
					return md;
		
		throw new NoSuchObject("Dock", false, x);
	}
	
	public Ship getShipByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
	         for (Ship ms: msp.getShips())
	            if (ms.getIndex() == x) 
	               return ms;
			
		throw new NoSuchObject("Ship", false, x);
	}
	
	public Job getJobByIndex(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Ship ms: msp.getShips())
				for (Job mj: ms.getJobs())
					if (mj.getIndex() == x)
						return mj;
		
		throw new NoSuchObject("Job", false, x);
	}
	
	public Thing indexSearch(int x) throws NoSuchObject {
		try {
			return getShipByIndex(x);
		}
		catch(NoSuchObject nso) {}
		
		try {
			return getPortByIndex(x);
		}
		catch(NoSuchObject nso) {}
		
		try {
			return getDockByIndex(x);
		}
		catch(NoSuchObject nso) {}
		
		try {
			return getPersonByIndex(x);
		}
		catch(NoSuchObject nso) {}
		try {
			return getJobByIndex(x);
		}
		catch(NoSuchObject nso) {}
		
		throw new NoSuchObject("Object", false, x);
	}
	
	public Person getPersonByName(String name) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Person mp: msp.getPersons())
				if (mp.getName().equals(name))
					return mp;
		throw new NoSuchObject("Person", name);
	}
	
	public SeaPort getPortByName(String name) throws NoSuchObject {
		for (SeaPort msp: ports)
			if (msp.getName().equals(name))
				return msp;
		throw new NoSuchObject("Port", name);
	}
	
	public Dock getDockByName(String name) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Dock md: msp.getDocks())
				if (md.getName().equals(name))
					return md;
		throw new NoSuchObject("Dock", name);
	}
	
	public Ship getShipByName(String name) throws NoSuchObject {
		for (SeaPort msp: ports)
	         for (Ship ms: msp.getShips())
	            if (ms.getName().equals(name)) 
	               return ms;
			
		throw new NoSuchObject("Ship", name);
	}
	
	public Job getJobByName(String name) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Ship ms: msp.getShips())
				for (Job mj: ms.getJobs())
					if (mj.getName().equals(name))
						return mj;
		
		throw new NoSuchObject("Job", name);
	}
	
	public ArrayList<Thing> nameSearch(String name) throws NoSuchObject {
		ArrayList<Thing> matches = new ArrayList<Thing>();
		
		try {
			matches.add(getShipByName(name));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getPortByName(name));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getDockByName(name));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getPersonByName(name));
		}
		catch(NoSuchObject nso) {}
		try {
			matches.add(getJobByName(name));
		}
		catch(NoSuchObject nso) {}
		
		if (matches.size() != 0)
			return matches;
		
		throw new NoSuchObject("Object", name);
	}
	
	public Person getPersonByParent(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Person mp: msp.getPersons())
				if (mp.getParent() == x)
					return mp;
		throw new NoSuchObject("Person", true, x);
	}
	
	public SeaPort getPortByParent(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			if (msp.getParent() == x)
				return msp;
		throw new NoSuchObject("Port", true, x);
	}
	
	public Dock getDockByParent(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Dock md: msp.getDocks())
				if (md.getParent() == x)
					return md;
		
		throw new NoSuchObject("Dock", true, x);
	}
	
	public Ship getShipByParent(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
	         for (Ship ms: msp.getShips())
	            if (ms.getParent() == x) 
	               return ms;
			
		throw new NoSuchObject("Ship", true, x);
	}
	
	public Job getJobByParent(int x) throws NoSuchObject {
		for (SeaPort msp: ports)
			for (Ship ms: msp.getShips())
				for (Job mj: ms.getJobs())
					if (mj.getParent() == x)
						return mj;
		
		throw new NoSuchObject("Job", true, x);
	}
	
	public ArrayList<Thing> parentSearch(int x) throws NoSuchObject {
		ArrayList<Thing> matches = new ArrayList<Thing>();
		
		try {
			matches.add(getShipByParent(x));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getPortByParent(x));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getDockByParent(x));
		}
		catch(NoSuchObject nso) {}
		
		try {
			matches.add(getPersonByParent(x));
		}
		catch(NoSuchObject nso) {}
		try {
			matches.add(getJobByParent(x));
		}
		catch(NoSuchObject nso) {}
		
		if (matches.size() != 0)
			return matches;
		
		throw new NoSuchObject("Object", true, x);
	}
	
	public String toString() {
		String st = ">>>>> The world:";
		
		for(SeaPort sp: ports) {
			st += sp;
		}
		
		return st;
	}
}
