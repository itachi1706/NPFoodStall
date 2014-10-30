package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    static final int DB_VER = 1;

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
        initDatabase();
    }

    @Override
    public void onPause(){
        super.onPause();    //Call superclass method first
        try {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }  catch (NullPointerException e){

        }
    }

    @Override
    public void onResume(){
        super.onResume();   //Call superclass method first

        initDatabase();
    }

    public void initDatabase(){
        //Toast.makeText(getApplicationContext(), Build.PRODUCT.toString(), Toast.LENGTH_SHORT).show();
        if (DatabaseHandler.checkIfSDK()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Error with SDK :(")
                    .setMessage("There will be an error if you use an SDK to access emulator this app\n"
                                    + "Please use an actual Android device to test this application.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainScreen.this.finish();
                        }
                    });
            builder.show();
        } else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (sharedPref.getInt("dbValue", 0) != DB_VER) {
                DatabaseHandler db = new DatabaseHandler(this.getApplicationContext());
                pDialog = new ProgressDialog(this);
                pDialog.setCancelable(false);
                pDialog.setIndeterminate(true);
                pDialog.setTitle("Refreshing Database...");
                pDialog.setMessage("Updating Database of food stalls...");
                pDialog.show();

                new InitializeDatabase(pDialog, this).execute();
                sharedPref.edit().putInt("dbValue", DB_VER).apply();
            }
        }

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
            //Toast.makeText(getApplicationContext(), "LAUNCH SETTING ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainScreen.this, AppSettings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
