/*
 * File: SeaPortProgram.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Create a GUI by which one can interact with 
 * 			the World of SeaPorts.
 */

package main;
import javax.swing.*;

public class SeaPortProgram extends JFrame {
	private static final long serialVersionUID = -3927369720013543277L;

	public SeaPortProgram() {
		setTitle("Sea Port World");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		SeaPortProgram spp = new SeaPortProgram();
		
		spp.add(chooser);

	}

}
