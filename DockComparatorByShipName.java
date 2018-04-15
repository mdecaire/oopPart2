
/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator that will sort dock by ship name
 */
import java.util.Comparator;

class DockComparatorByShipName implements Comparator<Dock> {

	@Override
	public int compare(Dock d1, Dock d2) {
		return d1.ship.name.compareTo(d2.ship.name);
	}

}
