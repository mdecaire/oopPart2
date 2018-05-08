/**
 * File Name: Comparators.java
 * Date Due: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will sort
 * ships in que by length
 * NO CHANGES MADE IN PROJECT THREE
 *  NO CHANGES MADE IN PROJECT FOUR
 */
import java.util.Comparator;

class queComparatorByLength implements Comparator<Ship> {

	@Override
	public synchronized int compare(Ship s1, Ship s2) {
		return (int) (s1.getLength() - s2.getLength());
	}

}