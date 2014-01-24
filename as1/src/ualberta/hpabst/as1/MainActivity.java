/*
 * This should act as the main menu for the counter app. Basically be a title screen with a button
 * to go see the list of counters.
 * TODO: Add the function to make the button transfer to the button display activity. Maybe sparkle things
 * up a bit if I have time. Add functionality for reloading the CounterMaster when the app is reopened.
 */


package ualberta.hpabst.as1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	CounterMaster counterMaster;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterMaster = new CounterMaster();
        testingSetup();//Should be removed after testing.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void toCountersDisplay(View view){
    	Intent counterListIntent = new Intent(ualberta.hpabst.as1.MainActivity.this,
    										  CounterListActivity.class);
    	counterListIntent.putExtra("counterMaster", counterMaster);
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
