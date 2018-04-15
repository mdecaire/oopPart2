
/**
 * File Name: Dock.java
 * Date due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: to define a dock which holds a ship.
 * NO METHODS WERE ADDED TO THIS CLASS FOR PROJECT 2
 */
import java.util.Scanner;

public class Dock extends Thing {
	Ship ship;

	public Dock(String name, Scanner sc2) {
		super(name, sc2);
	}

	public void setShip(Ship listShip) {
		this.ship = listShip;
	}

	public String toString() {
		String dockString = ("Dock: " + super.toString() + "\n" + "  " + ship.toString());
		return dockString;
	}

}
