/*
 * This class acts as the "master" of the counters within the app.
 * It holds a list of the counters which are incremented, and allows
 * sorting of said counters by count amount.
 */

package ualberta.hpabst.as1;

import java.util.ArrayList;
import java.util.List;

public class CounterMaster {
	
	public List<Counter> allCounters;
	
	public CounterMaster(){
		allCounters = new ArrayList<Counter>();
	}
	
	public void sortCounterList(){
		/*
		 * Still need to do the implementation of this.
		 */	
	}
	
	public void addCounter(Counter newCounter){
		allCounters.add(newCounter);
	}
	
	public void clearCounterList(){
		allCounters.clear();
	}
	
	

}
