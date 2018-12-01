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
	private Person worker;
	private JPanel panel;
	private JProgressBar bar;
	private JButton stop;
	private JButton cancel;
	private JLabel workerLabel;
	private boolean goFlag;
	private boolean killFlag;
	Status status = Status.WAITING;
	
	enum Status {RUNNING, SUSPENDED, WAITING, DONE, CANCELLED};
	
	public Job(Scanner sc) {
		super(sc);
		
		worker = null;
		workerLabel = new JLabel("Worker: Unallocated");
		
		goFlag = true;
		killFlag = false;
		
		requirements = new ArrayList<String>();
		
		if(sc.hasNextDouble()) duration = sc.nextDouble();
		while(sc.hasNext()) requirements.add(sc.next());
		
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

		
		new Thread(this).start();
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
				break;
			case SUSPENDED:
				bar.setForeground (Color.yellow);
				break;
			case WAITING:
				bar.setForeground (Color.orange);
				break;
			case DONE:
				bar.setForeground (Color.blue);
				break;
			case CANCELLED:
				bar.setForeground(Color.red);
				break;
		}
	}

	@Override
	public void sort(Comparator<Thing> comparator) {
		Collections.sort(requirements);
	}
	
	public void assignWorker(Person worker) {
		this.worker = worker;
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long startTime = time;
		double stopTime = time + 1000 * duration;
		double endTime = stopTime - time;
		
		synchronized(this) {
			while(worker == null) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			workerLabel.setText("Worker: " + worker.getName());
		}
		
		synchronized (worker) {
			while(worker.isBusy()) {
				showStatus(Status.WAITING);
				try {
					worker.wait();
				}
				catch (InterruptedException ie) {}
			}
			worker.toggleBusy();
		}
		
		while (time < stopTime && !killFlag) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {}
			
			if (goFlag) {
				showStatus(Status.RUNNING);
				time += 100;
				bar.setValue((int)(((time - startTime) / endTime) * 100));
			}
			else {
				showStatus (Status.SUSPENDED);
			}
		}
		
		bar.setValue(100);
		showStatus(Status.DONE);
		
		synchronized (worker) {
			worker.toggleBusy();
			worker.notifyAll();
			workerLabel.setText("Worker: N/A");
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
