/** 
 * File Name: DockComparatorByShipDOCKTime.java
 * Date: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: TO compare docks by arrival time
 * NO Changes added in project three
 */
import java.util.Comparator;

public class DockComparatorByShipDockTime implements Comparator<Dock> {

	@Override
	public synchronized int compare(Dock d1, Dock d2) {
		Long d1Time = d1.getShip().getDockTime().time;
		Long d2Time = d2.getShip().getDockTime().time;
		return d1Time.compareTo(d2Time);

	}

}
 