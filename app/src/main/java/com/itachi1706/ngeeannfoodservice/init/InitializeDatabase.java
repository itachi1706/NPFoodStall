package com.itachi1706.ngeeannfoodservice.init;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.DatabaseHandler;
import com.itachi1706.ngeeannfoodservice.FoodStall;
import com.itachi1706.ngeeannfoodservice.MainScreen;

import java.util.ArrayList;

/**
 * Created by Kenneth on 29/10/2014, 2:15 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class InitializeDatabase extends AsyncTask<Void,Void,Boolean> {

    ProgressDialog pDialog;
    Context mContext;

    Boolean dropped = false;
    String name, stall;

    public InitializeDatabase(ProgressDialog dialog, Context context) {
        super();
        pDialog = dialog;
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //Init Database
        DatabaseHandler db = new DatabaseHandler(mContext);
        publishProgress();
        db.dropTablesAndRecreate();
        dropped = true;
        ArrayList<FoodStall> munch = FoodInMunch.getFoodInMunch();
        name = "Munch";
        for (int i = 0; i < munch.size(); i++){
            db.addFoodStall(munch.get(i));
            stall = munch.get(i).getName();
            publishProgress();
        }
        ArrayList<FoodStall> pool = FoodInPoolside.getFoodInPoolside();
        name = "Poolside";
        for (int i = 0; i < pool.size(); i++){
            db.addFoodStall(pool.get(i));
            stall = pool.get(i).getName();
            publishProgress();
        }
        ArrayList<FoodStall> makan = FoodInMakan.getFoodInMakan();
        name = "Makan Place";
        for (int i = 0; i < makan.size(); i++){
            db.addFoodStall(makan.get(i));
            stall = makan.get(i).getName();
            publishProgress();
        }
        ArrayList<FoodStall> c4 = FoodInC4.getFoodInC4();
        name = "Canteen 4";
        for (int i = 0; i < c4.size(); i++){
            db.addFoodStall(c4.get(i));
            stall = c4.get(i).getName();
            publishProgress();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if (response) {
            pDialog.dismiss();
            Toast.makeText(mContext.getApplicationContext(), "Database Refreshed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        if (dropped) {
            pDialog.setMessage("Updating Database of food stalls...\nInserting " + name + " Food Stall: " + stall);
        } else {
            pDialog.setMessage("Updating Database of food stalls...\nDropping Database");
        }
    }
}
