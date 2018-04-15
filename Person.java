
/**
 * FileName: Person.java
 * Date: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: to define and hold elements of each person
 * NO METHODS ADDED DURING PROJECT 2
 */
import java.util.Comparator;
import java.util.Scanner;

public class Person extends Thing {
	private String skill;

	public Person(String name, Scanner sc) {
		super(name, sc);
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
		return "Person: " + super.toString() + " " + skill + "\n";
	}

	class PersonComparatorByName implements Comparator<Person> {

		public int compare(Person p1, Person p2) {
			return p1.name.compareTo(p2.name);

		}
	}
}
