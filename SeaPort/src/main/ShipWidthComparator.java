/*
 * File: ShipWidthComparator.java
 * Date: 11/14/2018
 * Author: Nicholas Mills
 * Purpose: Provide a comparator for sorting ships by width.
 */

package main;

import java.util.Comparator;

public class ShipWidthComparator implements Comparator<Ship> {

	@Override
	public int compare(Ship arg0, Ship arg1) {
		double dif = arg0.getWidth() - arg1.getWidth();
		
		if (dif > 0)
			return 1;
		else if (dif == 0) 
			return 0;
		else
			return -1;
	}
	
}
