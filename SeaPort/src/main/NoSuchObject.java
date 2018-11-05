/*
 * File: NoSuchObject.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Provides an exception to be thrown
 * 			in the event of missing or incorrect
 * 			objects.
 */

package main;

public class NoSuchObject extends Exception {

	private static final long serialVersionUID = 1L;
	
	String type = "";
	String name = "";
	int index = -1;
	int parent = -1;
	
	public NoSuchObject(String type, boolean parent, int index) {
		if(parent == true)
			this.parent = index;
		else
			this.index = index;
		this.type = type;
	}
	
	public NoSuchObject(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getMessage() {
		if (index >= 0)
			return "The " + type + " " + index + " was not found.";
		else if (parent >= 0)
			return "The " + type + " with the parent of " + parent + " was not found.";
		else
			return "The " + type + " with the name " + name + " was not found.";
	}

}
