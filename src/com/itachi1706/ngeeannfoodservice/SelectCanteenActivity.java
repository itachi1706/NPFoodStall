package com.itachi1706.ngeeannfoodservice;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectCanteenActivity extends ActionBarActivity {
	
	ListView lvCanteen;
	String[] listOfCanteens = {"Makan Place", "Poolside", "Canteen 4", "Munch", "KFC", "Starbucks", "Coffee Bean"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_canteen);
		lvCanteen = (ListView) findViewById(R.id.lvSelectCanteen);
		
		//Displays List
		ArrayAdapter<String> canteenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfCanteens);
		lvCanteen.setAdapter(canteenAdapter);
		
		//Based on item selected go to activity
		lvCanteen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String canteenSelected = (String) lvCanteen.getItemAtPosition(position);
				
				Toast.makeText(getApplicationContext(), "Selected Canteen: " + canteenSelected, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SelectCanteenActivity.this, ListStalls.class);
				intent.putExtra("location", canteenSelected);
				startActivity(intent);
				
				/*
				AlertDialog.Builder builder = new Builder(SelectCanteenActivity.this);
				builder.setTitle("Canteen Selected");
				builder.setMessage("Selected canteen: " + canteenSelected);
				builder.setPositiveButton(android.R.string.ok, new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.show();*/
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_canteen, menu);
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
