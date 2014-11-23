package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.CartItem;


public class NotifyUserIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String foodName = this.getIntent().getStringExtra("food");
        final String location = this.getIntent().getStringExtra("location");
        final int qty = this.getIntent().getIntExtra("qty", 0);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String studentID = pref.getString("studentID", "Error: (No Student ID)");
        new AlertDialog.Builder(this).setTitle("Food Item Prepared")
                .setMessage("Student: " + studentID + "\nFood Item: " + foodName + "\nQuantity: " + qty + "\nLocation: " + location)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Mark as Received", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AlertDialog.Builder(NotifyUserIntent.this).setTitle(foodName).setMessage("Have you collected this item from the stall?\nWARNING: Clicking Yes is irreversible!")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                                CartItem item = db.getCartItem(foodName, location);
                                db.itemArrived(item);
                                Toast.makeText(getApplicationContext(), "Marked food as received", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }}).show();
        }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify_user_intent, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
