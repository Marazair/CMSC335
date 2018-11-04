package main;

import java.util.Scanner;

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
}
