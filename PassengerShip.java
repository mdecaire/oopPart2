
/**
 * File Name: PassengerShip.java
 * Date:05/06/2018
 * Author: Michelle Decaire
 * Purpose: to define a passenger ship
 * NO METHODS ADDED DURING PROJECT 2
 * NO CHANGES MADE IN PROJECT THREE
 * NO CHANGES MADE IN PROJECT FOUR
 */
import java.util.Scanner;

public class PassengerShip extends Ship {

	private int numPassengers;
	private int numRooms;
	private int numOccupied;
	public Boolean amPassenger = true;

	public PassengerShip(String name,int index, Scanner sc) {
		super(name, index, sc);
		if (sc.hasNextInt())
			this.setNumPassengers(sc.nextInt());
		if (sc.hasNextInt())
			this.setNumRooms(sc.nextInt());
		if (sc.hasNextInt())
			this.setNumOccupied(sc.nextInt());

	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	public int getNumOccupied() {
		return numOccupied;
	}

	public void setNumOccupied(int numOccupied) {
		this.numOccupied = numOccupied;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public String toString() {
		return "Passenger ship: " + super.toString();
	}
}
