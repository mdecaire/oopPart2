import java.util.Date;

/**
 * File Name: PortTime.java Date Due: 3/25/2018 author: Michelle Decaire
 * Purpose: to establish time stamps of world and ships. Changed code to accept
 * a long int for time...time is randomly generated in the world
 * NO CHANGES MADE IN PROJECT THREE
 */
public class PortTime {
	long time;
	Date date;

	// gets the current computer time when a new time is called
	public PortTime(long time) {
		this.time = time;
		date = new java.util.Date(time);
	}

	// converts time to human readable format.
	public Date getDate() {
		return date;
	}

	// converts a date to human readable format
	public String toString() {
		String shipDate = "";
		shipDate = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
		return shipDate;
	}
}
