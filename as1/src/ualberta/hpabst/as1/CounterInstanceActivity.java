package ualberta.hpabst.as1;
/*
 * Activity for the display of data for an individual counter. From here the user should be able to 
 * rename the counter, reset it back to 0, increment the count, and view statistics of the counter.
 * Associated XML layout file is counter_layout.xml.
 * TODO: Everything involved with this Activity.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class CounterInstanceActivity extends Activity {
	
	Counter clickedCounter;
	CounterMaster counterMaster;
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
		counterMaster = (CounterMaster) i.getSerializableExtra("counterMaster");
		counterNameDisplay = (TextView) findViewById(R.id.textCounterName);
		counterCountDisplay = (TextView) findViewById(R.id.textCount);
		counterNameDisplay.setText(clickedCounter.getCounterName());
		counterCountDisplay.setText(clickedCounter.getCount().toString());
	}
	
	public void displayStats(View view){
		/*
		 * Still need to fill out, should push the app to displaying
		 * statistics regarding the counter currently being viewed.
		 */
	}
	
	public void resetCounter(View view){
		/*
		 * Still need to fill out, should prompt the user for confirmation
		 * and reset the counter back to 0. SAVE AFTER.
		 */

	}
	
	public void renameCounter(View view){
		/*
		 * Still need to fill out, should prompt the user to rename
		 * the counter currently being displayed then update the textview.
		 * SAVE AFTER.
		 */
		/*
		 * The code for this function was largely adapted from 
		 * www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		 */
		Log.i("renameCounter","I have been pressed.");
		AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
		resetAlert.setTitle("New Name Entry");
		resetAlert.setMessage("Please enter a new name for the current counter, names must be less than 15 characters long:");
		final EditText input = new EditText(this);
		resetAlert.setView(input);
		resetAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){
				String value = input.getText().toString();
				if(value.length() > 14){
					value = value.substring(0,15);
				}
				clickedCounter.setCounterName(value);
				counterNameDisplay.setText(clickedCounter.getCounterName());
			}
		});
		
		resetAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				/*
				 * We do nothing here since we're just leaving the current counter
				 * name as-is.
				 */
			}
		});
		resetAlert.show();
	}
	
	public void incrementCounter(View view){
		/*
		 * Still need to fill out, should increment the counter and redisplay
		 * the count.
		 * SAVE AFTER.
		 */
	}

}
