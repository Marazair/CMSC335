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
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

public class SeaPortProgram extends JFrame implements ActionListener {
	private static final long serialVersionUID = -3927369720013543277L;
	
	private World world;
	private JTextArea jta = new JTextArea();
	
	private JTextField searchField = new JTextField("");
	private String[] searchStrings = {"Index", "Name", "Parent"};
	private JComboBox<String> searchList = new JComboBox<String>(searchStrings);
	
	private JTextField targetField = new JTextField("");
	private String[] sortTargets = {"Port", "World", "Ship"};
	private JComboBox<String> targetList = new JComboBox<String>(sortTargets);
	
	private JComboBox<String> sortTypes = new JComboBox<String>();
	private String sortType;
	
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
		
		JButton searchButton = new JButton("Search");
		JButton sortButton = new JButton("Sort");
		JButton reset = new JButton("Reset");
		
		searchButton.addActionListener(this);
		searchButton.setActionCommand("search");
		
		sortButton.addActionListener(this);
		sortButton.setActionCommand("sort");
		
		targetList.addActionListener(this);
		targetList.setActionCommand("target");
		
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		
		JPanel userPanel = new JPanel(new GridLayout(3, 1));
		JPanel searchPanel = new JPanel(new GridLayout(1, 4));
		JPanel sortPanel = new JPanel(new GridLayout(2, 3));
		
		userPanel.add(searchPanel);
		searchPanel.setBorder(new TitledBorder("Search"));
		searchPanel.add(new JLabel("Type:"));
		searchPanel.add(searchList);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		targetList.setSelectedItem("Port");
		
		userPanel.add(sortPanel);
		sortPanel.setBorder(new TitledBorder("Sort"));
		sortPanel.add(new JLabel("Target:"));
		sortPanel.add(targetList);
		sortPanel.add(targetField);
		sortPanel.add(new JLabel("Type:"));
		sortPanel.add(sortTypes);
		sortPanel.add(sortButton);
		
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
				if (searchList.getSelectedItem().equals("Index")) {
					int index = Integer.parseInt(searchField.getText());
					jta.setText(world.indexSearch(index).toString());
				}
				else if (searchList.getSelectedItem().equals("Name")) {
					String name = searchField.getText();
					String results = "";
					
					for(Thing mt: world.nameSearch(name))
						results += mt.toString() + "\n";
					
					jta.setText(results);
				}
				else if (searchList.getSelectedItem().equals("Parent")) {
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
		else if (e.getActionCommand().equals("sort")) {
			String targetString = targetField.getText();
			String targetType = (String)targetList.getSelectedItem();
			sortType = (String)sortTypes.getSelectedItem();
			
			if (targetType.equals("World")) {
				generalSort(world);
				jta.setText(world.toString());
			}
			else if (targetType.equals("Port")){
				try {
					SeaPort target = world.getPortByName(targetString);
					
					generalSort(target);
					
					if (sortType.equals("Length")) {
						target.sortShip(new ShipLengthComparator());
					}
					else if (sortType.equals("Width")) {
						target.sortShip(new ShipWidthComparator());
					}
					else if (sortType.equals("Draft")) {
						target.sortShip(new ShipDraftComparator());
					}
					else if (sortType.equals("Weight")) {
						target.sortShip(new ShipDraftComparator());
					}
					
					jta.setText(target.toString());
				} catch (NoSuchObject nso) {
					JOptionPane.showMessageDialog(this, nso.getMessage());
				}
			}
			else if (targetType.equals("Ship")) {
				try {
					Ship target = world.getShipByName(targetString);
					
					generalSort(target);
					jta.setText(target.toString());
				} catch (NoSuchObject nso) {
					JOptionPane.showMessageDialog(this, nso.getMessage());
				}
			}
		}
		else if (e.getActionCommand().equals("target")) {
			targetField.setEditable(true);
			sortTypes.removeAllItems();
			sortTypes.addItem("Name");
			sortTypes.addItem("Index");
			
			if (targetList.getSelectedItem().equals("Port")) {
				sortTypes.addItem("Length");
				sortTypes.addItem("Width");
				sortTypes.addItem("Draft");
				sortTypes.addItem("Weight");
			}
			else if (targetList.getSelectedItem().equals("Ship"))
				sortTypes.addItem("Skill");
			else if (targetList.getSelectedItem().equals("World"))
				targetField.setEditable(false);
		}
		else if (e.getActionCommand().equals("reset"))
			jta.setText(world.toString());
	}
	
	private void generalSort(Sorter target) {
		if (sortType.equals("Name"))
			target.sort(new NameComparator());
		else if (sortType.equals("Index"))
			target.sort(new IndexComparator());
	}
}
