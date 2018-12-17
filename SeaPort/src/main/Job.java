/*
 * File: Job.java
 * Date: 11/1/2018
 * Author: Nicholas Mills
 * Purpose: Contains logic for Job objects.
 */

package main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Job extends Thing implements Sorter, Runnable {	
	private double duration;
	private ArrayList<String> requirements;
	private HashMap<String, Integer> skillCount;
	private HashMap<String, ArrayList<Person>> workerPool;
	private HashMap<String, Person> assignedWorkers;
	private JPanel panel;
	private JPanel workerPanel;
	private DefaultListModel<String> assigned;
	private DefaultListModel<String> missing;
	private JPanel assignedPanel;
	private JPanel missingPanel;
	private GridBagConstraints c= new GridBagConstraints();
	private JProgressBar bar;
	private JButton stop;
	private JButton cancel;
	private Ship ship;
	private boolean goFlag;
	private boolean killFlag;
	Status status = Status.WAITING;
	
	enum Status {RUNNING, SUSPENDED, WAITING, DONE, CANCELLED};
	
	public Job(Scanner sc) throws NoSuchObject {
		super(sc);
		workerPool = null;
		assignedWorkers = new HashMap<String, Person>();
	
		workerPanel = new JPanel(new GridLayout(1, 2));
		
		goFlag = true;
		killFlag = false;
		
		requirements = new ArrayList<String>();
		
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
		
		skillCount = new HashMap<String, Integer>();
		
		for (String s:requirements) {
			if (skillCount.containsKey(s)) {
				int count;
				count = skillCount.get(s);
				skillCount.put(s, count + 1);
			}
			else {
				skillCount.put(s, 1);
			}
		}
		
		ship = World.getShipByIndex(getParent());
		
		for (String s:requirements) {
			assignedWorkers.put(s, null);
		}
		
		Collections.sort(requirements);
		
		panel = new JPanel(new GridBagLayout());
		panel.setBorder(new TitledBorder(getName()));
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		bar = new JProgressBar();
		bar.setStringPainted(true);
		
		panel.add(bar, c);
		
		c.gridy = 1;
		c.gridwidth = 1;
		
		stop = new JButton("Stop");
		
		panel.add(stop, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		cancel = new JButton("Cancel");
		
		panel.add(cancel, c);
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		
		panel.add(workerPanel, c);
		
		assignedPanel = new JPanel();
		assignedPanel.setBorder(new TitledBorder("Assigned"));
		
		assigned = new DefaultListModel<String>();
		JList<String> assignedList = new JList<String>(assigned);
		
		assignedPanel.add(assignedList);
		
		missingPanel = new JPanel();
		missingPanel.setBorder(new TitledBorder("Missing"));
		
		missing = new DefaultListModel<String>();
		JList<String> missingList = new JList<String>(missing);
		
		missingPanel.add(missingList);
		
		workerPanel.add(assignedPanel);
		workerPanel.add(missingPanel);
		
		for(Map.Entry<String, Person> entry : assignedWorkers.entrySet()) {
			String s = entry.getKey();
			missing.addElement(s);
		}
		
		stop.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				toggleGoFlag();
			}
		});
		
		cancel.addActionListener (new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				setKillFlag();
			}
		});
	}
	
	public String toString() {
		String st = "Job: " + super.toString();
		st += "\n  Duration: " + duration;
		st += "\n  Requirements: ";
		
		for (String str: requirements)
			st += "\n    -" + str;
		
		return st;
	}
	
	private void toggleGoFlag() {
		goFlag = !goFlag;
	}
	
	private void setKillFlag() {
		killFlag = true;
		showStatus(Status.CANCELLED);
	}
	
	void showStatus (Status st) {
		status = st;
		
		switch (status) {
			case RUNNING:
				bar.setForeground(Color.green);
				stop.setText("Stop");
				break;
			case SUSPENDED:
				bar.setForeground (Color.yellow);
				stop.setText("Resume");
				break;
			case WAITING:
				bar.setForeground (Color.orange);
				break;
			case DONE:
				bar.setForeground (Color.blue);
				bar.setString(bar.getString() + ": COMPLETE");
				break;
			case CANCELLED:
				bar.setForeground(Color.red);
				bar.setString(bar.getString() + ": CANCELLED");
				cancel.setEnabled(false);
				stop.setEnabled(false);
				break;
		}
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(requirements);
	}
	
	public void passPool(HashMap<String, ArrayList<Person>> workers) {
		workerPool = workers;
	}
	
	public void startThread() {
		Thread thread = new Thread(this);
		thread.setName(getName());
		thread.start();
	}
	
	private void releaseWorkers() {
		for(Map.Entry<String, Person> entry : assignedWorkers.entrySet()) {
			String s = entry.getKey();
			Person worker = entry.getValue();
			ArrayList<Person> list;
			
			list = workerPool.get(s);
			
			if (worker != null) {
				synchronized(list) {
					list.add(worker);
					list.notifyAll();
					assignedWorkers.put(s, null);
				}
			}
		}
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long startTime = time;
		double stopTime = time + 1000 * duration;
		double endTime = stopTime - time;
		boolean undocked;
		
		do {
			synchronized(ship) {
				undocked = (ship.getDock() == null);
				if (undocked) {
					try {
						ship.wait();
					} catch (InterruptedException e) {}
				}
			}
		} while(undocked);
		
		int workerCount = 0;
		
		ship.getPort().;
		
		outerloop:
		while(workerCount < requirements.size()) {
			for(String s:requirements) {
				ArrayList<Person> list;
				
				list = workerPool.get(s);
				
				if (list != null) {
					synchronized(list) {
						if(!list.isEmpty()) {
							workerCount++;
							assignedWorkers.put(s, list.get(0));
							if (!assigned.contains(s))
								assigned.addElement(s);
							
							missing.removeElement(s);
							
							list.remove(0);
						}
						else {
							workerCount = 0;
							showStatus(Status.WAITING);
							releaseWorkers();
							
							try {
								list.wait();
							} catch (InterruptedException e) { }
							
							break;
						}
					}
				}
				else {
					setKillFlag();
					panel.remove(workerPanel);
					
					JLabel insufficientWorkers = 
							new JLabel("<html>Unable to complete job "
							+ "<br>due to insufficient workers<br>of type " + s);
					
					insufficientWorkers.setHorizontalAlignment(JLabel.CENTER);
					
					panel.add(insufficientWorkers, c);
					break outerloop;
				}
			}
		} 
		
		while (time < stopTime && !killFlag) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {}
			if (!killFlag) {
				if (goFlag) {
					showStatus(Status.RUNNING);
					time += 100;
					bar.setValue((int)(((time - startTime) / endTime) * 100));
				}
				else {
					showStatus (Status.SUSPENDED);
				}
			}
		}
		
		releaseWorkers();
		
		if (!killFlag) {
			bar.setValue(100);
			showStatus(Status.DONE);
		}
		
		synchronized(ship) {
			if(ship.jobsComplete()) {
				ship.undock();
			}
		}
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	@Override
	public DefaultMutableTreeNode createNode() {
		DefaultMutableTreeNode node = super.createNode();
		DefaultMutableTreeNode requirementNode = new DefaultMutableTreeNode("Requirements");
		
		node.add(new DefaultMutableTreeNode("Duration: " + duration));
		node.add(requirementNode);
		
		for(String r:requirements)
			requirementNode.add(new DefaultMutableTreeNode(r));
		
		return node;
	}
}
