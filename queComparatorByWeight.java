
/**
 * File Name: Comparators.java
 * Date Due: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: to define a comparator
 * that will sort ships in que by weight
 * NO CHANGES MADE IN PROJECT THREE
 *  NO CHANGES MADE IN PROJECT FOUR
 * 
 * */
import java.util.Comparator;

class queComparatorByWeight implements Comparator<Ship> {

	@Override
	public  synchronized int compare(Ship s1, Ship s2) {
		return (int) (s1.getWeight() - s2.getWeight());
	}

}