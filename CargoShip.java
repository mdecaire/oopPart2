/**
 * File name: CargoShip.java
 * Date: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: To define and hold elements of a cargo Class.
 * NO FEATURES ADDED TO PROJECT TWO
 * TO Featured added in parts 3 or 4
 */
import java.util.Scanner;

public class CargoShip extends Ship {

	private double cargoValue;
	private double cargoVolume;
	private double cargoWeight;

	public CargoShip(String name, int index,Scanner sc) {
		super(name, index,sc);
		if (sc.hasNextDouble())
			this.setCargoValue(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setCargoVolume(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setCargoWeight(sc.nextDouble());
	}

	public double getCargoValue() {
		return cargoValue;
	}

	public void setCargoValue(double cargoValue) {
		this.cargoValue = cargoValue;
	}

	public double getCargoVolume() {
		return cargoVolume;
	}

	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	public double getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(double cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public String toString() {
		return "Cargo Ship: " + super.toString();
	}

}
