package main;

import java.util.*;

public class World extends Thing {
	private ArrayList<SeaPort> ports;
	private PortTime time;
	
	public World (Scanner sc) {
		super(sc);
		ports = new ArrayList<SeaPort>();
	}
	
	public void process(String str) {
		Scanner sc = new Scanner(str);
		if(!sc.hasNext()) {
			sc.close();
			return;
		}
		switch(sc.next()) {
		case "port": 
			addPort(new SeaPort(sc));
			break;
		case "dock":
			addDock(new Dock(sc));
		}
	}
	
	public int getTime() {
		return time.getTime();
	}
	
	public void addPort(SeaPort port) {
		ports.add(port);
	}
	
	public void addDock(Dock dock) {
		
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
