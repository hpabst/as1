/*
 * This is the activity for displaying the list of counters with the ability to view or remove
 * individual counters. Should as well have a button that will sort the counters based on their count and one
 * that allows the generation of a new counter.
 * Corresponding XML layout file is counter_list_layout.xml.
 * TODO: Basically everything on this screen. Created it for the sake of having it here.
 */

package ualberta.hpabst.as1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class CounterListActivity extends Activity {
	/*
	 * A large amount of code for this class was adapted from
	 * www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/
	 * 
	 */
	
	ListView lstTest;
	CounterAdapter arrayAdapter;
	CounterMaster counterMaster;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.counter_list_layout);
		
		Intent i = getIntent();
		counterMaster = (CounterMaster)i.getSerializableExtra("counterMaster");
		
		//Initialize ListView
		lstTest = (ListView) findViewById(R.id.counterListView);
		
		//Initialize our array adapter notice how it references the counter_list_instance_layout.xml layout
		arrayAdapter = new CounterAdapter(this, R.layout.counter_list_instance_layout,
											counterMaster.allCounters);
		
		lstTest.setAdapter(arrayAdapter);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		/*
		 * Sets up the menu for sorting and creating new counters when the activity is created.
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.counter_list_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.sortCounters:
				sortCounters(item);
				return true;
			case R.id.newCounter:
				createNewCounter(item);
				return true;
			default:
				return false;
		}
	}
	
	public void createNewCounter(MenuItem item){
		/*
		 * Still need to implement. Should prompt the user for the name of
		 * the new counter, create it, and append it to the end of
		 * the master list of counters.
		 */
		Log.i("Add new counter button:", "I have been pressed.");
		
	}
	
	public void sortCounters(MenuItem item){
		/*
		 * Still need to implement. Should sort the counters currently in the
		 * master list of counters according to their count, then redisplay them
		 * in the activity.
		 */
		Log.i("Sort counters button:", "I have been pressed.");
	}
	

}
