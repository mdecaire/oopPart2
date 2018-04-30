/**
 * File Name: Comparators.java
 * Date Due: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will
 * allow sorting ships in que by Draft
 * NO CHANGES MADE IN PROJECT THREE
 */
import java.util.Comparator;

class queComparatorByDraft implements Comparator<Ship> {

	@Override
	public synchronized int compare(Ship s1, Ship s2) {
		return (int) (s1.getDraft() - s2.getDraft());
	}

}