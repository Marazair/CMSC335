/*
 * File: Dock.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Dock objects.
 */

package main;

import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

public class Dock extends Thing {
	private Ship ship;
	
	public Dock(Scanner sc) {
		super(sc);
	}
	
	public void addShip(Ship ship) {
		this.ship = ship;
	}
	
	public String toString() {
		String st = "Dock: " + super.toString();
		if(ship != null)
			st += "\n    Ship: " + ship;
		return st;
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode("Ship");
		
		node.add(shipNode);
		shipNode.add(ship.createNode());
		
		return node;
	}
	
}
