/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will sort
 * ships in que by length
 */
import java.util.Comparator;

class queComparatorByLength implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		return (int) (s1.getLength() - s2.getLength());
	}

}