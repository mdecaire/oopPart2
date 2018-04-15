
/**
 * File Name: Thing.java
 * Due Date:4/8/2018
 * Author: Michelle DeCaire
 * Purpose: Parent Class of most classes
 * 	defines the index name parent and toString base methods.
 * PROJECT TWO ADDITIONS: index taken out, accepts name as string 
 * and changed comparator to sort by name.
 */
import java.util.Scanner;

public class Thing implements Comparable<Thing> {

	protected String name = "";
	protected int parent = 0;

	public Thing() {
	}

	public Thing(String name, Scanner sc) {

		this.name = name;
		if (sc.hasNextInt())
			this.parent = sc.nextInt();
	}

	// compares the names of each child class
	@Override
	public int compareTo(Thing myThing) {
		int compare = this.name.compareTo(myThing.name);
		return compare;
	}

	public String toString() {
		return name + " ";
	}

}