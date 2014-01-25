/*
 * This class acts as an individual counter for the app. Contains the name of the counter,
 * its current count, and the date/times at which a count was added.
 * TODO: Add support for the calculation and output of statistics.
 */

package ualberta.hpabst.as1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Counter implements Serializable {
	
	private String counterName;
	private Integer count;
	private List<Date> countTimes;
	
	public Counter(){
		/*
		 * If no name is passed on object creation, the default name
		 * of the counter is 'Default'.
		 */
		this.counterName = "Default";
		this.count = 0;
		this.countTimes = new ArrayList<Date>();
	}
	
	public Counter(String newName){
		this.counterName = newName;
		this.count = 0;
		this.countTimes = new ArrayList<Date>();
	}
	
	
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

	public List<Date> getCountTimes() {
		return countTimes;
	}

	public void setCountTimes(List<Date> countTimes) {
		this.countTimes = countTimes;
	}
	
	public void increment(){
		/*
		 * Increments the count, and adds the time/date it was incremented to countTimes.
		 */
		this.count += 1;
		Date g = new Date(System.currentTimeMillis());
		countTimes.add(g);
	}
	
	public void reset(){
		/*
		 * Resets the count of the counter back to 0 and clears
		 * the list containing times/dates of when the count was incremented.
		 */
		this.count = 0;
		countTimes.clear();
	}
	
	@Deprecated
	public void rename(String newName){
		/*
		 * Deprecated, rendered redundant by default setCounterName method.
		 */
		this.counterName = newName;
	}
	
	public List<String> getMonthStats(){
		/*
		 * Returns an list of strings of the form: "Month of XXX -- Y"
		 * where XXX is a month that has at least one  count taken by the
		 * counter and Y is the number of counts taken in that month.
		 */
		int currentCount = 0;
		String[] temp;
		List<String> returnList = new ArrayList<String>();
		String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
								"Oct", "Nov", "Dec"};
		for (String month: monthNames){
			currentCount = 0;
			for(Date date: this.countTimes){
				temp = date.toString().split(" ");
				if ((temp[1].compareToIgnoreCase(month) == 0)){
						currentCount += 1;		
				}			
			}//endof inner loop
			if(currentCount > 0){
				returnList.add(String.format("Month of %s -- %d", month, currentCount));
			}
		}//endof outer loop
		return returnList;
	}
	
	
	
	

}
