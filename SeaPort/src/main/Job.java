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
	private HashMap<String, ArrayList<Person>> workerPool;
	private HashMap<String, Person> assignedWorkers;
	private JPanel panel;
	private JProgressBar bar;
	private JButton stop;
	private JButton cancel;
	private JLabel workerLabel;
	private Ship ship;
	private Dock dock;
	private SeaPort port;
	private boolean goFlag;
	private boolean killFlag;
	Status status = Status.WAITING;
	
	enum Status {RUNNING, SUSPENDED, WAITING, DONE, CANCELLED};
	
	public Job(Scanner sc) throws NoSuchObject {
		super(sc);
		
		workerPool = null;
		assignedWorkers = new HashMap<String, Person>();
	
		workerLabel = new JLabel("Worker: Unallocated");
		
		goFlag = true;
		killFlag = false;
		
		requirements = new ArrayList<String>();
		
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
		
		ship = World.getShipByIndex(getParent());
		
		for (String s:requirements) {
			assignedWorkers.put(s, null);
		}
		
		panel = new JPanel(new GridBagLayout());
		panel.setBorder(new TitledBorder(getName()));
		
		GridBagConstraints c= new GridBagConstraints();
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
		
		c.gridx = 2;
		c.gridy = 1;
		
		panel.add(workerLabel, c);
		
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

		Thread thread = new Thread(this);
		thread.setName(getName());
		thread.start();
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
				workerLabel.setText("Worker: N/A");
				break;
			case CANCELLED:
				bar.setForeground(Color.red);
				bar.setString(bar.getString() + ": CANCELLED");
				workerLabel.setText("Worker: N/A");
				cancel.setEnabled(false);
				stop.setEnabled(false);
				break;
		}
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(requirements);
	}
	
	public void passPool(HashMap<String, ArrayList<Person>> worker) {
		this.workerPool = worker;
		//workerLabel.setText("Worker: " + worker.getName());
	}
	
	private void releaseWorkers() {
		for(String s:requirements) {
			Person worker = assignedWorkers.get(s);
			ArrayList<Person> list = workerPool.get(s);
			
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
		
		synchronized(ship) {
			 
		}
		
		
		int workerCount = 0;
		
		while(workerCount < requirements.size()) {
			for(String s:requirements) {
				
				ArrayList<Person> list = workerPool.get(s);
				
				if (list != null) {
					synchronized(list) {
						if(!list.isEmpty()) {
							workerCount++;
							assignedWorkers.put(s, list.get(0));
							list.remove(0);
						}
						else {
							workerCount = 0;
							showStatus(Status.WAITING);
							releaseWorkers();
							
							try {
								list.wait();
							} catch (InterruptedException e) { }
						}
					}
				}
				else {
					showStatus(Status.CANCELLED);
					
				}
			}
		} 
		
		while (time < stopTime && !killFlag) {
			try {
				Thread.sleep(100);
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
		
		if (ship.jobsComplete()) {
			
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
