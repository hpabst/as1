/*
 * This class acts as the "master" of the counters within the app.
 * It holds a list of the counters which are incremented, and allows
 * sorting of said counters by count amount.
 */

package ualberta.hpabst.as1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("serial")
public class CounterMaster implements Serializable {
	
	public List<Counter> allCounters;
	
	public CounterMaster(){
		allCounters = new ArrayList<Counter>();
	}
	
	public void sortAllCounters(){
		/*
		 * Sorts the counters stored in allCounters from largest to smallest.
		 */
		Collections.sort(this.getAllCounters(), new Comparator<Counter>(){
			public int compare (Counter c1, Counter c2){
				if (c1.getCount() <= c2.getCount()){
					return 1;
				}
				return -1;
			}
		});
	}
	
	public void addCounter(Counter newCounter){
		allCounters.add(newCounter);
	}
	
	public void clearAllCounters(){
		allCounters.clear();
	}
	
	public List<Counter> getAllCounters(){
		return this.allCounters;
	}
	
	
	
}
