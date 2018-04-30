
/**
 * File Name: Comparators.java
 * Date Due: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: to define a comparator
 * that will sort ships in que by weight
 * NO CHANGES MADE IN PROJECT THREE
 * 
 * */
import java.util.Comparator;

class queComparatorByWeight implements Comparator<Ship> {

	@Override
	public  synchronized int compare(Ship s1, Ship s2) {
		return (int) (s1.getWeight() - s2.getWeight());
	}

}