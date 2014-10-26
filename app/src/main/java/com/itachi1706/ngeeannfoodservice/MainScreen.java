package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.init.FoodInC4;
import com.itachi1706.ngeeannfoodservice.init.FoodInMakan;
import com.itachi1706.ngeeannfoodservice.init.FoodInMunch;
import com.itachi1706.ngeeannfoodservice.init.FoodInPoolside;

import java.util.ArrayList;


public class MainScreen extends Activity {

    Button btnReserve;
    ProgressDialog pDialog;

    public static final int NUMBERSTALLS = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btnReserve = (Button) findViewById(R.id.btnReserve);
        btnReserve.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainScreen.this, SelectCanteen.class);
                startActivity(intent);
            }

        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.setTitle("Constructing Database...");
        pDialog.setMessage("Constructing Database of food stalls...");
        pDialog.show();

        //Init Database
        DatabaseHandler db = new DatabaseHandler(this);
        pDialog.setMessage("Constructing Database of food stalls...\nDropping Database");
        db.dropTablesAndRecreate();
        ArrayList<FoodStall> munch = FoodInMunch.getFoodInMunch();
        for (int i = 0; i < munch.size(); i++){
            db.addFoodStall(munch.get(i));
            pDialog.setMessage("Constructing Database of food stalls...\nInserting Munch Food Stall: " + munch.get(i).getName());
        }
        ArrayList<FoodStall> pool = FoodInPoolside.getFoodInPoolside();
        for (int i = 0; i < pool.size(); i++){
            db.addFoodStall(pool.get(i));
            pDialog.setMessage("Constructing Database of food stalls...\nInserting Poolside Food Stall: " + pool.get(i).getName());
        }
        ArrayList<FoodStall> makan = FoodInMakan.getFoodInMakan();
        for (int i = 0; i < makan.size(); i++){
            db.addFoodStall(makan.get(i));
            pDialog.setMessage("Constructing Database of food stalls...\nInserting Makan Place Food Stall: " + makan.get(i).getName());
        }
        ArrayList<FoodStall> c4 = FoodInC4.getFoodInC4();
        for (int i = 0; i < c4.size(); i++){
            db.addFoodStall(c4.get(i));
            pDialog.setMessage("Constructing Database of food stalls...\nInserting Canteen 4 Food Stall: " + c4.get(i).getName());
        }
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Database Refreshed", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
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
