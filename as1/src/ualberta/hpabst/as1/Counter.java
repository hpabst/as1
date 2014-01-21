/*
 * This class acts as an individual counter for the app. Contains the name of the counter,
 * its current count, and the date/times at which a count was added.
 */

package ualberta.hpabst.as1;

import java.util.Date;
import java.util.List;

public class Counter {
	
	public String counterName; //The name itself of the counter.
	public Integer count; //Current count of the counter.
	public List<Date> countTimes; //A List of Dates at the time when the count was incremented.
	//Is this right? fucking java.
	
	public Counter(String newName){
		this.counterName = newName;
		this.count = 0;
		countTimes.clear(); //Not really necessary, but may as well be safe.
	}
	
	public void increment(){
		/*
		 * Increments the count, and adds the time/date it was incremented to countTimes.
		 */
		this.count += 1;
		countTimes.add(new Date());
	}

}
