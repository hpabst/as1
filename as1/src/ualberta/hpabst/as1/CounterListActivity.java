/*
 * This is the activity for displaying the list of counters with the ability to view or remove
 * individual counters. Should as well have a button that will sort the counters based on their count and one
 * that allows the generation of a new counter.
 * Corresponding XML layout file is counter_list_layout.xml.
 */

package ualberta.hpabst.as1;

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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;


public class CounterListActivity extends Activity {

	
	private ListView lstTest;
	private CounterAdapter arrayAdapter;
	private CounterMaster counterMaster;
	private static final String SAVEFILE = "savefile.sav";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.counter_list_layout);
	}
	
	public void onResume(){
		super.onResume();
		counterMaster = loadFromFile();
		
		/*
		 * A large amount of code for this method was adapted from
		 * www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/
		 * 
		 */
		
		//Initialize ListView
		lstTest = (ListView) findViewById(R.id.counterListView);
		
		//Initialize our array adapter notice how it references the counter_list_instance_layout.xml layout
		arrayAdapter = new CounterAdapter(this, R.layout.counter_list_instance_layout,
											counterMaster.getAllCounters());	
		lstTest.setAdapter(arrayAdapter);
		lstTest.setOnItemClickListener(new OnItemClickListener(){
			/*
			 * When one of the counters is clicked, we want to go to a new activity
			 * where we can view and increment it.
			 * 
			 */
			
			@Override
			public void onItemClick(AdapterView<?> parent, View clickView, int position, long id){
				Intent counterInstance = new Intent(ualberta.hpabst.as1.CounterListActivity.this,
													CounterInstanceActivity.class);
				counterInstance.putExtra("viewPos", position);
				/*
				 * We pass a copy of the clicked counter along so we can modify
				 * the version in the counterMaster and preserve changes.
				 */
				saveInFile(counterMaster);
				startActivity(counterInstance);
			}
		});
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
		 * Prompts the user for the entry of the name of the new counter.
		 *
		 *
		 * This segment of code was taken from
		 * www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		 */
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Name Entry");
		alert.setMessage("Please enter a name for the new counter, names must be less than 15 characters.");
		final EditText input = new EditText(this);
		alert.setView(input);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				String value = input.getText().toString();
				if(value.length() > 14){
					value = value.substring(0,15);
				}
				counterMaster.addCounter(new Counter(value));
				saveInFile(counterMaster);
		}
		});
		
		alert.setNegativeButton("Cancel",  new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichButton){
				
			}
		});
		
		alert.show();
		
	}
	
	public void sortCounters(MenuItem item){
		/*
		 *Sorts the list of counters and calls arrayAdapter to update the ListView.
		 */
		counterMaster.sortAllCounters();
		Log.i("Sort counters button:", "I have been pressed.");

		saveInFile(counterMaster);
		arrayAdapter.notifyDataSetChanged();
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
