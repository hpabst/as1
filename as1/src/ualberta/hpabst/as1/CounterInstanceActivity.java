package ualberta.hpabst.as1;
/*
 * Activity for the display of data for an individual counter. From here the user should be able to 
 * rename the counter, reset it back to 0, increment the count, and view statistics of the counter.
 * Associated XML layout file is counter_layout.xml.
 * 
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
		counterNameDisplay.setText(clickedCounter.getCounterName());
		counterCountDisplay.setText(clickedCounter.getCount().toString());	
		
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
		 * Resets the counter being viewed back to 0.
		 * Code for this method was modified from
		 * www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		 */
		AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
		resetAlert.setTitle("Counter Reset");
		resetAlert.setMessage("Are you sure? This will reset the counter back to 0.");
		resetAlert.setPositiveButton("I'm Sure", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){
				clickedCounter.reset();
				saveInFile(counterMaster);
				counterCountDisplay.setText(clickedCounter.getCount().toString());
			}
		});
		resetAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){}
		});
		resetAlert.show();
	}
	
	public void removeCounter(View view){
		/*
		 * Removes the counter being view and takes the user back to the CounterListActivity.
		 * Code for this method was modified from
		 * www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		 */
		AlertDialog.Builder removalAlert = new AlertDialog.Builder(this);
		removalAlert.setTitle("Counter Removal");
		removalAlert.setMessage("Are you sure? A counter cannot be recovered once removed.");
		removalAlert.setPositiveButton("I'm Sure", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){
				boolean test;
				test = counterMaster.getAllCounters().remove(clickedCounter);
				saveInFile(counterMaster);
				Log.i("removeCounter",String.format("Result of removceCounter was %s.", String.valueOf(test)));
				finish();
			}
		});
		removalAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int button){}
		});
		removalAlert.show();
	}
	
	public void renameCounter(View view){
		/*
		 * Prompts the user to enter a new name for the counter currently being viewed.
		 * 
		 * The code for this method was largely adapted from 
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
		 * Increments the counter and causes a redisplay of the new count.
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
