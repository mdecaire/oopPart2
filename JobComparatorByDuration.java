import java.util.Comparator;

/**
 * File Name: Comparators.java
 * Date Due: 4/8/2018
 * Author: Michelle Decaire
 * Purpose: To implement comparator class that will provide sorting 
 * jobs by duration
 */


// compares the duration of jobs.
class JobComparatorByDuration implements Comparator<Job> {

	@Override
	public int compare(Job j1, Job j2) {
		return (int) (j1.getDuration() - j2.getDuration());
	}

}