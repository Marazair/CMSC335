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
import java.awt.*;
import java.awt.event.*;

public class SeaPortProgram extends JFrame implements ActionListener {
	private static final long serialVersionUID = -3927369720013543277L;
	
	private World world;
	private JTextArea jta = new JTextArea();
	private JTextField indexField = new JTextField("");
	private JTextField nameField = new JTextField("");
	private JTextField parentField = new JTextField("");
	
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
		
		
		JScrollPane jsp = new JScrollPane(jta);
		add(jsp, BorderLayout.CENTER);
		
		jta.setFont (new Font("Monospaced", 0, 12));
		jta.setText(world.toString());
		
		JLabel indexLabel = new JLabel("Index:");
		JLabel nameLabel = new JLabel("Name:");
		JLabel parentLabel = new JLabel("Parent:");
		
		JButton indexSB = new JButton("Search");
		JButton nameSB = new JButton("Search");
		JButton parentSB = new JButton("Search");
		JButton reset = new JButton("Reset");
		
		indexSB.addActionListener(this);
		indexSB.setActionCommand("index");
		
		nameSB.addActionListener(this);
		nameSB.setActionCommand("name");
		
		parentSB.addActionListener(this);
		parentSB.setActionCommand("parent");
		
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		
		JPanel userPanel = new JPanel(new GridLayout(2, 1));
		JPanel searchPanel = new JPanel(new GridLayout(3,3));
		
		userPanel.add(searchPanel);
		
		searchPanel.add(indexLabel);
		searchPanel.add(indexField);
		searchPanel.add(indexSB);
		
		searchPanel.add(nameLabel);
		searchPanel.add(nameField);
		searchPanel.add(nameSB);
		
		searchPanel.add(parentLabel);
		searchPanel.add(parentField);
		searchPanel.add(parentSB);
		
		userPanel.add(reset);
		
		add(userPanel, BorderLayout.NORTH);
		
		setTitle("Seaport World");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SeaPortProgram spp = new SeaPortProgram();
		spp.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("index")) {
			try {
				int index = Integer.parseInt(indexField.getText());
				jta.setText(world.indexSearch(index).toString());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid input. Please use a number.");
			} catch (NoSuchObject nso) {
				JOptionPane.showMessageDialog(this, nso.getMessage());
			}
		}
		else if (e.getActionCommand().equals("name")) {
			try {
				String name = nameField.getText();
				String results = "";
				
				for(Thing mt: world.nameSearch(name))
					results += mt.toString() + "\n";
				
				jta.setText(results);
			}
			catch (NoSuchObject nso) {
				JOptionPane.showMessageDialog(this, nso.getMessage());
			}
		}
		else if (e.getActionCommand().equals("parent")) {
			try {
				int index = Integer.parseInt(parentField.getText());
				String results = "";
				
				for(Thing mt: world.parentSearch(index))
					results += mt.toString() + "\n";
				
				jta.setText(results);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid input. Please use a number.");
			} catch (NoSuchObject nso) {
				JOptionPane.showMessageDialog(this, nso.getMessage());
			}
		}
		else if (e.getActionCommand().equals("reset")) {
			jta.setText(world.toString());
		}
	}

}
