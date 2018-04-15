
/**
 * File Name: GUI.java
 * Due Date: 3/25/2018
 * Author: Michelle Decaire
 * PuRpose: to build a interface and initialize searches  
 * foR data that the text file may want related to ports and such.
 * PROJECT 2 ADDITIONS: changed the entire graphical interface,
 * and added a sorting initializer method.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private File file = null;
	private World shipWorld = null;
	final JComboBox<String> sortCriteria = new JComboBox<String>();

	// driver
	public static void main(String[] args) {
		Interface i = new Interface();
		i.buildInterface();

	}

	// builds the interface and uses lambda to perform component actions
	private void buildInterface() {
		frame = new JFrame("Port Information Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		fPanel.setBorder(new EmptyBorder(5,5, 0, 0));

		// structure Layout
		JPanel cPanel = new JPanel(new GridLayout(1,3, 25, 0));
		String[] StructureOptions = new String[] { "Entire Structure", "Ports", "Docks", "Ships", "Ships in Que",
				"Personnel", "Jobs" };
		JComboBox<String> structList = new JComboBox<>(StructureOptions);
		structList.setBackground(Color.WHITE);
		cPanel.add(structList);
		cPanel.add(new JLabel());
		cPanel.add(new JLabel());
		cPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Display Unsorted Structures:"));

		// search Layout
		JPanel searchPanel = new JPanel(new GridLayout(1, 3, 25, 0));
		JTextField searchField = new JTextField();
		searchField.setBounds(0, 0, 200, 40);
		String[] SearchOptions = new String[] { "Name", "Index", "Skill", "Weight", "Length",
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
		String[] firstSort = new String[] { "All","Ports", "Docks", "Ships", "Ships in Que", "Personnel", "Jobs" };
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
		StructureScroll.setPreferredSize(new Dimension(300, 300));

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

		// tabbed pane to show
		JTabbedPane tp = new JTabbedPane();
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(StructureScroll, BorderLayout.CENTER);
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(searchScroll, BorderLayout.CENTER);
		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(sortScroll, BorderLayout.CENTER);
		tp.add("Structures", p1);
		tp.addTab("Search", p2);
		tp.addTab("Sorted", p3);

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
		contentPane.add(cPanel, c);

		c.gridx = 0;
		c.gridy = 3;
		contentPane.add(searchPanel, c);

		c.gridx = 0;
		c.gridy = 5;
		contentPane.add(sortPanel, c);

		// split pane
		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		horizontalSplit.setTopComponent(contentPane);
		horizontalSplit.setBottomComponent(tp);
		horizontalSplit.setDividerLocation(.3);
		horizontalSplit.setOneTouchExpandable(true);
		horizontalSplit.setContinuousLayout(false);

		frame.setContentPane(horizontalSplit);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// actions for components
		mainSort.addActionListener(e -> setComboBox(mainSort.getSelectedItem()));
		fileButton.addActionListener(e -> getFile(textLabel));
		structList.addActionListener(e -> showStructure(structList.getSelectedItem(), structureResults, tp));
		searchButton.addActionListener(
				e -> getSearchResults(searchField.getText(), searchList.getSelectedItem(), searchResults, tp));
		sortButton.addActionListener(e -> showSorted(mainSort.getSelectedItem(), sortCriteria.getSelectedItem(),
				sortOrder.getSelectedItem(), sortResults, tp));
	}

	// method to initialize sorting in the world class...feeds file in for an index
	// sort
	private void showSorted(Object selectedItem, Object selectedItem2, Object selectedItem3,
			JTextArea sortResultsTextArea, JTabbedPane tp) {
		// "Ascending", "Descending"
		int x;
		tp.setSelectedIndex(2);
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

			}
		} catch (NullPointerException ex) {
			showError("FILE NOT FOUND", "Please select a file to continue.");
		}
		sortResultsTextArea.setText(sortResults);
		sortResultsTextArea.selectAll();
		x = sortResultsTextArea.getSelectionStart();
		sortResultsTextArea.select(x, x);
	}

	// for the search section of the program. Case statement is for
	// different different search types.
	private void getSearchResults(String oldText, Object selectedItem, JTextArea searchResults, JTabbedPane tp) {
		tp.setSelectedIndex(1);
		String searchType = "";
		if (selectedItem.equals(null)) {
			searchType = "Name";
		}
		searchType = (String) selectedItem;
		String text = oldText.trim();
		String textDisplay = "";
		World searchWorld = new World();

		try {

			switch (searchType) {
			case "Name":
				textDisplay = shipWorld.searchName(text);
				if (textDisplay.isEmpty()) {
					showMessage("Name Not Found", "Please check the spelling and try again!");
				}
				break;

			case "Index":
				int searchNum = Integer.parseInt(text);
				textDisplay = searchWorld.processFile(file, true, searchNum);
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

			case "Weight":
				double wgt = Double.parseDouble(text);
				textDisplay = shipWorld.searchByDouble(wgt, false);
				if (textDisplay.isEmpty()) {
					showMessage("None Found",
							"No ship with that minimum weight found. \nPlease check the number entered and try again!");
				}
				break;
			case "Length":
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
			return;
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

	}

	// informational messages like no name found
	private void showMessage(String title, String message) {

		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);

	}

	private String strucList = "";

	// gets the structure strings for each class
	private void showStructure(Object selectedItem, JTextArea structureResults, JTabbedPane tp) {
		String structureType = (String) selectedItem;
		tp.setSelectedIndex(0);

		try {
			switch (structureType) {
			case "Entire Structure":
				strucList = shipWorld.toString();
				break;
			case "Ports":
				strucList = shipWorld.getPorts();
				break;
			case "Docks":
				strucList = shipWorld.getDock();
				break;
			case "Ships":
				strucList = shipWorld.getShips();
				break;
			case "Ships in Que":
				strucList = shipWorld.getQue();
				break;
			case "Personnel":
				strucList = shipWorld.getPerson();
				break;
			case "Jobs":
				strucList = shipWorld.getJob();
				break;
			}

			structureResults.setText(strucList);
			int x;
			structureResults.selectAll();
			x = structureResults.getSelectionStart();
			structureResults.select(x, x);

		} catch (NullPointerException ex) {
			showError("FILE NOT FOUND", "Please select a file to continue.");
		}
		return;
	}

	// in case of errors
	private void showError(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);

	}

	// gets the file and creates the world
	private void getFile(JLabel textLabel) {

		JFileChooser fc = new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnValue = fc.showOpenDialog(frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			shipWorld = new World();
			shipWorld.processFile(file, false, 0);
			textLabel.setText("    " + file.getName());
		}
		return;

	}

	String sortSection = "";

	// uses the information from the first combo box to set the model for the second
	// combo box
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
