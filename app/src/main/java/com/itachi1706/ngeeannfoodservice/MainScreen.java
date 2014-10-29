package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.init.FoodInC4;
import com.itachi1706.ngeeannfoodservice.init.FoodInMakan;
import com.itachi1706.ngeeannfoodservice.init.FoodInMunch;
import com.itachi1706.ngeeannfoodservice.init.FoodInPoolside;
import com.itachi1706.ngeeannfoodservice.init.InitializeDatabase;

import java.util.ArrayList;


public class MainScreen extends ActionBarActivity {

    Button btnReserve, btnCart, btnReserved;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btnReserve = (Button) findViewById(R.id.btnReserve);
        btnReserve.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, SelectCanteen.class);
                startActivity(intent);
            }

        });

        btnCart = (Button) findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "LAUNCH CART ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            }

        });

        btnReserved = (Button) findViewById(R.id.btnReceipts);
        btnReserved.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "LAUNCH RESERVE ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            }

        });

        DatabaseHandler db = new DatabaseHandler(this);
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);
            pDialog.setTitle("Refreshing Database...");
            pDialog.setMessage("Updating Database of food stalls...");
            pDialog.show();

        new InitializeDatabase(pDialog, this).execute();
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
            Toast.makeText(getApplicationContext(), "LAUNCH SETTING ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
