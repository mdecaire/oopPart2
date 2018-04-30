
/**
 * File Name: SeaPort.java
 * Date Due: 4/22/2018
 * Author: Michelle DeCaire
 * Purpose: To represent its segment of the world
 * it holds list of docks,  ships, persons and que. 
 * It is responsible for  most searches since it owns the list.
 * PROJECT TWO ADDITIONS: added multiple sorting features for each arrayList
 * PROJECT THREE ADDITIONS: added node classes 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public class SeaPort extends Thing {
	CopyOnWriteArrayList<Dock> docks = new CopyOnWriteArrayList<Dock>();
	CopyOnWriteArrayList<Ship> que = new CopyOnWriteArrayList<Ship>();
	ArrayList<Ship> ships = new ArrayList<Ship>();
	ArrayList<Person> persons = new ArrayList<Person>();
	
	SeaPort(String name, int index, Scanner sc) {
		super(name, index);

	}

	// adds dock to list
	public void addDock(Dock dock) {
		docks.add(dock);
	}

	// adds person to list
	public void addPerson(Person p) {
		persons.add(p);
	}

	public synchronized void addToQ(Ship s) {
		que.add(s);
	}

	// returns a list of ships to world
	public String getShips(String kindOfShip) {
		String shipList = "\n--- List of all ships: \n";
		String passShip = "\n--- List of Passenger Ships: \n";
		String cargShip = "\n--- List of Cargo Ships: \n";
		if (ships.isEmpty()) {
			return "None Listed";
		}
		for (Ship s : ships) {
			if (s.getWhetherPassenger()) {
				passShip += " > " + s.toString();
			} else if (!s.getWhetherPassenger()) {
				cargShip += " > " + s.toString();
			}

			shipList += " > " + s.toString();

		}
		if (kindOfShip.equals("cargo")) {
			return cargShip;
		} else if (kindOfShip.equals("passenger")) {
			return passShip;
		} else
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
	public String getJobs(String otherParent) {
		String jobList = "";
		String endProduct = "";

		for (Ship sh : ships) {
			String shipJob = sh.getJobs(otherParent);
			if (shipJob.isEmpty()) {
				jobList += "";
			} else {

				jobList += shipJob;
			}

		}
		if (jobList.isEmpty() && otherParent.equals("all")) {
			endProduct += "No Jobs Listed.\n";
		} else if (jobList.isEmpty()) {
			endProduct += "";
		} else {
			endProduct = "\n--- List of Jobs: \n" + jobList;
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
				break;
			}
			else {
				nameResult+=s.jobByName(text);
			}

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
				shipByLB += s.name + " Weight: " + s.getWeight() + "\n";
			}
		}
		return shipByLB;
	}

	// searches by length in this port
	public String searchByLength(double len) {
		String shipByLength = "";
		for (Ship s : ships) {
			if (s.getLength() >= len) {
				shipByLength += s.name +  " Length: " + s.getLength() + "\n";
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
					shipByPassenger += s.name + " Number of Passengers: " + (((PassengerShip) s)).getNumPassengers() + "\n\n";
				}
			}
		}
		return shipByPassenger;
	}

	// all attributes of this port class
	public String toString() {
		String allData = "";
		allData += "\n----SeaPort: " + super.toString() + "\n" + getDocks() + getQue() + getShips("all") + getPersons()
				+ getJobs("all");
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
			sortOrder += "\n" + d;
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
		String sortOrder = "";
		for (Ship s : ships) {
			if(!s.fixedJobs.isEmpty()) {
			sortOrder += "\n--- Ship: " + s.name + "\n";
			sortOrder += s.sortJobs(howToSort, orderToSort);
			}
		}
		return sortOrder;
	}

	//builds the nodes for the docks
	public synchronized void getDockNode(DefaultMutableTreeNode portNode) {
		DefaultMutableTreeNode dockNode = null;
		DefaultMutableTreeNode shipNode = null;
		DefaultMutableTreeNode dock = new DefaultMutableTreeNode("Dock");
		for (Dock d : docks) {
			dockNode = new DefaultMutableTreeNode(d.name);
			if (d.ship != null) {
				shipNode = new DefaultMutableTreeNode(d.fixedShip.name);
				d.ship.getJobNodes(shipNode);
				dockNode.add(shipNode);
				dock.add(dockNode);
			}

			portNode.add(dock);
		}

		return;
	}

	//builds the nodes for the ships
	public void getShipNodes(DefaultMutableTreeNode portNode) {
		DefaultMutableTreeNode cShipNode = new DefaultMutableTreeNode("Cargo Ships");
		DefaultMutableTreeNode pShipNode = new DefaultMutableTreeNode("Passenger Ships");
		DefaultMutableTreeNode allShipNode = new DefaultMutableTreeNode("All Ships");
		DefaultMutableTreeNode qShipNode = new DefaultMutableTreeNode("Ships in Que");
		DefaultMutableTreeNode shipNode = null;

		for (Ship s : ships) {
			shipNode = new DefaultMutableTreeNode(s.name);
			s.getJobNodes(shipNode);
			allShipNode.add(shipNode);

			if (s.getWhetherPassenger()) {
				shipNode = new DefaultMutableTreeNode(s.name);
				s.getJobNodes(shipNode);
				pShipNode.add(shipNode);

			} else {
				shipNode = new DefaultMutableTreeNode(s.name);
				s.getJobNodes(shipNode);
				cShipNode.add(shipNode);
			}

		}
		for (Ship s : que) {
			shipNode = new DefaultMutableTreeNode(s.name);
			s.getJobNodes(shipNode);
			qShipNode.add(shipNode);
		}
		portNode.add(allShipNode);
		portNode.add(qShipNode);
		portNode.add(pShipNode);
		portNode.add(cShipNode);
		return;
	}

	//builds nodes for person
	public void getPersonNodes(DefaultMutableTreeNode portNode) {
		DefaultMutableTreeNode personNode = new DefaultMutableTreeNode("Person");
		DefaultMutableTreeNode oneNode = null;
		for (Person p : persons) {
			oneNode = new DefaultMutableTreeNode(p.toString());
			personNode.add(oneNode);
		}
		portNode.add(personNode);
	}

}
