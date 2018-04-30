
/**
 * File name: Job.java
 * Date: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of the jobs class
 * and to return a list of requirements back to the ship class.
 * NO CHANGES ADDED DURING PROJECT 2
 * CHANGES: implemented runnable and made this a muti- threaded classes
 * displays the status of each of the jobs as they run
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Job extends Thing implements Runnable {
 Color normalColor;
	final static Object monitor = new Object();
	final static Object pauseMonitor = new Object();
	private double duration;
	private String skills;
	protected ArrayList<String> requirements = new ArrayList<String>();
	private Dock parentDock;
	private Ship parentShip;
	private SeaPort parentPort;
	private JPanel statusBoard;
	private JProgressBar pB;
	private JLabel dLabel;
	private String jName = "";
	boolean running=true;
	boolean cancelled = false;
	boolean completed = false;
	boolean waiting;
	JButton pauseButton = new JButton("Pause");
	JButton cancelButton = new JButton("Cancel");
	JLabel progLabel = new JLabel("Pending");
	boolean waitingToContinue;
	JPanel panel = new JPanel(new GridLayout(1, 7, 2, 2));
	Status status = Status.SUSPENDED;
	DefaultBoundedRangeModel model = new DefaultBoundedRangeModel();

	enum Status {
		RUNNING, SUSPENDED, WAITING, DONE
	};

	public Job(String name, int parentIndex, Scanner sc, JPanel p, Ship s, int shipCount) {

		super(name, parentIndex);
		if (sc.hasNextDouble()) {
			duration = (sc.nextDouble());
		}
		while (sc.hasNext()) {
			skills = (sc.next());
			requirements.add(skills);
		}
		jName = name;
		String sName = s.name;
		String dockName = "";
		parentShip = s;
		parentDock = parentShip.getDock();
		if (parentDock == null) {
			dockName = "Pending Dock";
			showJobStatus(Status.WAITING, "Waiting on Dock");
			waiting = true;	
		} else {
			dockName = "Dock: " + parentDock.name;
			waiting = false;
		}
		waitingToContinue = false;
		progLabel.setOpaque(true);
		parentPort = parentShip.getPort();
		statusBoard = p;
		statusBoard.setBackground(Color.white);
		statusBoard.setLayout(new GridLayout(shipCount, 1, 10, 5));
		pB = new JProgressBar();
		pB.setStringPainted(true);
		panel.add(new JLabel("Port: " + parentPort.name));
		dLabel = new JLabel(dockName);
		panel.add(dLabel);
		panel.add(new JLabel("Ship: " + sName));
		panel.add(new JLabel("Job: " + jName));
		panel.add(pB);
		panel.add(progLabel);
		panel.add(pauseButton);
		panel.add(cancelButton);
		statusBoard.add(panel);
		statusBoard.revalidate();
		statusBoard.repaint();
		pB.setValue(10);
		normalColor=pauseButton.getBackground();
		pauseButton.addActionListener(e -> pauseButtonEvents());
		cancelButton.addActionListener(e -> cancelTheThread());
		Thread t = new Thread(this); 
		 t.setName(this.name);
		 t.start();


	}

	//method to kill a thread
	private synchronized void cancelTheThread() {
		cancelled = true;
		running=false;
		Thread.currentThread().interrupt();
		cancelButton.setBackground(new Color(153, 0, 76));
		progLabel.setBackground(new Color(153, 0, 76));
		progLabel.setForeground(Color.white);
		progLabel.setText("Job Cancelled");
		pauseButton.setEnabled(false);
	}

	/**
	 * method to set a job status to pause
	 */
	private void pauseButtonEvents() {
		running=!running;	
		if (!running) {
			showJobStatus(Status.WAITING, "Paused");
			pauseButton.setText("Resume");
		}

	}

	public synchronized double getDuration() {
		return duration;
	}

	public synchronized void setDuration(double duration) {
		this.duration = duration;
	}

	public synchronized String getSkills() {
		return skills;
	}

	public synchronized void setSkills(String skills) {
		this.skills = skills;
		requirements.add(skills);
	}

	/**
	 * method to indicate the status of the job on the gui
	 * @param st
	 * @param s
	 */
	private synchronized void showJobStatus(Status st, String s) {
		status = st;
		switch (status) {
		case SUSPENDED:
			progLabel.setForeground(new Color(255, 255, 0));
			progLabel.setBackground(Color.BLUE);
			progLabel.setText("Suspended: "+ s);
			break;
		case RUNNING:
			pauseButton.setText("Pause");
			progLabel.setOpaque(true);
			pauseButton.setBackground(normalColor);
			progLabel.setBackground(normalColor);
			progLabel.setForeground(new Color(0, 255, 0));
			progLabel.setText("Status: Running");
			break;
		case WAITING:
			progLabel.setForeground(new Color(250, 170, 2));
			progLabel.setText("Status :" + s);
			break;
		case DONE:
			progLabel.setText("Status: Completed");
			progLabel.setForeground(new Color(127, 0, 255));
			progLabel.setOpaque(false);
			pauseButton.setBackground(new Color(127, 0, 255));
			pauseButton.setText("Completed");
			pauseButton.setForeground(Color.WHITE);
			break;

		}

	}

	@Override
	public synchronized String toString() {
		return "Job: " + super.toString() + "\n" + getRequirements();
	}

	// returns the requirements needed for each job
	public synchronized String getRequirements() {
		String jList = "";
		if (requirements.isEmpty()) {
			jList = "No skills Listed.\n";
			return jList;
		} else {
			jList += ">Skills: ";
			for (String s : requirements) {
				jList += " ~ " + s;
			}
			jList += "\n";
		}
		return jList;
	}

	
