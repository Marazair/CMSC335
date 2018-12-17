/*
 * File: Ship.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for SeaPort objects.
 */

package main;

import java.awt.Dimension;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class SeaPort extends Thing implements Sorter {
	
	private LinkedList<Ship> queue;
	private ArrayList<Dock> docks;
	private ArrayList<Dock> availableDocks;
	private ArrayList<Ship> ships;
	private ArrayList<Person> persons;
	private HashMap<String, ArrayList<Person>> skillPools;
	private HashMap<String, Integer> skillCount;
	private JPanel panel;
	
	public SeaPort(Scanner sc) {
		super(sc);
		
		docks = new ArrayList<Dock>();
		availableDocks = new ArrayList<Dock>();
		ships = new ArrayList<Ship>();
		queue = new LinkedList<Ship>();
		persons = new ArrayList<Person>();
		skillPools = new HashMap<String, ArrayList<Person>>();
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(getName()));
		panel.setPreferredSize(new Dimension(200,200));
		
		skillCount = new HashMap<String, Integer>();
		
	}
	
	public void addDock(Dock dock) {
		docks.add(dock);
	}
	
	public void addShip(Ship ship) {
		ships.add(ship);
	}
	
	public void addPerson(Person person) {
		String skill = person.getSkill();
		
		persons.add(person);
		
		if (!skillPools.containsKey(skill)) {
			skillPools.put(skill, new ArrayList<Person>());
		}
		
		skillPools.get(skill).add(person);
	}
	
	public void count() {
		for (Map.Entry<String, ArrayList<Person>> entry : skillPools.entrySet()) {
			String s = entry.getKey();
			
			skillCount.put(s, entry.getValue().size());
		}
	}
	
	public HashMap<String, Integer> getCount() {
		return skillCount;
	}
	
	public void createPanel() {
		synchronized(skillPools) {
			for(Map.Entry<String, ArrayList<Person>> entry : skillPools.entrySet()) {
				
				panel.add(new JLabel(entry.getKey() + ": " + entry.getValue().size()));
			}
		}
	}
	
	public void queueShip(Ship ship) {
		queue.add(ship);
	}
	
	public String toString () {
		String st = "\n\nSeaPort: " + super.toString();
		for (Dock md: docks) st += "\n  " + md + "\n";
		st += "\n\n --- List of all ships in queue:";
		for (Ship ms: queue ) st += "\n   > " + ms;
		st += "\n\n --- List of all ships:";
		for (Ship ms: ships) st += "\n   > " + ms;
		st += "\n\n --- List of all persons:";
		for (Person mp: persons) st += "\n   > " + mp;
		return st;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public Queue<Ship> getQueue() {
		return queue;
	}
	
	public ArrayList<Dock> getDocks() {
		return docks;
	}
	
	public ArrayList<Dock> getAvailableDocks() {
		return availableDocks;
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public HashMap<String, ArrayList<Person>> getPools() {
		return skillPools;
	}
	
	public void fillDocks() {
		for(Dock d:docks) {
			Ship s = d.getShip();
			if (s.jobsComplete())
				undockShip(s);
		}
		
		while(!availableDocks.isEmpty() && !queue.isEmpty()) {
			dockShip(queue.poll());
		}
	}
	
	public void dockShip(Ship ship) {
		Dock dock;
		boolean noDocks;
		
		do {
			synchronized(availableDocks) {
				noDocks = availableDocks.isEmpty();
				if(availableDocks.isEmpty()) {
					try {
						availableDocks.wait();
					} catch (InterruptedException e) {}
				}
				dock = availableDocks.get(0);
				availableDocks.remove(dock);
			}
		} while(noDocks);
		
		synchronized(ship) {
			ship.assignDock(dock);
			dock.assignShip(ship);
			ship.notifyAll();
		}
	}
	
	public void undockShip(Ship ship) {
		Dock dock = ship.getDock();
		
		ship.assignDock(null);
		
		synchronized(queue) {
			if(!queue.isEmpty()) {
				Ship newShip = queue.poll();
				
				synchronized(newShip) {
					dock.assignShip(newShip);
					newShip.assignDock(dock);
					newShip.notifyAll();
				}
			}
			else {
				synchronized(availableDocks) {
					dock.assignShip(null);
					availableDocks.add(dock);
					availableDocks.notifyAll();
				}
			}
		}
	}
	
	public void checkAvailable() {
		for (Dock d:docks) {
			if (d.getShip() == null)
				if (!availableDocks.contains(d))
					availableDocks.add(d);
		}
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(queue, comparator);
		Collections.sort(ships, comparator);
		Collections.sort(docks, comparator);
		Collections.sort(persons, comparator);
	}
	
	public void sortShip(Comparator<Ship> comparator) {
		Collections.sort(queue, comparator);
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		DefaultMutableTreeNode queueNode = new DefaultMutableTreeNode("Queue");
		DefaultMutableTreeNode docksNode = new DefaultMutableTreeNode("Docks");
		DefaultMutableTreeNode personsNode = new DefaultMutableTreeNode("Persons");
		
		node.add(queueNode);
		node.add(docksNode);
		node.add(personsNode);
		
		
		for(Ship s:queue)
			queueNode.add(s.createNode());
		for(Dock d:docks)
			docksNode.add(d.createNode());
		for(Person p:persons)
			personsNode.add(p.createNode());
			
		return node;
	}

	public JPanel getPanel() {
		return panel;
	}
}
