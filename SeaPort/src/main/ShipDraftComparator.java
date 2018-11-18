/*
 * File: ShipDraftComparator.java
 * Date: 11/14/2018
 * Author: Nicholas Mills
 * Purpose: Provide a comparator for sorting ships by draft.
 */
package main;

import java.util.Comparator;

public class ShipDraftComparator implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		double dif = s1.getDraft() - s2.getDraft();
		
		if (dif > 0)
			return 1;
		else if (dif == 0) 
			return 0;
		else
			return -1;
	}
	
}