/**
 * runnable method to make sure that each job is running or waiting it's turn
 */
	@Override
	public void run() {
		long currTime=System.currentTimeMillis();
		long startTime =currTime ;
		long completionTime = (long) (duration*100 + startTime);
		double finishTime=completionTime-currTime;
		double time;

		synchronized (monitor) { //guarded block to make sure a thread waits
			while (waiting ) {
				pB.setValue(1);
				try {
					monitor.wait();

				} catch (InterruptedException ie) {

				}

				//condition to get out of the wait process
				for (Dock d : parentPort.docks) {
					if (d.getReady()) {
						this.parentDock = d;
						d.setShip(parentShip);
						for (Job j : parentShip.jobs) {
							j.waiting = false;
						}
						dLabel.setText("Dock: "+d.name);
						this.running = true;
						break;
					}
				}

			}
		}

		//check to see if the status is right and runs the job
		while (currTime < completionTime ) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			if (running) {
				showJobStatus(Status.RUNNING, "");
				currTime +=200;
				double elapsedTime=((double) (currTime)-(startTime));
				time = ((elapsedTime / (finishTime)) * 100);
				pB.setValue((int) (time));
			}else if(cancelled) {
				checkShips(monitor); 
				return;
			}
			else {
				while (!running) {
					showJobStatus(Status.SUSPENDED, "Paused");
				}

			}
		}

		pB.setValue(100);
		showJobStatus(Status.DONE, "");
		checkShips(monitor);
		running = false;
		cancelButton.setEnabled(false);
		pauseButton.setEnabled(false);
		completed = true;
		

	}
	/**
	 * method to clear the ships jobs and  
	 * notify all threads that it might be their turn
	 * @param monitor
	 */

	private void checkShips(Object monitor) {
		synchronized (monitor) {
			parentShip.jobs.remove(this);
			if (parentShip.jobs.isEmpty()) {
				parentShip.setDock(null);// nulls the parent dock in ship
				parentDock.setReady(true);// sets flag to indicate it is ready for another ship
				parentPort.addToQ(parentShip);// puts the ship in que
				monitor.notifyAll();
			}
		}

	}

}
