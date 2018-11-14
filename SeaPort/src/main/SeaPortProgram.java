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
	private JTextField searchField = new JTextField("");
	
	private JRadioButton indexRadio = new JRadioButton("Index");
	private JRadioButton nameRadio = new JRadioButton("Name");
	private JRadioButton parentRadio = new JRadioButton("Parent");
	
	ButtonGroup radio = new ButtonGroup();	
	
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
		
		radio.add(indexRadio);
		radio.add(nameRadio);
		radio.add(parentRadio);
		
		JButton searchButton = new JButton("Search");
		JButton reset = new JButton("Reset");
		
		searchButton.addActionListener(this);
		searchButton.setActionCommand("search");
		
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		
		JPanel userPanel = new JPanel(new GridLayout(3, 1));
		JPanel searchPanel = new JPanel(new GridLayout(1, 2));
		JPanel radioPanel = new JPanel(new GridLayout(1, 3));
		
		userPanel.add(searchPanel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		userPanel.add(radioPanel);
		radioPanel.add(indexRadio);
		radioPanel.add(nameRadio);
		radioPanel.add(parentRadio);
		
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
		if (e.getActionCommand().equals("search")) {
			try {
				if (indexRadio.isSelected()) {
					int index = Integer.parseInt(searchField.getText());
					jta.setText(world.indexSearch(index).toString());
				}
				else if (nameRadio.isSelected()) {
					String name = searchField.getText();
					String results = "";
					
					for(Thing mt: world.nameSearch(name))
						results += mt.toString() + "\n";
					
					jta.setText(results);
				}
				else if (parentRadio.isSelected()) {
					int index = Integer.parseInt(searchField.getText());
					String results = "";
					
					for(Thing mt: world.parentSearch(index))
						results += mt.toString() + "\n";
					
					jta.setText(results);
				}
				else {
					JOptionPane.showMessageDialog(this, "Please select a search option.");
				}
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
