
/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator that will sort dock by ship name
 * NO changed added to project 3
 */
import java.util.Comparator;

class DockComparatorByShipName implements Comparator<Dock> {

	@Override
	public synchronized int compare(Dock d1, Dock d2) {
		return d1.getShip().name.compareTo(d2.ship.name);
	}

}
