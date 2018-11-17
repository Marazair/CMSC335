/*
 * File: Job.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Job objects.
 */

package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Job extends Thing implements Sorter {
	private double duration;
	private ArrayList<String> requirements;
	
	public Job(Scanner sc) {
		super(sc);
		
		requirements = new ArrayList<String>();
		
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
	}
	
	public String toString() {
		String st = "Job: " + super.toString();
		st += "\n  Duration: " + duration;
		st += "\n  Requirements: ";
		
		for (String str: requirements)
			st += "\n    -" + str;
		
		return st;
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(requirements);
	}
}
