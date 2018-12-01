/*
 * File: World.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for World objects.
 */

package main;

import java.util.*;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public class World extends Thing implements Sorter {
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
	public World(Scanner sc) throws NoSuchObject {
		time = new PortTime(0);
		ports = new ArrayList<SeaPort>();
		
		readFile(sc);
	}
	
	public void readFile(Scanner fsc) throws NoSuchObject {
		HashMap<Integer, Dock> dockMap = new HashMap<Integer, Dock>();
		HashMap<Integer, Ship> shipMap = new HashMap<Integer, Ship>();
		HashMap<Integer, SeaPort> portMap = new HashMap<Integer, SeaPort>();
		
		
		while (fsc.hasNextLine()) {
			Scanner sc = new Scanner(fsc.nextLine());
			SeaPort sp;
			Dock d;
			Ship s;
			int parent;
			
			if(!sc.hasNext()) {
				sc.close();
				continue;
			}
			
			switch(sc.next()) {
			case "port":
				sp = new SeaPort(sc);
				ports.add(sp);
				portMap.put(sp.getIndex(), sp);
				break;
			case "dock":
				d = new Dock(sc);
				parent = d.getParent();
				sp = portMap.get(parent);
				
				if (sp != null) {
					sp.addDock(d);
					dockMap.put(d.getIndex(), d);
				}
				else {
					throw new NoSuchObject("Port", false, parent);
				}
				break;
			case "pship":
				s = new PassengerShip(sc);
				addShip(s, dockMap, portMap);
				shipMap.put(s.getIndex(), s);
				break;
			case "cship":
				s = new CargoShip(sc);
				addShip(s, dockMap, portMap);
				shipMap.put(s.getIndex(), s);
				break;
			case "person":
				Person p = new Person(sc);
				parent = p.getParent();
				sp = portMap.get(parent);
				
				if (sp != null) {
					sp.addPerson(p);
				}
				else
					throw new NoSuchObject("Port", false, parent);
				break;
			case "job":
				Job j = new Job(sc);
				parent = j.getParent();
				s = shipMap.get(parent);
				
				if (s != null) {
					s.addJob(j);
				}
			}
			sc.close();
		}
	}
	
	public int getTime() {
		return time.getTime();
	}

	public void addShip(Ship ship, HashMap<Integer, Dock> dhm, 
			HashMap<Integer, SeaPort> phm) throws NoSuchObject {
		int index = ship.getParent();
		SeaPort port;
		Dock dock;
		
		port = phm.get(index);
		
		try {
			port.addShip(ship);
			port.queueShip(ship);
			return;
		} catch (NullPointerException npe) {}
		
		dock = dhm.get(index);
		
		try {
			dock.addShip(ship);
			phm.get(dock.getParent()).addShip(ship);
			return;
		} catch (NullPointerException npe){
			throw new NoSuchObject("Ship", true, index);
		}
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

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(ports, comparator);
		for(SeaPort msp:ports) {
			msp.sort(comparator);
			for(Ship ms:msp.getShips()) {
				ms.sort(comparator);
				for(Job mj:ms.getJobs())
					mj.sort(comparator);
			}
		}
	}
	
	public ArrayList<JPanel> getJobPanels() {
		ArrayList<JPanel> jobPanels = new ArrayList<JPanel>();
		
		for (SeaPort sp:ports) 
			for (Ship s:sp.getShips()) 
				for (Job j:s.getJobs())
					jobPanels.add(j.getPanel());
		
		return jobPanels;
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("World");
		
		for(SeaPort sp:ports)
			node.add(sp.createNode());
		
		return node;
	}
}
