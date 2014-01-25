/*
 * This should act as the main menu for the counter app. Basically be a title screen with a button
 * to go see the list of counters.
 * TODO: Add the function to make the button transfer to the button display activity. Maybe sparkle things
 * up a bit if I have time. Add functionality for reloading the CounterMaster when the app is reopened.
 */


package ualberta.hpabst.as1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	CounterMaster counterMaster;
	private static final String SAVEFILE = "savefile.sav";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onResume(){
    	super.onResume();
    	counterMaster = loadFromFile();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void saveInFile(CounterMaster c){
    	/*
    	 * Saves the current state of a CounterMaster to a file.
    	 */
    	try{
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
    		deleteFile(SAVEFILE);
    	}catch (FileNotFoundException e){
    		e.printStackTrace();
    	}catch (IOException e){
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return c;
    }
    
    public void toCountersDisplay(View view){
    	Intent counterListIntent = new Intent(ualberta.hpabst.as1.MainActivity.this,
    										  CounterListActivity.class);
    	testingSetup();
    	saveInFile(counterMaster);
    	startActivity(counterListIntent);
    }
    
    
    public void testingSetup(){
    	/*
    	 * Function used for testing the passing of objects through activites and the display
    	 * of the ListView in CounterListActivity.java.
    	 */
    	counterMaster.addCounter(new Counter("Pushups"));
    	counterMaster.addCounter(new Counter("Trains"));
    }
    
}
