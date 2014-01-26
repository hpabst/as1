/*
 * This is the activity first launched when the  app is started. It displays the
 * opening title and a button to go look at the list of counters.
 */


package ualberta.hpabst.as1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	CounterMaster counterMaster;
	private static final String SAVEFILE = "savefile.sav";
	private long msExtra;
	

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
    
    public void toCountersDisplay(View view){
    	Intent counterListIntent = new Intent(ualberta.hpabst.as1.MainActivity.this,
    										  CounterListActivity.class);
    	saveInFile(counterMaster);
    	startActivity(counterListIntent);
    }
    
    
    public void testingSetup(){
    	/*
    	 * Function used for testing the passing of objects through activites and the display
    	 * of the ListView in CounterListActivity.java.
    	 */
    	Counter tmp;
    	if (counterMaster.getAllCounters().isEmpty()){
    		msExtra = 1320000;
    	counterMaster.addCounter(new Counter("Pushups"));
    	counterMaster.addCounter(new Counter("Trains"));
    	List<Date> dates = new ArrayList<Date>();
    	Date currentDate = new Date();
    	for (int j = 0; j <= 100; j++){
    		dates.add(new Date(currentDate.getTime() + (j * msExtra)));
    	}
    	for (int i = 0; i <= 100; i++){
    		tmp = new Counter("Cnt" + String.valueOf(i));
    		tmp.setCountTimes(dates);
    		tmp.setCount(100);
    		counterMaster.addCounter(tmp);
    	}
    	}
    }
    
}
