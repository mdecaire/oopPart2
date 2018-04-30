
/**
 * FileName: Person.java
 * Date: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of each person
 * NO METHODS ADDED DURING PROJECT 3
 */

import java.util.Scanner;

public class Person extends Thing {
	private String skill;

	public Person(String name,int index, Scanner sc) {
		super(name, index);
		if (sc.hasNext())
			setSkill(sc.next());
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String toString() {
		return super.toString() + ": " + skill + "\n";
	}

	
}
