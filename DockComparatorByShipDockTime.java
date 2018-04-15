/** 
 * File Name: DockComparatorByShipDOCKTime.java
 * Date: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: TO compare docks by arrival time
 */
import java.util.Comparator;

public class DockComparatorByShipDockTime implements Comparator<Dock> {

	@Override
	public int compare(Dock d1, Dock d2) {
		Long d1Time = d1.ship.getDockTime().time;
		Long d2Time = d2.ship.getDockTime().time;
		return d1Time.compareTo(d2Time);

	}

}
 