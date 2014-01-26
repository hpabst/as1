/*
 * This class acts as an individual counter for the app and supports
 * the logic for displaying stats re: the frequency of 
 * incrementing the counter. Contains the name of the counter,
 * its current count, and the date/times at which a count was added.
 * 
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
		/*
		 * Basic constructor for when a string is passed when a new object is created.
		 */
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
	
//	public List<String> getMonthStats(){
//		/*
//		 * Returns an list of strings of the form: "Month of XXX -- Y"
//		 * where XXX is a month that has at least one  count taken by the
//		 * counter and Y is the number of counts taken in that month.
//		 */
//		int currentCount = 0;
//		String[] temp;
//		List<String> returnList = new ArrayList<String>();
//		String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
//								"Oct", "Nov", "Dec"};
//		for (String month: monthNames){
//			currentCount = 0;
//			for(Date date: this.countTimes){
//				temp = date.toString().split(" ");
//				if ((temp[1].compareToIgnoreCase(month) == 0)){
//						currentCount += 1;		
//				}			
//			}//endof inner loop
//			if(currentCount > 0){
//				returnList.add(String.format("Month of %s -- %d", month, currentCount));
//			}
//		}//endof outer loop
//		return returnList;
//	}
	
	public List<String> getMonthStats(){
		/*
		 * Returns a list of strings of the form: "Month of XXX -- YY"
		 * where XXX is the month and YY is the number of times the counter
		 * was incremented in that month.
		 */
		String[] temp;
		int currentCount = 0;
		List<String> returnList = new ArrayList<String>();
		List<String> foundMonths = new ArrayList<String>();
		for (Date date: this.countTimes){
			temp = date.toString().split(" ");
			if((foundMonths.contains(temp[1])) == false){
				foundMonths.add(temp[1]);
			}
		}
		for(String s: foundMonths){
			currentCount = 0;
			for (Date date: this.countTimes){
				temp = date.toString().split(" ");
				if(temp[1].compareToIgnoreCase(s) == 0){
					currentCount += 1;
				}
			}//endof inner loop
			if(currentCount > 0){
				returnList.add(String.format("Month of %s -- %d", s, currentCount));
			}
		}//endof outer loop
		return returnList;
	}
	
	public List<String> getWeekStats(){
	/*
	 * Returns a list of strings of the form :"Week of XXX YY -- Z"
	 * where XXX is the month the week occurred, YY is the day of
	 * the start of that week (to simplify, we consider the months divided into
	 * 3/4 weeks, each starting on day 1, 8, 15, 22, or 29 of that month).
	 */
	String[] temp;
	int currentCount = 0;
	List<String> returnList = new ArrayList<String>();
	int[] weekStarters = {1, 8, 15, 22, 29, 32};
	String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
			"Oct", "Nov", "Dec"};
	for(String month:monthNames){
		currentCount = 0;
		for(int i = 0; i < weekStarters.length -1; i++){
			currentCount = 0;
			for(Date date: this.countTimes){
				temp = date.toString().split(" ");
				if ((temp[1].compareToIgnoreCase(month) == 0)&&
					(weekStarters[i] <= Integer.parseInt(temp[2])) &&
					(weekStarters[i+1] > Integer.parseInt(temp[2]))){
					currentCount += 1;
				}
			}//endof inner loop
			if(currentCount > 0){
			returnList.add(String.format("Week of %s %d to %d -- %d", month, weekStarters[i],
							weekStarters[i+1] - 1, currentCount));
			}
		}//endof middle loop
	}//endof outermost loop
	return returnList;
	}
	
	public List<String> getDayStats(){
		/*
		 * Returns a list of strings of the form: "XXX YY -- ZZ"
		 * where XXX is the month, YY is the day, and ZZ is the number of counts
		 * that took place on that
		 */
		String[] temp;
		String temp2;
		int currentCount = 0;
		List<String> returnList = new ArrayList<String>();
		List<String> foundDays = new ArrayList<String>();
		for (Date date: this.countTimes){
			temp = date.toString().split(" ");
			temp2 = temp[1]+ " " + temp[2];
			if((foundDays.contains(temp2)) == false){
			foundDays.add(temp2);	
			}
		}
		for (String s: foundDays){
			currentCount = 0;
			for(Date date: this.countTimes){
				temp = date.toString().split(" ");
				temp2 = temp[1] + " " + temp[2];
				/*
				 * temp2 is a string of the form "mon dd"
				 */
				if(temp2.compareToIgnoreCase(s) == 0){
					currentCount += 1;
				}
			}//endof inner loop
			returnList.add(String.format("Day of %s -- %d", s, currentCount));
		}//endof outer loop
		return returnList;
	}
	
	public List<String> getHourStats(){
		/*
		 * Returns a list of strings of the form: "Hour of XXX YY AA:00 AM/PM -- ZZ"
		 * where XXX is the month, YY is the day, AA is the hour of the day,
		 * and ZZ is the number of times the counter was incremented in that hour.
		 */
		String[] temp = {"pointless","initialization"};
		String[] temp2 = {"pointless","initialization"};
		String temp3 = "pointless";
		String amOrPm = "AM";
		int currentCount = 0;
		List<String> returnList = new ArrayList<String>();
		List<String> foundDays = new ArrayList<String>();
		for (Date date: this.countTimes){
			temp = date.toString().split(" ");
			temp2 = temp[3].split(":");
			temp3 = temp[1] + " " + temp[2] + " " + temp2[0] + ":00";
			/*
			 * temp3 is a string of the form "mon dd hh:00:
			 */
			if((foundDays.contains(temp3)) == false){
			foundDays.add(temp3);	
			}
		}
		for (String s: foundDays){
			currentCount = 0;
			for(Date date: this.countTimes){
				temp = date.toString().split(" ");
				temp2 = temp[3].split(":");
				temp3 = temp[1] + " " + temp[2] + " " + temp2[0] + ":00";
				if(temp3.compareToIgnoreCase(s) == 0){
					currentCount += 1;
				}
			}//endof inner loop
			if((Integer.parseInt(s.substring(7,9))) >= 12){
				amOrPm = "PM";
			}else{
				amOrPm = "AM";
			}
			temp3 = s.substring(7,9);
			if((Integer.parseInt(temp3)) >= 13 ||
			   (Integer.parseInt(temp3)) == 0){
				temp3 = String.valueOf((Integer.parseInt(temp3)) - 12);
			}
			temp3 = s.substring(0,7) + temp3;
			returnList.add(String.format("Hour of %s:00 %s -- %d", temp3, amOrPm, currentCount));
		}//endof outer loop.
		return returnList;
	}
	
	
	

}
