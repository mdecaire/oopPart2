
/**
 * File Name: Ship.java
 * Date: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: to define a parent ship class and hold 
 * all jobs attached to the ship. Perfoms searches on
 * the jobs arraylist.
 * PROJECT TWO ADDITION: sorting methods added for jobs. Default sort is
 * ascending but descending is available
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ship extends Thing {
	private PortTime arrivalTime;
	private PortTime dockTime;
	private double weight;
	private double length;
	private double width;
	private double draft;
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private boolean amPassenger = false;

	Ship() {
	}

	Ship(String name, Scanner sc) {
		super(name, sc);

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
	}

	public String toString() {
		return super.toString() + "\n";
	}

	// returns a list of jobs belonging to this ship
	public String getJobs() {
		String job = "";
		if (jobs.isEmpty()) {
			return "";
		} else {
			for (Job j : jobs) {
				job += "> " + j;
			}
		}

		return job;
	}

	// searches for a job by the name to report back to port
	public String jobByName(String text) {
		String nameResult = "";
		for (Job j : jobs) {
			if (j.name.equalsIgnoreCase(text)) {
				nameResult += j;
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
		for (Job j : jobs) {
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
		for (Job j : jobs) {
			sortOrder += j + "\n";
		}
		return sortOrder;
	}

}
