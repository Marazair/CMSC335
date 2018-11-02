package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {
	private double duration;
	private ArrayList<String> requirements;
	
	public Job(int index, int parent, String name, double duration, ArrayList<String> requirements) {
		super(index, parent, name);
		this.duration = duration;
		this.requirements = requirements;
	}
	
	public Job(Scanner sc) {
		super(sc);
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
	}
}
