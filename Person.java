
/**
 * FileName: Person.java
 * Date: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of each person
 * NO METHODS ADDED DURING PROJECT 3
 * PROJECT FOUR: added a flag that indicates whether the person is busy
 * this helps to determine if there are enough resources.
 */

import java.util.Scanner;

public class Person extends Thing {
	 String skill;
	boolean busy;
	
	public Person(String name,int index, Scanner sc) {
		super(name, index);
		if (sc.hasNext())
			setSkill(sc.next());
		
	}
	
	public synchronized boolean getBusy() {
		return busy;
	}

	public synchronized void setBusy(boolean busy) {
		this.busy = busy;
	}


	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill=skill.trim();
		this.skill = skill;
	}

	public String toString() {
		return super.toString() + ": " + skill + "\n";
	}

	
}
