/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that 
 * will sort ships in que by width
 */
import java.util.Comparator;

class queComparatorByWidth implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		return (int) (s1.getWidth() - s2.getWidth());
	}

}