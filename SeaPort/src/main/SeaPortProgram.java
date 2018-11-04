/*
 * File: SeaPortProgram.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Create a GUI by which one can interact with 
 * 			the World of SeaPorts.
 */

package main;

import java.io.*;
import java.util.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class SeaPortProgram extends JFrame implements ActionListener {
	private static final long serialVersionUID = -3927369720013543277L;
	
	private World world;
	JTextArea jta = new JTextArea();
	
	public SeaPortProgram() {
		JFileChooser chooser = new JFileChooser(".");
		File file = null;
		StringBuffer nsoMessages = new StringBuffer("");
		boolean nsoFound = false;
		
		int returnVal = chooser.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			
			try {
				world = new World(new Scanner(file));
			}
			catch (FileNotFoundException fnfe) {
				JOptionPane.showMessageDialog(this, file.getName() + "not found.");
			}
			catch (NoSuchObject nso) {
				nsoMessages.append(nso.getMessage() + "\n");
				nsoFound = true;
			}
			
			if (nsoFound == true) {
				JOptionPane.showMessageDialog(this, nsoMessages.toString());
			}
		}
		
		setTitle("Seaport World");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JScrollPane jsp = new JScrollPane(jta);
		add(jsp, BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		
		SeaPortProgram spp = new SeaPortProgram();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
