import java.util.Comparator;

/**
 * File Name: Comparators.java
 * Date Due: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will implement
 * a sort of persons by skill
 * NO CHANGES MADE IN PROJECT THREE
 *  NO CHANGES MADE IN PROJECT FOUR
 */



// compares skills of persons
class PersonnelComparatorBySkill implements Comparator<Person> {

	@Override
	public  synchronized int compare(Person p1, Person p2) {
		return p1.getSkill().compareTo(p2.getSkill());
	}

}