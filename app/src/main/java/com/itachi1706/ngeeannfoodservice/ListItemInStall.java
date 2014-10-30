package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
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

import com.itachi1706.ngeeannfoodservice.init.FoodItemListAdapter;

import java.util.ArrayList;


public class ListItemInStall extends ActionBarActivity {

    ListView lvItems;
    FoodStall stall;
    ArrayList<FoodItem> foodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_in_stall);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getInt("foodStall", -1) == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error!");
                builder.setMessage("Unable to get Food Stall Info");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ListItemInStall.this.finish();
                    }

                });
            } else {
                DatabaseHandler db = new DatabaseHandler(this);
                stall = db.getFoodStall(bundle.getInt("foodStall"));
                foodItems = stall.getFoodItems();
                lvItems = (ListView) findViewById(R.id.lvItems);
                FoodItemListAdapter adapter = new FoodItemListAdapter(this, R.layout.listview_food_items, foodItems);
                lvItems.setAdapter(adapter);
                lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FoodItem itemSelected = (FoodItem) lvItems.getItemAtPosition(position);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListItemInStall.this);
                        dialog.setTitle("Item Selected").setMessage("Selected Item: " + itemSelected.getName() + "\nPrice: " +
                        itemSelected.getPrice());
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                        Toast.makeText(getApplicationContext(), "LAUNCH ALERTDIALOG(ITEMQUANTITY) [UNIMPLEMENTED]", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_item_in_stall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ListItemInStall.this, AppSettings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_checkout){
            Toast.makeText(getApplicationContext(), "LAUNCH CHECKOUT ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_viewmap){
            //Toast.makeText(getApplicationContext(), "LAUNCH MAP ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ListItemInStall.this, ViewOnMaps.class).putExtra("location", stall.getLocation()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
