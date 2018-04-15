
/**
 * File name: Job.java
 * Date: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of the jobs class
 * and to return a list of requirements back to the ship class.
 * NO CHANGES ADDED DURING PROJECT 2
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {
	private double duration;
	private String skills;
	protected ArrayList<String> requirements = new ArrayList<String>();

	public Job(String name, Scanner sc) {
		super(name, sc);
		if (sc.hasNextDouble())
			this.setDuration(sc.nextDouble());
		while (sc.hasNext()) {

			this.setSkills(sc.next());

		}
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
		requirements.add(skills);
	}

	public String toString() {
		return "Job: " + super.toString() + "\n" + getRequirements();
	}

	// returns the requirements needed for each job
	public String getRequirements() {
		String jList = "";
		if (requirements.equals(null) || requirements.isEmpty()) {
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

}
