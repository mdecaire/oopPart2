
import java.util.Comparator;

/**
 * File Name: Comparators.java
 * Date Due: 05/06/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will provide sorting 
 * jobs by duration
 * NO CHANGES MADE IN PROJECT THREE
 * NO CHANGES MADE IN PROJECT FOUR
 */


// compares the duration of jobs.
class JobComparatorByDuration implements Comparator<Job> {

	@Override
	public int compare(Job j1, Job j2) {
		return (int) (j1.getDuration() - j2.getDuration());
	}

}