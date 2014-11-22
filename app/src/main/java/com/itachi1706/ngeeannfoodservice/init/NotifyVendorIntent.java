package com.itachi1706.ngeeannfoodservice.init;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.itachi1706.ngeeannfoodservice.R;

public class NotifyVendorIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notify_vendor_intent);
        String foodName = this.getIntent().getStringExtra("food");
        String location = this.getIntent().getStringExtra("location");
        int qty = this.getIntent().getIntExtra("qty", 0);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String studentID = pref.getString("studentID", "Error: (No Student ID)");
        new AlertDialog.Builder(this).setTitle("Incoming Food Reservation")
                .setMessage("Student: " + studentID + "\nFood Item: " + foodName + "\nQuantity: " + qty)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Notify Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Send a Notification to the orderer notifying that food is done
            }
        }).show();
    }
}
