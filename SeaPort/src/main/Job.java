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
		
		panel = new JPanel(new GridLayout(2,3));
		panel.setBorder(new TitledBorder(getName()));
		
		bar = new JProgressBar();
		bar.setStringPainted(true);
		
		stop = new JButton("Stop");
		cancel = new JButton("Cancel");
		
		panel.add(bar);
		panel.add(stop);
		panel.add(cancel);
		panel.add(workerLabel);
		
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
		
		while(worker == null) {
			try {
				wait();
			} catch (InterruptedException e) {}
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
		}
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
