/*
 * File: Person.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Person objects.
 */

package main;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.tree.DefaultMutableTreeNode;

public class Person extends Thing {
	private String skill;
	
	public Person(Scanner sc) {
		super(sc);
		if(sc.hasNext()) skill = sc.next();
	}
	
	public String toString() {
		String st = "Person: " + super.toString();
		st += " " + skill;
		return st;
	}
	
	public String getSkill() {
		return skill;
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		
		node.add(new DefaultMutableTreeNode("Skill: " + skill));
		
		return node;
	}
}
