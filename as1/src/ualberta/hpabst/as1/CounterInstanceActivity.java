package ualberta.hpabst.as1;
/*
 * Activity for the display of data for an individual counter. From here the user should be able to 
 * rename the counter, reset it back to 0, increment the count, and view statistics of the counter.
 * Associated XML layout file is counter_layout.xml.
 * TODO: Everything involved with this Activity.
 */

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;


public class CounterInstanceActivity extends Activity {
	
	Counter clickedCounter;
	TextView counterNameDisplay;
	TextView counterCountDisplay;
	
	public void onResume(){
		/*
		 * Gets the counter that was clicked in the previous screen and set our views to display its
		 * information.
		 */
		super.onResume();
        setContentView(R.layout.counter_layout);
		Intent i = getIntent();
		clickedCounter = (Counter) i.getSerializableExtra("clickedCounter");
		counterNameDisplay = (TextView) findViewById(R.id.textCounterName);
		counterCountDisplay = (TextView) findViewById(R.id.textCount);
		counterNameDisplay.setText(clickedCounter.getCounterName());
		counterCountDisplay.setText(clickedCounter.getCount().toString());
	}

}
