
/**
 * FileName: World.java
 * Date Due: 4/22/2018
 * Author: Michelle Decaire
 * Purpose: To take the file and build a world of ports that hold docks ships and jobs;
 * docks that hold ships; and ships that hold people.
 * After building this world it has the capability to search for special types of searches and structures
 * Note: This class is closely tied to the port class and usually iterates through a list of ports to find 
 * structure information.
 * PROJECT TWO ADDITIONS: processFile was modified to intialize hashmaps, consume the first three tokens on a scanner line 
 * to get the index, and intialize a search by index.
 * Adding children to parent array became trivial with the hashmap and sorting methods were also added
 * PROJECT THREE ADDITIONS: added nodes and changed some of the string builder methods
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

public class World extends Thing {

	private Scanner sc = null;
	private ArrayList<SeaPort> ports = new ArrayList<SeaPort>();
	private SeaPort port = null;
	private Dock dock = null;
	private PassengerShip pShip;
	private CargoShip cShip;
	private Person employee;
	private Job job;
	PortTime worldTime;

	public World() {
		worldTime = new PortTime(System.currentTimeMillis() / 1000);
	}

	public World(String name, Scanner sc) {
		super(name, 0);
	}

	// reads the file, creates a scanner of one line to pass to each class
	// instantiation.
	public String processFile(File file, boolean searchByIndex, int search, JPanel jobBoard) {
		HashMap<Integer, SeaPort> portMap = new HashMap<Integer, SeaPort>();
		HashMap<Integer, Dock> dockMap = new HashMap<Integer, Dock>();
		HashMap<Integer, Ship> shipMap = new HashMap<Integer, Ship>();
		HashMap<Integer, Person> personMap = new HashMap<Integer, Person>();
		HashMap<Integer, Job> jobMap = new HashMap<Integer, Job>();
		try {
			sc = new Scanner(new FileReader(file));
			int index = -1;
			String name = "";
			while (sc.hasNextLine()) {
				String type = sc.next();
				Scanner scan = new Scanner(sc.nextLine());

				if (type.equals("//") || type.isEmpty() || type.equals(" ")) {
					continue;
				} else {

					if (scan.hasNext())
						name = scan.next();
					if (scan.hasNextInt())
						index = scan.nextInt();
					createClasses(type, scan, name, index, portMap, dockMap, shipMap, personMap, jobMap, jobBoard);

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			// swallows the end of line exception
		}
		if (searchByIndex) {
			String searchResults = searchByIndex(search, portMap, dockMap, shipMap, personMap, jobMap);
			return searchResults;
		}
		sc.close();
		return "";
	}

	Ship currentShip = null;
//creates the classes by reading the file
	private void createClasses(String type, Scanner scan, String name, int index, HashMap<Integer, SeaPort> portMap,
			HashMap<Integer, Dock> dockMap, HashMap<Integer, Ship> shipMap, HashMap<Integer, Person> personMap,
			HashMap<Integer, Job> jobMap, JPanel jobBoard) {
		int parentIndex = scan.nextInt();
		int shipCount = 0;
		switch (type) {
		case "port":
			port = new SeaPort(name, parentIndex, scan);
			ports.add(port);
			portMap.put(index, port);
			break;
		case "dock":
			dock = new Dock(name, parentIndex, scan);
			addDock(dock, portMap);
			dockMap.put(index, dock);
			break;
		case "pship":
			shipCount++;
			pShip = new PassengerShip(name, parentIndex, scan);
			pShip.setWhetherPassenger(true);
			shipMap.put(index, pShip);
			assignShip((Ship) pShip, portMap, dockMap);
			break;
		case "cship":
			shipCount++;
			cShip = new CargoShip(name, parentIndex, scan);
			cShip.setWhetherPassenger(false);
			shipMap.put(index, cShip);
			assignShip((Ship) cShip, portMap, dockMap);
			shipMap.put(index, cShip);
			break;
		case "person":
			employee = new Person(name, parentIndex, scan);
			addPerson(employee, portMap);
			personMap.put(index, employee);
			break;
		case "job":
			Ship currentShip = findParent(parentIndex, shipMap);
			job = new Job(name, parentIndex, scan, jobBoard, currentShip, shipCount);
			addJob(job, currentShip);
			jobMap.put(index, job);
			break;
		default:
			break;
		}

	}

	//new method to have the job find its parent
	private Ship findParent(int parentIndex, HashMap<Integer, Ship> shipMap) {
		Ship s = shipMap.get(parentIndex);
		return s;

	}

	// determine which port the dock belongs to.
	private void addDock(Dock dock2, HashMap<Integer, SeaPort> portMap) {
		SeaPort p = portMap.get(dock.parent);
		if (p != null) {
			p.addDock(dock2);
			dock2.setPortParent(p);
		}
		return;
	}

	// determines which port the person belongs to.
	private void addPerson(Person employee2, HashMap<Integer, SeaPort> portMap) {
		SeaPort p = portMap.get(employee2.parent);
		if (p != null) {
			p.addPerson(employee2);
		}

	}

	// find which ship the job belongs to.
	private void addJob(Job job2, Ship currentShip2) {
		if (currentShip2 != null) {
			currentShip2.addJob(job2);

		}
		return;
	}

	// assigns ships to the correct dock and or list by
	// adding it to the que if it does not belong to a dock.
	public void assignShip(Ship myShip, HashMap<Integer, SeaPort> portMap, HashMap<Integer, Dock> dockMap) {
		Long time = setTime(7);
		myShip.setArrivalTime(time);
		Dock myDock = dockMap.get(myShip.parent);
		SeaPort port = portMap.get(myShip.parent);
		if (myDock == null) {
			port.ships.add(myShip);
			port.que.add(myShip);
			myShip.setPort(port);
			myShip.setDock(null);
			return;
		} else {
			long dTime = setTime(6);
			myShip.setDockTime(dTime);
			port = portMap.get(myDock.parent);
			myDock.setShip(myShip);
			myShip.setDockTime(dTime);
			port.ships.add(myShip);
			myShip.setDock(myDock);
			myShip.setPort(port);

		}

	}

	private Long setTime(int i) {

		Random rand = new Random();
		long now = System.currentTimeMillis();
		long temp = rand.nextInt(i * 24 * 60 * 60 * 1000);
		long time = now - temp;
		return time;
	}


	

	// returns the results for a name search
	public String searchName(String text) {
		String resultForName = "";
		String tempString = "";
		for (SeaPort p : ports) {

			if (p.name.equalsIgnoreCase(text)) {
				resultForName += p;
				return resultForName;
			} else {
				tempString += p.searchForName(text);
				if (!tempString.isEmpty()) {
					resultForName += "\n---Port:" + p.name + ":\n" + tempString;
					tempString = "";
				}
			}

		}

		return resultForName;
	}

	// performs a skill search
	public String searchSkill(String text) {
		String skill = "";
		String tempString = "";
		for (SeaPort s : ports) {
			tempString += s.searchForSkill(text);
			if (!tempString.isEmpty()) {
				skill += "\n Port: " + s.name + ":\n" + tempString;
				tempString = "";
			}
		}
		return skill;
	}

	// takes in a double, determines whether it is a length or width search
	// and returns the results of a lenght or width search
	public String searchByDouble(double dub, Boolean isLength) {
		String shipByDouble = "";
		String tempString = "";
		for (SeaPort s : ports) {
			if (isLength) {
				tempString += s.searchByLength(dub);
			} else {
				tempString += s.searchByWeight(dub);
			}
			if (!tempString.isEmpty()) {
				shipByDouble += "\n Port:" + s.name + ":\n" + tempString;
				tempString = "";
			}
		}

		return shipByDouble;
	}

	// returns the results of search by minimum number of passengers.
	public String searchByNumberOfPassengers(int numPass) {
		String shipWithMinPassengers = "";
		String tempString = "";
		for (SeaPort s : ports) {
			tempString += s.findShipsWithMinimumPassengers(numPass);
			if (!tempString.isEmpty()) {
				shipWithMinPassengers += "\n Port:" + s.name + ":\n" + tempString;
				tempString = "";
			}
		}
		return shipWithMinPassengers;
	}

	// returns string of all data structures
	public String toString() {
		String allData = ">>>>>>>>>>>> THE WORLD: \n\n";
		for (SeaPort p : ports) {
			allData += p;
		}

		return allData;
	}

	// returns the results of an index search
	public String searchByIndex(int num, HashMap<Integer, SeaPort> portMap, HashMap<Integer, Dock> dockMap,
			HashMap<Integer, Ship> shipMap, HashMap<Integer, Person> personMap, HashMap<Integer, Job> jobMap) {
		String ResultbyIndex = "";
		if (portMap.containsKey(num)) {
			SeaPort p = portMap.get(num);
			ResultbyIndex += p;
		} else if (dockMap.containsKey(num)) {
			Dock d = dockMap.get(num);
			ResultbyIndex += d;
		} else if (shipMap.containsKey(num)) {
			Ship s = shipMap.get(num);
			ResultbyIndex += getShipInfo(s, dockMap, portMap);

		} else if (personMap.containsKey(num)) {
			ResultbyIndex += personMap.get(num).toString();
		} else if (jobMap.containsKey(num)) {
			ResultbyIndex += jobMap.get(num).toString();
		} else {
			ResultbyIndex = "";
		}

		return ResultbyIndex;
	}

	// helper method to print parent information
	private String getShipInfo(Ship s, HashMap<Integer, Dock> dockMap, HashMap<Integer, SeaPort> portMap) {
		String shipResults = "";
		Dock d = dockMap.get(s.parent);

		if (d != null) {
			SeaPort p = portMap.get(d.parent);
			shipResults += p.toString() + "\n" + d;
		} else {
			SeaPort p = portMap.get(s.parent);
			shipResults += p.toString() + "\n" + s;
		}
		return shipResults;
	}

	// sorts the ports or entire structure by name uses thing comparator
	public String sortPort(String whatToSort, String orderToSort) {
		String sortedOrder = "";
		Collections.sort(ports);
		if (orderToSort.equals("Descending")) {
			Collections.reverse(ports);

		}
		// sorts everything then prints the port tostring
		if (whatToSort.equals("All")) {
			for (SeaPort p : ports) {
				p.sortJobs("Name", orderToSort);
				p.sortPersonnel("Name", orderToSort);
				p.sortShip("Name", orderToSort);
				p.sortQue("Name", orderToSort);
				p.sortShip("Name", orderToSort);
				p.sortDock("Name", orderToSort);
				sortedOrder += p;
			}
		} else {
			for (SeaPort p : ports) {
				sortedOrder += p.toString();

			}
		}
		return sortedOrder;
	}

	// initialize dock sort for each port
	public String sortDock(String howToSort, String orderToSort) {
		String sortOrder = "";
		sortPort("port", orderToSort);
		for (SeaPort p : ports) {
			sortOrder += "\n--- Port: " + p.name;
			sortOrder += p.sortDock(howToSort, orderToSort);

		}
		return sortOrder;
	}

	// initialize ship sort for each port
	public String sortShips(String howToSort, String orderToSort) {
		sortPort("port", orderToSort);
		String sortOrder = "";
		for (SeaPort p : ports) {
			sortOrder += "\n--- Port: " + p.name + "\n";
			sortOrder += p.sortShip(howToSort, orderToSort);
		}
		return sortOrder;
	}

	// initializes que sort for each port
	public String sortQue(String howToSort, String orderToSort) {
		sortPort("port", orderToSort);
		String sortOrder = "";
		for (SeaPort p : ports) {
			sortOrder += "\n--- Port: " + p.name + "\n";
			sortOrder += p.sortQue(howToSort, orderToSort);
		}
		return sortOrder;
	}

	// initializes the person sort by port
	public String sortPersonnel(String howToSort, String orderToSort) {
		sortPort("port", orderToSort);
		String sortOrder = "";
		for (SeaPort p : ports) {
			sortOrder += "\n--- Port: " + p.name + "\n";
			sortOrder += p.sortPersonnel(howToSort, orderToSort);
		}
		return sortOrder;
	}

	// initializes the jobs sort by port
	public String sortJobs(String howToSort, String orderToSort) {
		sortPort("port", orderToSort);
		String sortOrder = "";
		for (SeaPort p : ports) {
			sortOrder += "\n--- Port: " + p.name + "\n";
			sortOrder += p.sortJobs(howToSort, orderToSort);
		}
		return sortOrder;
	}

	public synchronized void addNodes(DefaultMutableTreeNode port2) {
		DefaultMutableTreeNode portNode = null;
		for (SeaPort p : ports) {
			portNode = new DefaultMutableTreeNode(p.name);
			p.getDockNode(portNode);
			p.getShipNodes(portNode);
			p.getPersonNodes(portNode);
			port2.add(portNode);
		}
		return;

	}

	/**
	 * replaces one of the original screen builders. THis builds an array to display on a table
	 * for all ports
	 * @return
	 * 
	 */
	
	public ArrayList<LinkedList<String>> buildList() {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			s = new LinkedList<String>();
			for (Dock d : p.docks) {
				s.add(p.name);
				s.add(d.name);
				s.add(d.ship.name);
				s.add("");
				al.add(s);
				s = new LinkedList<String>();
			}
			for (Ship ship : p.que) {

				s.add(p.name);
				s.add("");
				s.add(ship.name);
				s.add("");

				al.add(s);
				s = new LinkedList<String>();
			}
			for (Person peep : p.persons) {
				s.add(p.name);
				s.add("");
				s.add("");
				s.add(peep.toString());
				al.add(s);
				s = new LinkedList<String>();
			}
			al.add(s);
			s = new LinkedList<String>();
		}
		return al;
	}
	
	/**
	 * replaces the string builder for dock information
	 * @param parent
	 * @return
	 */

	public ArrayList<LinkedList<String>> buildDockList(String parent) {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(parent)) {
				s = new LinkedList<String>();
				for (Dock d : p.docks) {
					s.add(p.name);
					s.add(d.name);
					s.add(d.fixedShip.name);
					s.add("");
					al.add(s);
					s = new LinkedList<String>();
				}
				al.add(s);
				s = new LinkedList<String>();
			}
		}
		return al;
	}

	/**
	 * builds a list for all ships
	 * @param name
	 * @return
	 */
	public ArrayList<LinkedList<String>> buildShipList(String name) {

		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(name)) {
				s = new LinkedList<String>();
				for (Ship ship : p.ships) {
					if (ship.fixedJobs.isEmpty()) {
						s.add(p.name);
						s.add(ship.name);
						s.add("");
						al.add(s);
						s = new LinkedList<String>();
					} else {
						String jobName = "";
						s.add(p.name);
						s.add(ship.name);
						for (Job j : ship.fixedJobs) {
							jobName += j.name + ", ";
						}
						s.add(jobName);
						al.add(s);
						s = new LinkedList<String>();
					}
				}
			}
		}
		return al;
	}
	/**
	 * builds a list for passenger ships to display on table
	 * @param name
	 * @return
	 */

	public ArrayList<LinkedList<String>> buildPassShipList(String name) {

		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(name)) {
				s = new LinkedList<String>();
				for (Ship ship : p.ships) {
					if (ship.getWhetherPassenger()) {
						if (ship.fixedJobs.isEmpty()) {
							s.add(p.name);
							s.add(ship.toString());
							s.add("");
							al.add(s);
							s = new LinkedList<String>();
						} else {
							String jobName = "";
							s.add(p.name);
							s.add(ship.toString());
							for (Job j : ship.fixedJobs) {
								jobName += j.name + ", ";
							}
							s.add(jobName);
							al.add(s);
							s = new LinkedList<String>();
						}
					}
				}
			}
		}
		return al;
	}

	/**
	 * builds a list for cargo ships
	 * @param parent
	 * @return
	 */
	public ArrayList<LinkedList<String>> buildCargShipList(String parent) {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(parent)) {
				s = new LinkedList<String>();
				for (Ship ship : p.ships) {
					if (!ship.getWhetherPassenger()) {
						if (ship.fixedJobs.isEmpty()) {
							s.add(p.name);
							s.add(ship.toString());
							s.add("");
							al.add(s);
							s = new LinkedList<String>();
						} else {
							String jobName = "";
							s.add(p.name);
							s.add(ship.toString());
							for (Job j : ship.fixedJobs) {
								jobName += j.name + ", ";
							}
							s.add(jobName);
							al.add(s);
							s = new LinkedList<String>();
						}
					}
				}
			}
		}
		return al;
	}

	/**
	 * builds a list of the ships in que
	 * @param parent
	 * @return
	 */
	public ArrayList<LinkedList<String>> queList(String parent) {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(parent)) {
				s = new LinkedList<String>();
				for (Ship ship : p.que) {
					if (ship.fixedJobs.isEmpty()) {
						s.add(p.name);
						s.add(ship.toString());
						s.add("");
						al.add(s);
						s = new LinkedList<String>();
					} else {
						String jobName = "";
						s.add(p.name);
						s.add(ship.toString());
						for (Job j : ship.fixedJobs) {
							jobName += j.name + ", ";
						}
						s.add(jobName);
						al.add(s);
						s = new LinkedList<String>();
					}

				}
			}
		}
		return al;
	}
	/**
	 * builds a list of persons belonging to the parent port
	 * @param parent
	 * @return
	 */

	public ArrayList<LinkedList<String>> personList(String parent) {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for (SeaPort p : ports) {
			if (p.name.equals(parent)) {
				s = new LinkedList<String>();
				for (Person peep : p.persons) {
					s.add(p.name);
					s.add(peep.toString());
					al.add(s);
					s = new LinkedList<String>();
				}
			}

		}

		return al;
	}

	/**
	 * builds a list from the ship list that was not changed by jobs
	 * @param parent
	 * @return
	 */
	public ArrayList<LinkedList<String>> buildJobList(String parent) {
		LinkedList<String> s;
		ArrayList<LinkedList<String>> al = new ArrayList<LinkedList<String>>();
		for(SeaPort p: ports) {
			s = new LinkedList<String>();
			for(Ship ship: p.ships) {
				Ship rightShip= null;
				if(ship.name.equals(parent)) {
					rightShip=ship;
					if (rightShip.getPort().name.equals(p.name)){
						for(Job j: rightShip.fixedJobs) {
							s.add(p.name);
							s.add(rightShip.name);
							s.add(j.name);
							s.add(j.getRequirements());
							al.add(s);
							s = new LinkedList<String>();

						}
						
						break;
					}
				}
			}
		}
		return al;
	}

	

}
