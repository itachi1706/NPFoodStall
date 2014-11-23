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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class SelectCanteen extends ActionBarActivity {

    ListView lvCanteen;
    String[] listOfCanteens = {"Makan Place", "Poolside", "Canteen 4", "Munch", "KFC", "Starbucks Coffee", "Coffee Bean", "McDonalds",
            "Pizza Hut", "MOS Burger", "Subway", "Sakae Sushi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_canteen);
        lvCanteen = (ListView) findViewById(R.id.lvSelectCanteen);

        //Displays List
        final ArrayAdapter<String> canteenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfCanteens);
        lvCanteen.setAdapter(canteenAdapter);

        //Based on item selected go to activity
        lvCanteen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                final String canteenSelected = (String) lvCanteen.getItemAtPosition(position);

                if (canteenSelected == listOfCanteens[0] || canteenSelected == listOfCanteens[1] || canteenSelected == listOfCanteens[2] || canteenSelected == listOfCanteens[3]) {

                    //Toast.makeText(getApplicationContext(), "Selected Canteen: " + canteenSelected, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectCanteen.this, ListStalls.class);
                    intent.putExtra("location", canteenSelected);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(SelectCanteen.this).setTitle("Unsupported Eatery")
                            .setMessage("Unfortunately, this eatery has yet to support this application. However, you can click the show on map button to show " +
                            "the nearest location of the eatery from the school.").setPositiveButton("Show on Maps", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SelectCanteen.this, ViewOnMaps.class);
                            intent.putExtra("location", canteenSelected);
                            startActivity(intent);
                        }
                    }).setNegativeButton(android.R.string.cancel, null).show();
                }
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
            //Toast.makeText(getApplicationContext(), "LAUNCH SETTING ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectCanteen.this, AppSettings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
