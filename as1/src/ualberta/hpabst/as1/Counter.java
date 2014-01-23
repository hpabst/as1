/*
 * This class acts as an individual counter for the app. Contains the name of the counter,
 * its current count, and the date/times at which a count was added.
 * TODO: Add support for the calculation and output of statistics.
 */

package ualberta.hpabst.as1;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Counter {
	
	public String counterName;
	public Integer count;
	public List<GregorianCalendar> countTimes;
	
	
	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<GregorianCalendar> getCountTimes() {
		return countTimes;
	}

	public void setCountTimes(List<GregorianCalendar> countTimes) {
		this.countTimes = countTimes;
	}

	public Counter(String newName){
		this.counterName = newName;
		this.count = 0;
		this.countTimes = new ArrayList<GregorianCalendar>();
	}
	
	public void increment(){
		/*
		 * Increments the count, and adds the time/date it was incremented to countTimes.
		 */
		this.count += 1;
		countTimes.add(new GregorianCalendar());
	}
	
	public void reset(){
		this.count = 0;
		countTimes.clear();
	}
	
	public void rename(String newName){
		this.counterName = newName;
	}

}
