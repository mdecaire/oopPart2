import java.util.Comparator;

/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will implement
 * a sort of persons by skill
 */



// compares skills of persons
class PersonnelComparatorBySkill implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		return p1.getSkill().compareTo(p2.getSkill());
	}

}