
/**
 * File Name: Interface.java
 * Due Date:05/06/2018
 * Author: Michelle Decaire
 * PuRpose: to build a interface and initialize searches  
 * foR data that the text file may want related to ports and such.
 * PROJECT 2 ADDITIONS: changed the entire graphical interface,
 * and added a sorting initializer method.
 * CHANGES IN PROJECT THREE: jtree added show structures method changed
 * to display information in table
 * Project 4 Refactoring done to make a few methods smaller-  some new components added to the build
 * interface method.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class Interface<T> extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private File file = null;
	private File fileForSearch = null;
	private World shipWorld = null;
	final JComboBox<String> sortCriteria = new JComboBox<String>();
	JTree structuredTree;

	// driver
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Interface<Object>().buildInterface();
			}
		});

	}

	/**
	 *  builds the interface and uses lambda to perform component actions
	 */
	private synchronized void buildInterface() {
		frame = new JFrame("Port Information Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("World");
		structuredTree = new JTree(top);
		structuredTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane treeView = new JScrollPane(structuredTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		final DefaultComboBoxModel<String> defList = new DefaultComboBoxModel<String>(new String[] { "Name" });

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());

		// File button layout
		JButton fileButton = new JButton("Browse...");
		fileButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		fileButton.setPreferredSize(new Dimension(75, 30));
		JPanel fPanel = new JPanel(new GridLayout(1, 3));
		JLabel textLabel = new JLabel("    No File Selected");
		textLabel.setBorder(new TitledBorder(new EmptyBorder(10, 10, 0, 0), "Currently in File:"));
		textLabel.setForeground(Color.blue);

		fPanel.add(fileButton);
		fPanel.add(new JLabel());
		fPanel.add(textLabel);
		fPanel.setBorder(new EmptyBorder(5, 5, 0, 0));

		// search Layout
		JPanel searchPanel = new JPanel(new GridLayout(1, 3, 25, 0));
		JTextField searchField = new JTextField();
		searchField.setBounds(0, 0, 200, 40);
		String[] SearchOptions = new String[] { "Name", "Index", "Skill", "Minimum Weight", "Minimum Length",
				"Minimum Number of Passengers" };
		JComboBox<String> searchList = new JComboBox<>(SearchOptions);
		searchList.setBackground(Color.WHITE);
		JButton searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(100, 30));
		searchButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		searchPanel.add(searchField);

		searchPanel.add(searchList);
		searchPanel.add(searchButton);
		searchPanel.setBorder(new TitledBorder(new EtchedBorder(), "Search"));

		// sort Layout
		JPanel sortPanel = new JPanel(new GridLayout(1, 4, 15, 0));
		String[] firstSort = new String[] { "All", "Ports", "Docks", "Ships", "Ships in Que", "Personnel", "Jobs" };
		JComboBox<String> mainSort = new JComboBox<String>(firstSort);
		mainSort.setBackground(Color.WHITE);
		sortCriteria.setBackground(Color.white);
		sortCriteria.setModel(defList);
		String[] order = new String[] { "Ascending", "Descending" };
		JComboBox<String> sortOrder = new JComboBox<String>(order);
		sortOrder.setBackground(Color.WHITE);
		JButton sortButton = new JButton("Sort");
		sortButton.setPreferredSize(new Dimension(100, 30));
		sortButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		sortPanel.add(mainSort);
		sortPanel.add(sortCriteria);
		sortPanel.add(sortOrder);
		sortPanel.add(sortButton);
		sortPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sort"));

		// 3 text areas to display everything
		JTextArea structureResults = new JTextArea();
		structureResults.setFont(new java.awt.Font("Monospaced", 1, 14));
		structureResults.setEditable(false);
		JScrollPane StructureScroll = new JScrollPane(structureResults, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JTextArea searchResults = new JTextArea();
		searchResults.setFont(new java.awt.Font("Monospaced", 1, 14));
		searchResults.setEditable(false);
		JScrollPane searchScroll = new JScrollPane(searchResults, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		searchScroll.setPreferredSize(new Dimension(300, 300));

		JTextArea sortResults = new JTextArea();
		sortResults.setFont(new java.awt.Font("Monospaced", 1, 14));
		sortResults.setEditable(false);
		JScrollPane sortScroll = new JScrollPane(sortResults, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sortScroll.setPreferredSize(new Dimension(300, 300));

		JPanel JobBoard = new JPanel();
		sortResults.setFont(new java.awt.Font("Monospaced", 1, 14));
		sortResults.setEditable(false);
		JScrollPane sp = new JScrollPane(JobBoard, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sortScroll.setPreferredSize(new Dimension(300, 300));

		// tabbed pane to show
		JTabbedPane tp = new JTabbedPane();
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(StructureScroll, BorderLayout.CENTER);
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(searchScroll, BorderLayout.CENTER);
		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(sortScroll, BorderLayout.CENTER);
		JPanel p4 = new JPanel(new BorderLayout());
		p4.add(sp, BorderLayout.CENTER);
		tp.add("Table", p1);
		tp.addTab("Search", p2);
		tp.addTab("Sorted", p3);
		tp.addTab("Job Progression", p4);
		JButton updateScreen= new JButton("Update Resource Pool");
		JPanel bPanl= new JPanel(new GridLayout(1,3));
		bPanl.add(new JLabel());
		bPanl.add(updateScreen);
		bPanl.add(new JLabel());
		
		
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(15, 0, 5, 0);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 1.0;
		contentPane.add(fPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(searchPanel, c);

		c.gridx = 0;
		c.gridy = 5;
		contentPane.add(sortPanel, c);
		
		c.gridx = 0;
		c.gridy = 6;
		contentPane.add(bPanl, c);
		
		//contenet for tabbed panes
		JPanel resourcePanel = new JPanel();
		resourcePanel.setBorder(new TitledBorder(new EtchedBorder(), "Resources Currently Available"));
		JScrollPane resourceScroll = new JScrollPane(resourcePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JSplitPane horSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JTextArea textArea = new JTextArea();
		

		textArea.setBorder((new TitledBorder(new EmptyBorder(1, 1, 1, 1), "Original Port Resources:")));
		leftPane.setTopComponent(treeView);
		leftPane.setBottomComponent(textArea);
		horSplit.setLeftComponent(leftPane);
		horSplit.setDividerLocation(300);
		horSplit.setOneTouchExpandable(true);
		horSplit.setContinuousLayout(true);

		JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		topSplit.setTopComponent(contentPane);
		topSplit.setDividerLocation(250);
		topSplit.setBottomComponent(resourceScroll);
		horSplit.setOneTouchExpandable(true);
		horSplit.setContinuousLayout(true);

		JSplitPane totalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		horSplit.setRightComponent(topSplit);
		totalSplit.setTopComponent(horSplit);
		totalSplit.setBottomComponent(tp);
		totalSplit.setDividerLocation(650);
		totalSplit.setOneTouchExpandable(true);
		totalSplit.setContinuousLayout(true);

		frame.setContentPane(totalSplit);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setResizable(true);
		frame.setSize(1600, 1200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		tp.setSelectedIndex(3);
		DefaultTreeModel model = (DefaultTreeModel) structuredTree.getModel();
		
		// actions for components
		mainSort.addActionListener(e -> setComboBox(mainSort.getSelectedItem()));
		try {
			fileButton.addActionListener(e -> getFile(model, textLabel, top, tp, JobBoard, p1, StructureScroll,
					structureResults, resourcePanel, textArea));
			updateScreen.addActionListener(e->shipWorld.updateLogging());
		} catch (Exception e) {
			System.out.println("In file event");
		}
		searchButton.addActionListener(e -> getSearchResults(searchField.getText(), searchList.getSelectedItem(),
				searchResults, tp, JobBoard, resourcePanel, textArea));
		sortButton.addActionListener(e -> showSorted(mainSort.getSelectedItem(), sortCriteria.getSelectedItem(),
				sortOrder.getSelectedItem(), sortResults, tp));

	}

	/**
	 * builds the nodes and calls the show structure when a node is clicked
	 */

	private synchronized void createNodes(DefaultTreeModel model, DefaultMutableTreeNode top, JTabbedPane tp, JPanel p1,
			JScrollPane structureScroll, JTextArea structureResults, JPanel resourcePanel) {

		DefaultMutableTreeNode Port = new DefaultMutableTreeNode("Ports");
		shipWorld.addNodes(Port);
		top.add(Port);
		model.reload(top);
		try {
			structuredTree.addTreeSelectionListener(e -> showStructure(structuredTree.getSelectionPaths(), tp, p1,
					structureScroll, structureResults, resourcePanel));

		} catch (NullPointerException e) {

		}
		return;
	}

	/**
	 * method to initialize sorting in the world class...feeds file in for an index
	 * sort
	 */

	private void showSorted(Object selectedItem, Object selectedItem2, Object selectedItem3,
			JTextArea sortResultsTextArea, JTabbedPane tp) {

		tp.setSelectedIndex(2);
		int x;
		String whatToSort = (String) selectedItem;
		String howToSort = (String) selectedItem2;
		String orderToSort = (String) selectedItem3;
		String sortResults = "";
		try {
			switch (whatToSort) {
			case "All":
				sortResults = shipWorld.sortPort(whatToSort, orderToSort);
				break;
			case "Ports":
				sortResults = shipWorld.sortPort(whatToSort, orderToSort);
				break;
			case "Docks":
				sortResults = shipWorld.sortDock(howToSort, orderToSort);
				break;
			case "Ships":
				sortResults = shipWorld.sortShips(howToSort, orderToSort);
				break;
			case "Ships in Que":
				sortResults = shipWorld.sortQue(howToSort, orderToSort);
				break;
			case "Personnel":
				sortResults = shipWorld.sortPersonnel(howToSort, orderToSort);
				break;
			case "Jobs":
				sortResults = shipWorld.sortJobs(howToSort, orderToSort);
				break;
			}
		} catch (NullPointerException ex) {
			showError("FILE NOT FOUND", "Please select a file to continue.");
		}
		sortResultsTextArea.setText(sortResults);
		sortResultsTextArea.selectAll();
		x = sortResultsTextArea.getSelectionStart();
		sortResultsTextArea.select(x, x);
		return;
	}

	/**
	 * for the search section of the program. Case statement is for different search
	 * types.
	 */

	private String getSearchResults(String oldText, Object selectedItem, JTextArea searchResults, JTabbedPane tp,
			JPanel jobBoard, JPanel resourcePanel, JTextArea textArea) {
		tp.setSelectedIndex(1);

		String searchType = "";
		if (selectedItem == null) {
			selectedItem = "Name";
		}
		searchType = (String) selectedItem;
		String text = oldText.trim();
		String textDisplay = "";

		try {

			switch (searchType) {
			case "Name":
				textDisplay = shipWorld.searchName(text);
				if (textDisplay.isEmpty()) {
					showMessage("Name Not Found", "Please check the spelling and try again!");
				}
				break;

			case "Index":
				World searchWorld = new World();
				int searchNum = Integer.parseInt(text);
				textDisplay = searchWorld.processFile(fileForSearch, true, searchNum, jobBoard, resourcePanel,
						textArea);
				if (textDisplay.isEmpty()) {
					showMessage("Index Not Found", "Please check the number entered and try again!");
				}
				break;

			case "Skill":
				textDisplay = shipWorld.searchSkill(text);
				if (textDisplay.isEmpty()) {
					showMessage("None Found",
							"Person with that skill not Found\nPlease check the spelling and try again!");
				}
				break;

			case "Minimum Weight":
				double wgt = Double.parseDouble(text);
				textDisplay = shipWorld.searchByDouble(wgt, false);
				if (textDisplay.isEmpty()) {
					showMessage("None Found",
							"No ship with that minimum weight found. \nPlease check the number entered and try again!");
				}
				break;
			case "Minimum Length":
				double length = Double.parseDouble(text);
				textDisplay = shipWorld.searchByDouble(length, true);
				if (textDisplay.isEmpty()) {
					showMessage("None Found",
							"No ship with that minimum length found. \nPlease check the number entered and try again!");
				}
				break;
			case "Minimum Number of Passengers":
				int numPass = Integer.parseInt(text);
				textDisplay = shipWorld.searchByNumberOfPassengers(numPass);
				if (textDisplay.isEmpty()) {
					showMessage("None Found",
							"No ship with that minimum passengers found. \nPlease check the number entered and try again!");
				}

				break;
			default:
				break;

			}
		} catch (NullPointerException e) {
			showError("File Not Found", "Please enter a file to continue!");

		} catch (NumberFormatException nfe) {
			showError("Not a number",
					"Please enter a valid number to continue" + "\n Note: Indexes should not have a decimal");
		}
		// sets the text to display and makes sure scroll bar begins at the top
		int x;
		searchResults.setText(textDisplay);
		searchResults.selectAll();
		x = searchResults.getSelectionStart();
		searchResults.select(x, x);
		return textDisplay;

	}

	/**
	 * informational messages like no name found
	 */
	private void showMessage(String title, String message) {

		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);

	}

	/**
	 * gets the structure strings/arrays for table for each class
	 *
	 */
	private synchronized void showStructure(TreePath[] treePaths, JTabbedPane tp, JPanel p1,
			JScrollPane structureScroll, JTextArea structureResults, JPanel resourcePanel) {
		tp.setSelectedIndex(0);
		String s = "";
		if (treePaths == null) {// this is to prevent a race condition from happening if a new file is selected
			return;
		}
		for (TreePath t : treePaths) {
			s = t.getLastPathComponent().toString();
		}
		String parent = "";

		String string = treePaths[0].toString();
		String[] parentString = (string.split(","));
		if (parentString.length > 1) {
			int size = (parentString.length - 2);
			parent = parentString[size];
			parent = parent.trim();
		}
		String[] tableEl = null;
		ArrayList<LinkedList<String>> alist = null;
		String strucList = "";
		s = s.trim();
		String[] name = s.split(" ");
		String nodeName = name[0];
		switch (s) {
		case "World":
			tableEl = new String[] { "Port", "Dock", "Ship", "Persons" };
			alist = shipWorld.buildList();
			buildTable(tableEl, alist, p1);
			break;
		case "Ports":

			tableEl = new String[] { "Port", "Dock", "Ship", "Persons" };
			alist = shipWorld.buildList();
			buildTable(tableEl, alist, p1);
			break;
		case "Dock":
			tableEl = new String[] { "Port", "Dock", "Ship" };
			alist = shipWorld.buildDockList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "All Ships":
			tableEl = new String[] { "Port", "Ship", "Job" };
			alist = shipWorld.buildShipList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "Passenger Ships":
			tableEl = new String[] { "Port", " Passenger Ships", "Job" };
			alist = shipWorld.buildPassShipList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "Cargo Ships":
			tableEl = new String[] { "Port", " Cargo Ships", "Job" };
			alist = shipWorld.buildCargShipList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "Ships in Que":
			tableEl = new String[] { "Port", " Cargo Ships", "Job" };
			alist = shipWorld.queList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "Person":
			tableEl = new String[] { "Port", "Persons" };
			alist = shipWorld.personList(parent);
			buildTable(tableEl, alist, p1);
			break;
		case "Job":
			tableEl = new String[] { "Port", "Ship", "Jobs", "Requirements" };
			alist = shipWorld.buildJobList(parent);
			buildTable(tableEl, alist, p1);
			break;
		default:
			// sets the information to a string if name was selected
			buildStringElements(structureScroll, nodeName, structureResults, tp, resourcePanel, p1, strucList);

		}

		return;
	}

	/**
	 * helper method to build string elements for structure
	 */
	private void buildStringElements(JScrollPane structureScroll, String nodeName, JTextArea structureResults,
			JTabbedPane tp, JPanel resourcePanel, JPanel p1, String strucList) {
		structureScroll.removeAll();
		strucList = getSearchResults(nodeName, null, structureResults, tp, null, resourcePanel, null);
		tp.setSelectedIndex(0);
		structureResults.setText(strucList);
		int x;
		structureResults.selectAll();
		x = structureResults.getSelectionStart();
		structureResults.select(x, x);
		structureScroll.add(structureResults);
		structureScroll.revalidate();
		structureScroll.repaint();
		p1.removeAll();
		p1.add(structureScroll);
		p1.revalidate();
		p1.repaint();

	}

	/**
	 * helper method to build the actual table.
	 * 
	 */
	private void buildTable(String[] tableEl, ArrayList<LinkedList<String>> alist, JPanel p1) {
		int count = 0;

		String[][] list = new String[alist.size()][tableEl.length];
		for (LinkedList<String> l : alist) {
			list[count] = l.toArray(new String[tableEl.length]);
			count++;
		}

		final JTable table = new JTable(list, tableEl);
		JScrollPane jsp = new JScrollPane(table);
		p1.removeAll();
		p1.add(jsp);
		p1.revalidate();
		p1.repaint();

	}

	/**
	 * in case of errors
	 */
	private void showError(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * gets the file and creates the world
	 */
	private synchronized void getFile(DefaultTreeModel model, JLabel textLabel, DefaultMutableTreeNode top,
			JTabbedPane tp, JPanel jobBoard, JPanel p1, JScrollPane structureScroll, JTextArea structureResults,
			JPanel resourcePanel, JTextArea textArea) {
		file = null;
		top.removeAllChildren();
		jobBoard.removeAll();
		jobBoard.repaint();
		JFileChooser fc = new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnValue = fc.showOpenDialog(frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			fileForSearch = fc.getSelectedFile();
			shipWorld = new World();
			shipWorld.processFile(file, false, 0, jobBoard, resourcePanel, textArea);
			textLabel.setText("    " + file.getName());
			shipWorld.updateLogging();
		}
		
		try {
			createNodes(model, top, tp, p1, structureScroll, structureResults, resourcePanel);
		} catch (Exception e) {
			System.out.println("Node Error");
		}
		
		return;

	}


	/**
	 * uses the information from the first combo box to set the model for the second
	 * combo box
	 */
	public void setComboBox(Object object) {

		DefaultComboBoxModel<String> defList = new DefaultComboBoxModel<String>(new String[] { "Name" });
		DefaultComboBoxModel<String> dockList = new DefaultComboBoxModel<String>(
				new String[] { "Name", "Ship Dock Time", "Ship Name" });
		DefaultComboBoxModel<String> queList = new DefaultComboBoxModel<String>(
				new String[] { "Name", "Weight", "Length", "Width", "Draft" });
		DefaultComboBoxModel<String> shipList = new DefaultComboBoxModel<String>(
				new String[] { "Name", "Passenger only", "Cargo only" });

		DefaultComboBoxModel<String> personList = new DefaultComboBoxModel<String>(new String[] { "Name", "Skills" });
		DefaultComboBoxModel<String> jobList = new DefaultComboBoxModel<String>(new String[] { "Name", "Duration" });

		String selectedValue = object.toString();
		if (selectedValue == null) {
			sortCriteria.setModel(defList);

		} else {
			switch (selectedValue) {
			case "All":
				sortCriteria.setModel(defList);
				break;
			case "Ports":
				sortCriteria.setModel(defList);
				break;
			case "Docks":
				sortCriteria.setModel(dockList);
				break;
			case "Ships":
				sortCriteria.setModel(shipList);
				break;
			case "Ships in Que":
				sortCriteria.setModel(queList);
				break;
			case "Personnel":
				sortCriteria.setModel(personList);
				break;
			case "Jobs":
				sortCriteria.setModel(jobList);
				break;
			default:
				break;

			}
		}
		return;
	}

}
