package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.init.FoodStallIListAdapter;

import java.util.ArrayList;


public class ListStalls extends ActionBarActivity {

    ListView lvStalls;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stalls);

        //Get the URL from the MainActivity class
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.getString("location").isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error!");
                builder.setMessage("Unable to get location");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ListStalls.this.finish();
                    }

                });
            } else {
                location = bundle.getString("location");
                getSupportActionBar().setTitle(location + " " + getResources().getString(R.string.title_activity_list_stalls));


                lvStalls = (ListView) findViewById(R.id.lvStalls);
                DatabaseHandler db = new DatabaseHandler(this);
                ArrayList<FoodStall> stalls = db.getAllFoodStallsFromLocation(location);

                FoodStallIListAdapter adapter = new FoodStallIListAdapter(this, R.layout.listview_food_stalls, stalls);
                lvStalls.setAdapter(adapter);
                lvStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FoodStall stallSelected = (FoodStall) lvStalls.getItemAtPosition(position);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListStalls.this);
                        dialog.setTitle("Stall Selected").setMessage("Selected Stall: " + stallSelected.getName());
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                        Toast.makeText(getApplicationContext(), "Selected Stall: " + stallSelected.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
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
            Toast.makeText(getApplicationContext(), "LAUNCH SETTING ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_checkout){
            Toast.makeText(getApplicationContext(), "LAUNCH CHECKOUT ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_viewmap){
            //Toast.makeText(getApplicationContext(), "LAUNCH MAP ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ListStalls.this, ViewOnMaps.class).putExtra("location", location));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}