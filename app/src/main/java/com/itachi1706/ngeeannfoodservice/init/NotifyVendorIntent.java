package com.itachi1706.ngeeannfoodservice.init;

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

import com.itachi1706.ngeeannfoodservice.NotifyUserActivity;
import com.itachi1706.ngeeannfoodservice.R;

public class NotifyVendorIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notify_vendor_intent);
        final String foodName = this.getIntent().getStringExtra("food");
        final String location = this.getIntent().getStringExtra("location");
        final int qty = this.getIntent().getIntExtra("qty", 0);
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
                Intent intent = new Intent(NotifyVendorIntent.this, NotifyUserActivity.class);
                intent.putExtra("food", foodName);
                intent.putExtra("location", location);
                intent.putExtra("qty", qty);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Notified student that food has been prepared", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).show();
    }
}
