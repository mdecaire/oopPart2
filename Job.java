
/**
 * File name: Job.java
 * Date: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of the jobs class
 * and to return a list of requirements back to the ship class.
 * NO CHANGES ADDED DURING PROJECT 2
 * CHANGES: implemented runnable and made this a muti- threaded classes
 * displays the status of each of the jobs as they run
 * Project 4 changes: made the constructor smaller, added new functions to have 
 * jobs wait till resouces are acquired.
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;


public class Job extends Thing implements Runnable {
	Color normalColor;
	final static Object monitor = new Object();
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
	boolean running = false;
	boolean cancelled = false;
	boolean completed = false;
	boolean waiting = false;
	String dockName = "";
	String sk = "";
	JButton pauseButton = new JButton("Pause");
	JButton cancelButton = new JButton("Cancel");
	JLabel progLabel = new JLabel("Pending");
	boolean haveResources = false;
	JPanel panel = new JPanel(new GridLayout(1, 8));
	JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
	JLabel avail = new JLabel();
	String resAvail = "Acquired: ";
	String resNeed = "Needed: ";
	JLabel need = new JLabel();
	Status status = Status.SUSPENDED;
	JPanel wholePan = new JPanel(new GridLayout(2, 1));
	World world;
	CopyOnWriteArrayList<Person> acq = new CopyOnWriteArrayList<Person>();

	String skillNeeded = "";

	enum Status {
		RUNNING, SUSPENDED, WAITING, DONE
	};

	/**
	 * constructor
	 * @author Michelle
	 *
	 */
	public Job(String name, int parentIndex, Scanner sc, JPanel p, Ship s, int shipCount) {

		super(name, parentIndex);
		if (sc.hasNextDouble()) {
			duration = (sc.nextDouble());
		}
		sk = "Skills Required: ";
		while (sc.hasNext()) {
			skills = (sc.next());
			sk += skills + "~  ";
			requirements.add(skills);

		}
		jName = name;
		String sName = s.name;
		parentShip = s;
		parentDock = parentShip.getDock();
		parentPort = parentShip.getPort();
		acq = new CopyOnWriteArrayList<Person>();
		setLogic();
		statusBoard = p;
		statusBoard.setBackground(Color.white);
		statusBoard.setLayout(new GridLayout(shipCount, 1, 0, 5));
		buildGUI(sName);
		statusBoard.add(wholePan);
		try {
			statusBoard.revalidate();
			normalColor = pauseButton.getBackground();
		} catch (Exception e) {

		}
		pauseButton.addActionListener(e -> pauseButtonEvents());
		cancelButton.addActionListener(e -> cancelTheThread("Job Cancelled"));

		Thread t = new Thread(this);
		t.setName(this.name);
		t.start();

	}

	/**
	 * helper method to build part of the Gui makes it so that there is less code in
	 * the constructor
	 * 
	 * @param sName
	 */
	private synchronized void buildGUI(String sName) {

		pB = new JProgressBar();
		pB.setStringPainted(true);
		JLabel portLab = new JLabel("Port: " + parentPort.name);
		panel.add(portLab);
		dLabel = new JLabel(dockName);
		panel.add(dLabel);
		JLabel shipLab = new JLabel("Ship: " + sName);
		panel.add(shipLab);
		JLabel jobLab = new JLabel(jName);
		panel.add(jobLab);
		panel.add(pB);
		panel.add(progLabel);
		panel.add(pauseButton);
		panel.add(cancelButton);
		progLabel.setOpaque(true);
		JLabel skillLabel = new JLabel(sk);
		skillLabel.setForeground(Color.WHITE);
		avail.setText(resAvail);
		avail.setBorder(new EtchedBorder());
		avail.setForeground(Color.WHITE);
		need.setText(resNeed);
		need.setForeground(Color.white);
		need.setBorder(new EtchedBorder());
		skillLabel.setBorder(new EtchedBorder());
		bottomPanel.add(skillLabel);
		bottomPanel.add(avail);
		bottomPanel.setBackground(new Color(112, 128, 144));
		bottomPanel.add(need);
		wholePan.add(panel);
		wholePan.add(bottomPanel);
	}

	/**
	 * method that checks to make sure their are enough resources at the port to
	 * begin task
	 * 
	 * @return
	 */
	private boolean checkForResources() {
		synchronized (monitor) {
			boolean HaveSkills = true;
			if (!haveResources) {
				skillNeeded = parentPort.checkResources(requirements, monitor);
			}
			if (!skillNeeded.isEmpty()) {
				HaveSkills = false;
				cancelTheThread(skillNeeded + " Not Available");
			}
			return HaveSkills;
		}
	}

	/**
	 * helper method for constructor so establish certain flags for each job.
	 */
	private synchronized void setLogic() {
		if (requirements.isEmpty()) {
			this.haveResources = true;
			sk = "No Skills Required for this job";
			resAvail += "None Needed";
			resNeed += "No Resources Needed";
		} else {

			showJobStatus(Status.WAITING, "Waiting on Resource");
		}
		if (parentDock == null) {
			dockName = "Pending Dock";
			showJobStatus(Status.WAITING, "Waiting on Dock");
			this.waiting = true;
		} else {
			dockName = "Dock: " + parentDock.name;
		}
		if (!waiting) {
			if (haveResources) {
				running = true;
			}

		}
	}

	/**
	 * method to kill a thread used for both the cancel button and cancelled by
	 * program when not enough workers
	 * 
	 * @param s
	 */
	private synchronized void cancelTheThread(String s) {
		cancelled = true;
		running = false;
		Thread.currentThread().interrupt();
		cancelButton.setBackground(new Color(153, 0, 76));
		cancelButton.setForeground(Color.white);
		cancelButton.setText("Cancelled");
		progLabel.setBackground(new Color(153, 0, 76));
		progLabel.setForeground(Color.white);
		progLabel.setText(s);
		pauseButton.setEnabled(false);
		avail.setText("Job Cancelled");
		need.setText("Resources Not Available At This Port");
		dLabel.setText("CANCELLED");
		checkShips();// makes sure resources are all unallocated
		return;
	}

	/**
	 * method to set a job status to pause
	 */
	private void pauseButtonEvents() {
		running = !running;
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
	 * 
	 * @param st
	 * @param s
	 */
	private synchronized void showJobStatus(Status st, String s) {
		status = st;
		switch (status) {
		case SUSPENDED:
			progLabel.setForeground(new Color(255, 255, 0));
			progLabel.setBackground(Color.BLUE);
			progLabel.setText("Suspended: " + s);
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
			progLabel.setForeground(new Color(255, 69, 0));
			progLabel.setText(s);
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

	/**
	 * returns the requirements needed for each job
	 * 
	 * @return
	 */
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
		long currTime = System.currentTimeMillis();
		long startTime = currTime;
		long completionTime = (long) (duration * 100 + startTime);
		double finishTime = completionTime - currTime;
		double time;

		if (!checkForResources()) {// checks to make sure the port has the resources to perform the job
			return;
		}

		synchronized (monitor) { // guarded block to make sure a thread waits
			while (!running) {
				try {
					monitor.wait();

				} catch (InterruptedException ie) {

				}
				if (waiting) {
					checkDock();

				}
				if (!waiting) {
					if (!haveResources) {
						checkResources();
					}

				}
				if (this.haveResources && !waiting) {

					this.running = true;
				}

			}

		}

		while (currTime < completionTime && running) {// check to see if the status is right and runs the job

			try {
				Thread.sleep(800);

			} catch (InterruptedException e) {
			}
			if (running) {
				showJobStatus(Status.RUNNING, "");
				currTime += 800;
				double elapsedTime = ((double) (currTime) - (startTime));
				time = ((elapsedTime / (finishTime)) * 100);
				pB.setValue((int) (time));
			} else if (cancelled) {
				return;
			} else {
				while (!running) {
					if(!cancelled) {
						showJobStatus(Status.SUSPENDED, "Paused");
					}
					
				}

			}
		}

		pB.setValue(100);
		showJobStatus(Status.DONE, "");
		resetPerson();// releases the workers after their job
		checkShips();
		running = false;
		cancelButton.setEnabled(false);
		pauseButton.setEnabled(false);
		completed = true;

	}

	/**
	 * method to set a ship to a dock and sets them to not waiting
	 */
	private void checkDock() {
		synchronized (monitor) {
			for (Dock d : parentPort.docks) {
				if (d.getReady()) {
					this.parentDock = d;
					d.setShip(parentShip);
					for (Job j : parentShip.jobs) {
						j.waiting = false;
						j.dLabel.setText("Dock: " + d.name);
					}

					showJobStatus(Status.WAITING, "Dock Acquired");
					break;
				}
			}
		}

	}

	/**
	 * This method iterates through each ports person array to see if the resources
	 * are available.
	 */
	private void checkResources() {
		synchronized (monitor) {
			String skill = "";
			String aq = "Acquired: ";
			String nd = "Need: ";

			for (String st : requirements) {
				haveResources = false;
				skill = st.trim();
				for (Person p : parentPort.persons) {
					String pSkill=p.skill.trim();
					if (pSkill.equalsIgnoreCase(skill)) {	
						aq += skill + "~ ";
						acq.add(p);
						parentPort.persons.remove(p);
						haveResources = true;
						break;
					} else {
						haveResources = false;
					}
				}
				if (!haveResources) {
					nd += skill + " ";
					
					need.setText(nd);
					haveResources = false;
					showJobStatus(Status.WAITING, "Waiting on " + skill);
					resetPerson();
					break;
				}
			}
			avail.setText(aq);
			

			if (haveResources) {
				
				nd = "All Resources have been acquired";
				need.setText(nd);
			}
		}
	}

	/**
	 * helper method to reset all people back once they are done task
	 */
	private synchronized void resetPerson() {
		synchronized (monitor) {
			for (Person p : acq) {
				parentPort.persons.add(p);
			}
			this.acq.clear();
		}

	}

	/**
	 * method to clear the ships jobs and notify all threads that it might be their
	 * turn
	 * 
	 * @param acq
	 */

	private void checkShips() {
		synchronized (monitor) {
			parentShip.jobs.remove(this);
			if (parentShip.jobs.isEmpty()) {
				parentShip.setDock(null);// nulls the parent dock in ship
				if (parentDock != null) {
					parentDock.setReady(true);// sets flag to indicate it is ready for another ship
				}
				parentPort.addToQ(parentShip);// puts the ship in que
				monitor.notifyAll();

				return;
			}
		}

	}

}
