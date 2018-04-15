
/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: to define a comparator
 * that will sort ships in que by weight
 * 
 * */
import java.util.Comparator;

class queComparatorByWeight implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		return (int) (s1.getWeight() - s2.getWeight());
	}

}