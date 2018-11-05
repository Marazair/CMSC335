/*
 * File: PortTime.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for PortTime objects.
 */

package main;

public class PortTime {
	private int time;
	
	public PortTime(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
	
	public String toString() {
		return "Time: " + time;
	}
}
