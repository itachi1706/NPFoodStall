package com.itachi1706.ngeeannfoodservice;

import java.util.ArrayList;

import com.itachi1706.ngeeannfoodservice.init.FoodStallIListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ListStalls extends ActionBarActivity {
	
	ListView lvStalls;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_stalls);
		
		//Get the URL from the MainActivity class
		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			if (bundle.getString("location").isEmpty()){
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Error!");
				builder.setMessage("Unable to get location");
				builder.setPositiveButton(android.R.string.ok, new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						ListStalls.this.finish();
					}
					
				});
			} else {
				String location = bundle.getString("location");
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
					getActionBar().setTitle(getResources().getString(R.string.title_activity_list_stalls) + " " + location);
				} else {
					getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_list_stalls) + " " + location);
				}
				

				lvStalls = (ListView) findViewById(R.id.lvStalls);
				DatabaseHandler db = new DatabaseHandler(this);
				ArrayList<FoodStall> stalls = db.getAllFoodStallsFromLocation(location);
				
				FoodStallIListAdapter adapter = new FoodStallIListAdapter(this, R.layout.listview_food_stalls, stalls);
				lvStalls.setAdapter(adapter);
			}
		} else {
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_stalls, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
