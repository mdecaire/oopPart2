/**
 * File Name: Comparators.java
 * Date Due: 05/06
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that 
 * will sort ships in que by width
 * NO CHANGES MADE IN PROJECT THREE
 *  NO CHANGES MADE IN PROJECT FOUR
 */
import java.util.Comparator;

class queComparatorByWidth implements Comparator<Ship> {

	@Override
	public synchronized int compare(Ship s1, Ship s2) {
		return (int) (s1.getWidth() - s2.getWidth());
	}

}