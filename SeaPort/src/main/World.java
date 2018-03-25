package main;

import java.util.ArrayList;

public class World extends Thing {
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
	public World (ArrayList<SeaPort> ports, PortTime time) {
		this.ports = ports;
		this.time = time;
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
