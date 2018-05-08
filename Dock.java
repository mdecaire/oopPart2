
/**
 * File Name: Dock.java
 * Date due:05/06/2018
 * Author: Michelle Decaire
 * Purpose: to define a dock which holds a ship.
 * NO METHODS WERE ADDED TO THIS CLASS FOR PROJECT 2
 * PROJECT 3: added a fixed ship method to show the original ships that were in port
 * PART 4: NO new additions
 */
import java.util.Scanner;

public class Dock extends Thing {
	Ship ship;
	SeaPort portParent;
	Ship fixedShip;
	boolean readyToChange = false;

	public Dock(String name, int parentIndex, Scanner sc2) {
		super(name, parentIndex);
	}

	public void setShip(Ship listShip) {
		fixedShip=listShip;
		this.ship = listShip;
	}
	
	public Ship getShip() {
		return ship;
	}

	public String toString() {
		String dockString = ("Dock: " + super.toString() + "\n" + "  " + fixedShip.toString());
		return dockString;
	}

	public void setReady(boolean ready) {
		readyToChange = ready;

	}

	public boolean getReady() {
		return readyToChange;

	}

	public void setPortParent(SeaPort p) {
		portParent = p;

	}

	public SeaPort getPortParent() {
		return portParent;
	}

}
