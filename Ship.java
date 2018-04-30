
/**
 * File Name: Ship.java
 * Date: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: to define a parent ship class and hold 
 * all jobs attached to the ship. Perfoms searches on
 * the jobs arraylist.
 * PROJECT TWO ADDITION: sorting methods added for jobs. Default sort is
 * ascending but descending is available
 * PROJECT THREE ADDITIONS: added a method to build nodes for the jtree and 
 * added a fixed array and added parent dock and port
 */

import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Ship extends Thing {
	private PortTime arrivalTime;
	private PortTime dockTime;
	private double weight;
	private double length;
	private double width;
	private double draft;
	protected CopyOnWriteArrayList<Job> jobs = new CopyOnWriteArrayList<Job>();
	protected CopyOnWriteArrayList<Job> fixedJobs = new CopyOnWriteArrayList<Job>();
	private boolean amPassenger = false;
	protected Dock parentDock;
	private SeaPort parentPort;
	
	Ship() {
	}

	Ship(String name, int index,Scanner sc) {
		super(name, index);

		if (sc.hasNextDouble())
			this.setWeight(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setLength(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setWidth(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setDraft(sc.nextDouble());

	}

	// established whether it is of type passenger or cargo
	public void setWhetherPassenger(boolean amPass) {
		this.amPassenger = amPass;
	}

	public boolean getWhetherPassenger() {
		return amPassenger;
	}

	public void setDock(Dock d) {
		parentDock=d;
	}
	
	public Dock getDock() {
		return parentDock;
	}
	public void setPort(SeaPort p) {
		parentPort=p;
	}
	
	public SeaPort getPort() {
		return parentPort;
	}
	

	// getters and setters
	public void setArrivalTime(long time) {
		arrivalTime = new PortTime(time);
	}

	public PortTime getArrivalTime() {
		return arrivalTime;
	}

	public void setDockTime(long time) {

		dockTime = new PortTime(time);
	}

	public PortTime getDockTime() {
		return dockTime;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDraft() {
		return draft;
	}

	public void setDraft(double draft) {
		this.draft = draft;
	}

	// adds jobs to list
	public void addJob(Job j) {
		jobs.add(j);
		fixedJobs.add(j);
	}

	public String toString() {
		return super.toString()+"\n "+ getJobs("all")+"\n";
	}

	// returns a list of jobs belonging to this ship
	public String getJobs(String otherParent) {
		String job = "";
		String parentJob = "";
		if (jobs.isEmpty()) {
			return "";
		} else {
			if (this.name.equals(otherParent)) {
				for (Job j : fixedJobs) {
					parentJob += "> " + j;
				}
			} else if(otherParent.equals("all")){
				for (Job j : fixedJobs) {
					job += "> " + j;
				}
			}
		}
		if(otherParent.equals("all")) {
			return job;
		}else 
			return parentJob;

		
	}

	// searches for a job by the name to report back to port
	public String jobByName(String text) {
		String nameResult = "";
		for (Job j : fixedJobs) {
			if (j.name.equalsIgnoreCase(text)) {
				nameResult ="Ship: "+ this.name+ "\n"+j;
				break;
			}
		}
		return nameResult;
	}

	// method to initialize the job search
	public String sortJobs(String howToSort, String orderToSort) {
		String sortOrder = "";
		if (howToSort.equalsIgnoreCase("name")) {
			sortOrder += sortByName(orderToSort);
		} else if (howToSort.equalsIgnoreCase("Duration")) {
			sortOrder += sortByDuration(orderToSort);
		}
		return sortOrder;
	}

	// helper method to sort by duration
	private String sortByDuration(String orderToSort) {
		String sortOrder = "";
		jobs.sort(new JobComparatorByDuration());
		if (orderToSort.equalsIgnoreCase("descending")) {
			jobs.sort(Collections.reverseOrder(new JobComparatorByDuration()));
		}
		for (Job j : fixedJobs) {
			sortOrder += j + "Estimated Time to Completion: " + Double.toString(j.getDuration()) + "\n";
		}
		return sortOrder;
	}

	// helper method to sort jobs by name
	private String sortByName(String orderToSort) {
		String sortOrder = "";
		Collections.sort(jobs);
		if (orderToSort.equalsIgnoreCase("descending")) {
			Collections.reverse(jobs);
		}
		for (Job j : fixedJobs) {
			sortOrder += j + "\n";
		}
		return sortOrder;
	}

	/** 
	 * class added to build nodes for the tree pane
	 */
	public synchronized void getJobNodes(DefaultMutableTreeNode shipNode) {
		DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode("Job");
		DefaultMutableTreeNode jNode = null;
		if (jobs.isEmpty()) {
			return;
		}
		for (Job j : fixedJobs) {
			jNode = new DefaultMutableTreeNode(j.name);
			jobNode.add(jNode);
		}
		shipNode.add(jobNode);
		return;
	}

	public String getSkills(String parent) {
		String skills = "";
		for (Job j : jobs) {
			if (j.name.equals(parent)) {
				skills += j.getRequirements();
			}
		}
		if (skills.isEmpty()&& parent.equals("all")) {
			skills = "None listed";
		}else if(skills.isEmpty()) {
			skills="";
		}
		return skills;
	}

}
