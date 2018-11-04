package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {
	private double duration;
	private ArrayList<String> requirements;
	
	public Job(Scanner sc) {
		super(sc);
		
		requirements = new ArrayList<String>();
		
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
	}
}
