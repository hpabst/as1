/*
 * This is the class that provides an adapter between the screen display in
 * the CounterListActivity and the ArrayList of counters held in
 * the CounterMaster class.
 */

package ualberta.hpabst.as1;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CounterAdapter extends ArrayAdapter<Counter> {
	/*
	 * The majority of this code was modified from
	 * www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview
	 * 
	 */
	int resource;
	String response;
	Context context;
	
	public CounterAdapter(Context context, int resource, List<Counter> items){
		super(context,resource, items);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		RelativeLayout counterView;
		//Get the current counter object.
		Counter c = getItem(position);
		
		//Inflate the view.
		if(convertView==null){
			counterView = new RelativeLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, counterView, true);
		}
		else{
			counterView = (RelativeLayout) convertView;
		}
		//Get the text boxes from the counter_list_instance_layout.xml file.
		TextView counterName = (TextView) counterView.findViewById(R.id.counterInstName);
		TextView counterCount = (TextView) counterView.findViewById(R.id.counterInstCount);
		
		//Assign the appropriate data from the counter object above.
		counterName.setText(c.counterName);
		counterCount.setText(String.valueOf(c.count));
		
		return counterView;
		
	}

}
