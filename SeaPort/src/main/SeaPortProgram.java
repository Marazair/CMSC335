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
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;

public class SeaPortProgram extends JFrame implements ActionListener {
	private static final long serialVersionUID = -3927369720013543277L;
	
	private World world;
	
	private JTree tree;
	private DefaultTreeModel treeModel;
	private JPanel jobPanel = new JPanel(new FlowLayout());
	
	private JTextField searchField = new JTextField("");
	private String[] searchStrings = {"Name", "Parent"};
	private JComboBox<String> searchList = new JComboBox<String>(searchStrings);
	
	private JTextField targetField = new JTextField("");
	private String[] sortTargets = {"World", "Port", "Ship"};
	private JComboBox<String> targetList = new JComboBox<String>(sortTargets);
	
	private JComboBox<String> sortTypes = new JComboBox<String>();
	private String sortType;
	
	public SeaPortProgram() {
		JFileChooser chooser = new JFileChooser(".");
		File file = null;
		
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
				JOptionPane.showMessageDialog(this, nso.getMessage());
			}
		}
		
		tree = new JTree(world.createNode());
		treeModel = (DefaultTreeModel) tree.getModel();
		
		JScrollPane textScroll = new JScrollPane(tree);
		add(textScroll, BorderLayout.CENTER);
		
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
		
		JPanel userPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel searchPanel = new JPanel(new GridLayout(1, 4));
		JPanel sortPanel = new JPanel(new GridLayout(2, 3));
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		userPanel.add(searchPanel, c);
		searchPanel.setBorder(new TitledBorder("Search"));
		searchPanel.add(new JLabel("Type:"));
		searchPanel.add(searchList);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		targetList.setSelectedItem("World");
		
		c.gridy = 1;
		c.gridheight = 2;
		
		userPanel.add(sortPanel, c);
		sortPanel.setBorder(new TitledBorder("Sort"));
		sortPanel.add(new JLabel("Target:"));
		sortPanel.add(targetList);
		sortPanel.add(targetField);
		sortPanel.add(new JLabel("Type:"));
		sortPanel.add(sortTypes);
		sortPanel.add(sortButton);
		
		c.gridy = 3;
		c.gridheight = 1;
		
		userPanel.add(reset, c);
		
		add(userPanel, BorderLayout.NORTH);
		
		jobPanel.setLayout(new BoxLayout(jobPanel, BoxLayout.Y_AXIS));
		
		
		for(JPanel p:world.getJobPanels())
			jobPanel.add(p);
		
		JScrollPane jobScroll = new JScrollPane(jobPanel);
		add(jobScroll, BorderLayout.EAST);
		
		
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
			DefaultMutableTreeNode searchNode = (DefaultMutableTreeNode)treeModel.getRoot();
			
			try {
				if (searchList.getSelectedItem().equals("Name")) {
					String name = searchField.getText();
					searchNode = new DefaultMutableTreeNode("World");
					
					for(Thing mt: World.nameSearch(name))
						searchNode.add(mt.createNode());
					
				}
				
				else if (searchList.getSelectedItem().equals("Parent")) {
					String name = searchField.getText();
					searchNode = new DefaultMutableTreeNode("World");
					
					for(Thing mt: World.nameSearch(name))
						searchNode.add(World.indexSearch(mt.getParent()).createNode());
					
				}
				
				updateTree(searchNode);
				
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
				updateTree(world.createNode());
			}
			else if (targetType.equals("Port")){
				try {
					SeaPort target = World.getPortByName(targetString);
					
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
					
					updateTree(target.createNode());
				} catch (NoSuchObject nso) {
					JOptionPane.showMessageDialog(this, "Target not found.");
				}
			}
			else if (targetType.equals("Ship")) {
				try {
					Ship target = World.getShipByName(targetString);
					
					generalSort(target);
					updateTree(target.createNode());
				} catch (NoSuchObject nso) {
					JOptionPane.showMessageDialog(this, "Target not found.");
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
		else if (e.getActionCommand().equals("reset")) {
			updateTree(world.createNode());
		}
		
	}
	
	private void updateTree(DefaultMutableTreeNode node) {
		treeModel.setRoot(node);
		treeModel.reload();
	}
	
	private void generalSort(Sorter target) {
		if (sortType.equals("Name"))
			target.sort(new NameComparator());
		else if (sortType.equals("Index"))
			target.sort(new IndexComparator());
	}
}
