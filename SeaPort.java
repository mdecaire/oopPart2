
/**
 * File Name: SeaPort.java
 * Date Due: 3/25/2018
 * Author: Michelle DeCaire
 * Purpose: To represent its segment of the world
 * it holds list of docks,  ships, persons and que. 
 * It is responsible for  most searches since it owns the list.
 * PROJECT TWO ADDITIONS: added multiple sorting features for each arrayList
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SeaPort extends Thing {
	ArrayList<Dock> docks = new ArrayList<Dock>();
	ArrayList<Ship> que = new ArrayList<Ship>();
	ArrayList<Ship> ships = new ArrayList<Ship>();
	ArrayList<Person> persons = new ArrayList<Person>();

	SeaPort(String name, Scanner sc) {
		super(name, sc);

	}

	// adds dock to list
	public void addDock(Dock dock) {
		docks.add(dock);
	}

	// adds person to list
	public void addPerson(Person p) {
		persons.add(p);
	}

	// returns a list of ships to world
	public String getShips() {
		String shipList = "\n--- List of all ships: \n";
		for (Ship s : ships) {
			if (ships.isEmpty()) {
				shipList += "None Listed";
			} else {
				shipList += " > " + s;
			}
		}
		return shipList;
	}

	// list of docks for this port
	public String getDocks() {
		String dockList = "";
		for (Dock d : docks) {
			if (docks.isEmpty()) {
				dockList += "None Listed\n";
			} else {
				dockList += "\n " + d;
			}
		}
		return dockList;
	}

	// the list of ships in que for this port
	public String getQue() {
		String queList = "\n--- List of all ships in Que: \n";
		if (que.isEmpty() || que.equals(null)) {
			queList += "None Listed";
		}
		for (Ship qShips : que) {

			queList += " > " + qShips;

		}
		return queList;
	}

	// a list of persons for this port
	public String getPersons() {
		String pList = "\n--- List of all Persons: \n";
		if (persons.equals(null) || persons.isEmpty()) {
			pList += "No personnel listed \n";
		}
		for (Person p : persons) {

			pList += " > " + p;

		}
		return pList;
	}

	// list of jobs for this port
	public String getJobs() {
		String jobList = "";
		String endProduct = "\n--- List of all Jobs: \n";
		for (Ship sh : ships) {
			String shipJob = sh.getJobs();
			if (shipJob.isEmpty()) {
				jobList += "";
			} else {

				jobList += shipJob;
			}

		}
		if (jobList.isEmpty()) {
			endProduct += "No Jobs Listed.\n";
		} else {
			endProduct += jobList;
		}
		return endProduct;
	}

	// searches for given name in this port
	// assumes there could be multiple results with
	// the same name
	public String searchForName(String text) {
		String nameResult = "";
		for (Dock d : docks) {
			if (d.name.equalsIgnoreCase(text)) {
				nameResult += d;
			}

		}
		for (Ship s : ships) {
			if (s.name.equalsIgnoreCase(text)) {
				nameResult += s;
			}
			nameResult += s.jobByName(text);

		}
		for (Person p : persons) {
			if (p.name.equalsIgnoreCase(text)) {
				nameResult += p;
			}
		}

		return nameResult;
	}

	// searches for skills in this port
	public String searchForSkill(String text) {
		String skill = "";
		for (Person p : persons) {
			if (p.getSkill().equalsIgnoreCase(text)) {
				skill += p;
			}
		}
		return skill;
	}

	// searches by weight in this port
	public String searchByWeight(double wgt) {
		String shipByLB = "";
		for (Ship s : ships) {
			if (s.getWeight() >= wgt) {
				shipByLB += s + "Weight: " + s.getWeight() + "\n";
			}
		}
		return shipByLB;
	}

	// searches by length in this port
	public String searchByLength(double len) {
		String shipByLength = "";
		for (Ship s : ships) {
			if (s.getLength() >= len) {
				shipByLength += s + "Length: " + s.getLength() + "\n";
			}
		}
		return shipByLength;
	}

	// searches by minimum passenger at this port
	public String findShipsWithMinimumPassengers(int numPass) {
		String shipByPassenger = "";
		for (Ship s : ships) {
			if (s.getWhetherPassenger()) {
				if (((PassengerShip) s).getNumPassengers() >= numPass) {
					shipByPassenger += s + "Number of Passengers: " + (((PassengerShip) s)).getNumPassengers() + "\n\n";
				}
			}
		}
		return shipByPassenger;
	}

	// all attributes of this port class
	public String toString() {
		String allData = "";
		allData += "\n----SeaPort: " + super.toString() + "\n" + getDocks() + getQue() + getShips() + getPersons()
				+ getJobs();
		return allData;
	}

	// returns string of just the name and index of this port.
	public String toString(Boolean needPortList) {
		return "SeaPort: " + super.toString() + "\n";
	}

	// initializes sorts for all dock types
	public String sortDock(String howToSort, String orderToSort) {
		String sortOrder = "";
		if (howToSort.equals("Ship Dock Time")) {
			sortOrder = sortDockByTime(orderToSort);
			return sortOrder;
		} else if (howToSort.equalsIgnoreCase("ship name")) {
			sortOrder = sortDockByShip(orderToSort);
		} else {
			sortOrder = sortDockbyName(orderToSort);
		}
		return sortOrder;
	}

	// helper method to sort Dock by name
	private String sortDockbyName(String orderToSort) {
		String sortOrder = "";
		Collections.sort(docks);
		if (orderToSort.equalsIgnoreCase("descending")) {
			Collections.reverse(docks);
		}
		for (Dock d : docks) {
			sortOrder += "\n" + d.name;
		}
		return sortOrder;
	}

	// helper method to sort by dock ship
	private String sortDockByShip(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			docks.sort(new DockComparatorByShipName());
		} else {
			docks.sort(Collections.reverseOrder(new DockComparatorByShipName()));
		}
		for (Dock d : docks) {
			sortOrder += "\n" + d;
		}
		return sortOrder;
	}

	// helper method to sort by arrival time
	private String sortDockByTime(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			docks.sort(new DockComparatorByShipDockTime());
		} else {
			docks.sort(Collections.reverseOrder(new DockComparatorByShipDockTime()));

		}
		for (Dock d : docks) {
			sortOrder += "\n" + d + "\nShip Docked On: " + d.ship.getDockTime().toString() + "\n";
		}
		return sortOrder;
	}

	// sorts ship by name and all ships OR passenger OR cargo
	public String sortShip(String howToSort, String orderToSort) {
		String sortOrder = "";
		Collections.sort(ships);

		if (orderToSort.equalsIgnoreCase("descending")) {
			Collections.reverse(ships);
		}
		switch (howToSort) {
		case "Cargo only":
			for (Ship s : ships) {
				if (!s.getWhetherPassenger()) {
					sortOrder += s;
				}

			}
			break;
		case "Passenger only":
			for (Ship s : ships) {
				if (s.getWhetherPassenger()) {
					sortOrder += s;
				}

			}
			break;
		case "Name":
			for (Ship s : ships) {
				sortOrder += s;
			}
			break;
		}
		return sortOrder;
	}

	// initializes the que sort
	public String sortQue(String howToSort, String orderToSort) {
		String sortOrder = "";
		switch (howToSort) {
		case "Name":
			sortOrder = sortQueName(orderToSort);
			break;
		case "Weight":
			sortOrder = sortByWeight(orderToSort);
			break;
		case "Length":
			sortOrder = sortQueLength(orderToSort);
			break;
		case "Width":
			sortOrder = sortQueWidth(orderToSort);
			break;
		case "Draft":
			sortOrder = sortQueByDraft(orderToSort);

		}
		return sortOrder;
	}

	// helper method to sort by draft
	private String sortQueByDraft(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			que.sort(new queComparatorByDraft());
		} else if (orderToSort.equalsIgnoreCase("descending")) {
			que.sort(Collections.reverseOrder(new queComparatorByDraft()));
		}
		for (Ship s : que) {
			sortOrder += s + "Ship's Draft : " + s.getDraft() + " m. \n";
		}

		return sortOrder;
	}

	// helper method to sort by que width
	private String sortQueWidth(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			que.sort(new queComparatorByWidth());
		} else if (orderToSort.equalsIgnoreCase("descending")) {
			que.sort(Collections.reverseOrder(new queComparatorByWidth()));
		}
		for (Ship s : que) {
			sortOrder += s + "Ship's Width : " + s.getWidth() + " m. wide\n";
		}

		return sortOrder;
	}

	// helper method to sort que by length
	private String sortQueLength(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			que.sort(new queComparatorByLength());
		} else if (orderToSort.equalsIgnoreCase("descending")) {
			que.sort(Collections.reverseOrder(new queComparatorByLength()));
		}
		for (Ship s : que) {
			sortOrder += s + "Ship's Length : " + s.getLength() + " m. long\n";
		}

		return sortOrder;
	}

	// helper method to sort que by weight
	private String sortByWeight(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			que.sort(new queComparatorByWeight());
		} else if (orderToSort.equalsIgnoreCase("descending")) {
			que.sort(Collections.reverseOrder(new queComparatorByWeight()));
		}

		for (Ship s : que) {
			sortOrder += s + "Ship Weighs: " + s.getWeight() + " Tons\n";
		}

		return sortOrder;
	}

	// helper method to sort que by name
	private String sortQueName(String orderToSort) {
		String sortOrder = "";
		Collections.sort(que);
		if (orderToSort.equalsIgnoreCase("descending")) {
			Collections.reverse(que);
		}
		for (Ship s : que) {
			sortOrder += s;
		}
		return sortOrder;
	}

	// initializes sort of persons
	public String sortPersonnel(String howToSort, String orderToSort) {
		String sortOrder = "";
		if (howToSort.equals("Name")) {
			sortOrder = sortPersonByName(orderToSort);
		} else if (howToSort.equals("Skills")) {
			sortOrder = sortPersonBySkill(orderToSort);
		}

		return sortOrder;
	}

	// helper method to sort Person by skill
	private String sortPersonBySkill(String orderToSort) {
		String sortOrder = "";
		if (orderToSort.equalsIgnoreCase("ascending")) {
			persons.sort(new PersonnelComparatorBySkill());
		} else if (orderToSort.equalsIgnoreCase("descending")) {
			persons.sort(Collections.reverseOrder(new PersonnelComparatorBySkill()));
		}
		for (Person p : persons) {
			sortOrder += p;
		}
		return sortOrder;
	}

	// helper method to sort Person by name
	private String sortPersonByName(String orderToSort) {
		String sortOrder = "";
		Collections.sort(persons);
		if (orderToSort.equalsIgnoreCase("descending")) {
			Collections.reverse(persons);
		}
		for (Person p : persons) {
			sortOrder += p;
		}
		return sortOrder;
	}

	// initializes the job sort by Ship
	public String sortJobs(String howToSort, String orderToSort) {
		sortShip("Name", orderToSort);
		String sortOrder = "";
		for (Ship s : ships) {
			sortOrder += "\n--- Ship: " + s.name + "\n";
			sortOrder += s.sortJobs(howToSort, orderToSort);
		}
		return sortOrder;
	}

}
