package ualberta.hpabst.as1;
/*
 * Activity for the display of data for an individual counter. From here the user should be able to 
 * rename the counter, reset it back to 0, increment the count, and view statistics of the counter.
 * Associated XML layout file is counter_layout.xml.
 * TODO: Everything involved with this Activity.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class CounterInstanceActivity extends Activity {
	
	private Counter clickedCounter;
	private CounterMaster counterMaster;
	private TextView counterNameDisplay;
	private TextView counterCountDisplay;
	private static final String SAVEFILE = "savefile.sav";
	
	public void onResume(){
		/*
		 * Gets the counter that was clicked in the previous screen and set our views to display its
		 * information.
		 */
		super.onResume();
        setContentView(R.layout.counter_layout);
		Intent i = getIntent();
		int counterIndex = (int) i.getExtras().getInt("viewPos");
		counterMaster = loadFromFile();
		clickedCounter = counterMaster.getAllCounters().get(counterIndex);
		counterNameDisplay = (TextView) findViewById(R.id.textCounterName);
		counterCountDisplay = (TextView) findViewById(R.id.textCount);
		counterNameDisplay.setText(clickedCounter.getCounterName());//Should be changed after testing.
		counterCountDisplay.setText(clickedCounter.getCount().toString());//Should be changed after testing.	
		
	}
	
	public void displayStats(View view){
		/*
		 * Still need to fill out, should push the app to displaying
		 * statistics regarding the counter currently being viewed.
		 */
		Intent statsDisplay = new Intent(ualberta.hpabst.as1.CounterInstanceActivity.this,
										CounterStatsActivity.class);
		statsDisplay.putExtra("clickedCounter",clickedCounter);
		saveInFile(counterMaster);
		startActivity(statsDisplay);
	}
	
	public void resetCounter(View view){
		/*
		 * Still need to fill out, should prompt the user for confirmation
		 * and reset the counter back to 0. SAVE AFTER.
		 */
		clickedCounter.reset();
		saveInFile(counterMaster);
		counterCountDisplay.setText(clickedCounter.getCount().toString());

	}
	
	public void removeCounter(View view){
		/*
		 * SAVE AFTER
		 * This is broken atm due to passing of CounterMaster and counters by value
		 * rather than reference. Still need to add user confirmation.
		 */
		boolean test;
		test = counterMaster.getAllCounters().remove(clickedCounter);
		saveInFile(counterMaster);
		Log.i("removeCounter",String.format("Result of removceCounter was %s.", String.valueOf(test)));
		finish();
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
		AlertDialog.Builder renameAlert = new AlertDialog.Builder(this);
		renameAlert.setTitle("New Name Entry");
		renameAlert.setMessage("Please enter a new name for the current counter, names must be less than 15 characters long:");
		final EditText input = new EditText(this);
		renameAlert.setView(input);
		renameAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){
				String value = input.getText().toString();
				if(value.length() > 14){
					value = value.substring(0,15);
				}
				clickedCounter.setCounterName(value);
				saveInFile(counterMaster);
				counterNameDisplay.setText(clickedCounter.getCounterName());
			}
		});
		
		renameAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				/*
				 * We do nothing here since we're just leaving the current counter
				 * name as-is.
				 */
			}
		});
		renameAlert.show();
	}
	
	public void incrementCounter(View view){
		/*
		 * Still need to fill out, should increment the counter and redisplay
		 * the count.
		 * SAVE AFTER.
		 */
		clickedCounter.increment();
		saveInFile(counterMaster);
		counterCountDisplay.setText(clickedCounter.getCount().toString());
	}
	
    private void saveInFile(CounterMaster c){
    	/*
    	 * Saves the current state of a CounterMaster to a file.
    	 */
    	try{
    		deleteFile(SAVEFILE);
    		FileOutputStream stream = openFileOutput(SAVEFILE,Context.MODE_PRIVATE);
    		ObjectOutputStream objOut = new ObjectOutputStream(stream);
    		objOut.writeObject(c);
    		objOut.close();
    	}catch(FileNotFoundException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    private CounterMaster loadFromFile(){
    	/*
    	 * Loads the previously saved CounterMaster and deletes the save file.
    	 */
    	CounterMaster c = new CounterMaster();
    	try{
    		FileInputStream stream = openFileInput(SAVEFILE);
    		ObjectInputStream objIn = new ObjectInputStream(stream);
    		c = (CounterMaster) objIn.readObject();
    		objIn.close();
    	}catch (FileNotFoundException e){
    		e.printStackTrace();
    	}catch (IOException e){
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return c;
    }

}
