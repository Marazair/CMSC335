package main;

public class NoSuchObject extends Exception {

	private static final long serialVersionUID = 1L;
	
	String type;
	int index;
	
	public NoSuchObject(String type, int index) {
		this.index = index;
		this.type = type;
	}
	
	public String getMessage() {
		return "The " + type + " " + index + " was not found.";
	}

}
