package ualberta.hpabst.as1;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * This is the activity for the display of statistics regarding a single counter.
 * It changes nothing regarding the state of the CounterMaster controlling the state
 * of the app. The associated layout files are stats_list_element.xml and 
 * stats_list_layout.xml.
 */

public class CounterStatsActivity extends Activity {
	
	private Counter selectedCounter;
	private ArrayAdapter<String> adapter;
	private List<String> statStrings;
	private ListView lstView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.stats_list_layout);
	}
	
	public void onResume(){
		super.onResume();
		Intent i = getIntent();
		selectedCounter = (Counter) i.getSerializableExtra("clickedCounter");
		lstView = (ListView) findViewById(R.id.statsListView);
		//Getting the statistics themselves below here.
		statStrings = selectedCounter.getMonthStats();
		statStrings.addAll(selectedCounter.getWeekStats());
		statStrings.addAll(selectedCounter.getDayStats());
		statStrings.addAll(selectedCounter.getHourStats());
		adapter = new ArrayAdapter<String>(this, R.layout.stats_list_element, 
				                           R.id.statElementDisplay,statStrings);
		lstView.setAdapter(adapter);		
	}

}
