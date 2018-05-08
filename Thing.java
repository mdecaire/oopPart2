
/**
 * File Name: Thing.java
 * Due Date:05/06/2018
 * Author: Michelle DeCaire
 * Purpose: Parent Class of most classes
 * 	defines the index name parent and toString base methods.
 * PROJECT TWO ADDITIONS: index taken out, accepts name as string 
 * and changed comparator to sort by name.
 * No additions or changes in Project 3
 *  NO CHANGES MADE IN PROJECT FOUR
 */


public class Thing implements Comparable<Thing> {

	protected String name = "";
	protected int parent = 0;

	public Thing() {
	}

	public Thing(String name,int parentIndex) {

		this.name = name;
		this.parent=parentIndex;
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
