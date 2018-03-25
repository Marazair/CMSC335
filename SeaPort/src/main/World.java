package main;

import java.util.*;

public class World extends Thing {
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
	public World (Scanner sc) {
		super(sc);
		ports = new ArrayList<SeaPort>();
	}
	
	public int getTime() {
		return time.getTime();
	}
	
	public void addPort(SeaPort port) {
		ports.add(port);
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
