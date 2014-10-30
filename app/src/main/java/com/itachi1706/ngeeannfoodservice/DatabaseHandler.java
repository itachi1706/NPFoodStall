package com.itachi1706.ngeeannfoodservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "FoodStallsDatabase";

    //Table Names
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_FOOD_ITEM = "fooditem";

    //Food Table Keys
    private static final String KEY_FOOD_ID = "stall_id";
    private static final String KEY_FOOD_NAME = "name";
    private static final String KEY_FOOD_LOCATION = "location";

    //Food Item Table Keys
    private static final String KEY_FOOD_ITEM_ID = "id";
    private static final String KEY_FOOD_ITEM_STALL_ID = "stall_id";
    private static final String KEY_FOOD_ITEM_NAME = "food_name";
    private static final String KEY_FOOD_ITEM_PRICE = "food_price";

    public DatabaseHandler(Context context){
            super(context, context.getExternalFilesDir(null)
                    + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static boolean checkIfSDK(){
        if (Build.PRODUCT.startsWith("sdk")){
            return true;
        } else {
            return false;
        }
    }

    //Create the 2 tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "(" + KEY_FOOD_ID + " INTEGER PRIMARY KEY," + KEY_FOOD_NAME +
                " TEXT," + KEY_FOOD_LOCATION + " TEXT);";
        String CREATE_FOOD_ITEM_TABLE = "CREATE TABLE " + TABLE_FOOD_ITEM + "(" + KEY_FOOD_ITEM_ID + " INTEGER,"
                + KEY_FOOD_ITEM_STALL_ID + " INTEGER," + KEY_FOOD_ITEM_NAME + " TEXT," + KEY_FOOD_ITEM_PRICE + " DOUBLE, " +
                "FOREIGN KEY (" + KEY_FOOD_ITEM_STALL_ID + ") REFERENCES " + TABLE_FOOD + " (" + KEY_FOOD_ID + "), PRIMARY KEY (" + KEY_FOOD_ITEM_ID + ", " + KEY_FOOD_ITEM_STALL_ID + "));";
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_FOOD_ITEM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_ITEM);

        // Create tables again
        onCreate(db);
    }

    public void dropTablesAndRecreate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_ITEM);

        // Create tables again
        onCreate(db);
    }


    //Add to Food Stall and food stall item
    public void addFoodStall(FoodStall stall){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_ID, stall.getID());
        values.put(KEY_FOOD_NAME, stall.getName());
        values.put(KEY_FOOD_LOCATION, stall.getLocation());
        db.insert(TABLE_FOOD, null, values);

        //Get the ArrayList and parse it out
        ArrayList<FoodItem> items = stall.getFoodItems();

        for (int i = 0; i < items.size(); i++){
            FoodItem it = items.get(i);
            ContentValues subValues = new ContentValues();
            subValues.put(KEY_FOOD_ITEM_ID, it.getID());
            subValues.put(KEY_FOOD_ITEM_NAME, it.getName());
            subValues.put(KEY_FOOD_ITEM_STALL_ID, it.getStallID());
            subValues.put(KEY_FOOD_ITEM_PRICE, it.getPrice());
            db.insert(TABLE_FOOD_ITEM, null, subValues);
        }

        db.close();
    }

    //Get Food Stall based on Location
    public ArrayList<FoodStall> getAllFoodStallsFromLocation(String location){
        ArrayList<FoodStall> results = new ArrayList<FoodStall>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_FOOD + " WHERE " + KEY_FOOD_LOCATION + " = '" + location + "';";

        Cursor cursor = db.rawQuery(queryString, null);

        //Loop through and add to list
        if (cursor.moveToFirst()){
            do {
                FoodStall stall = new FoodStall();
                stall.setID(cursor.getInt(0));
                stall.setName(cursor.getString(1));
                stall.setLocation(cursor.getString(2));

                //Get food item
                ArrayList<FoodItem> items = new ArrayList<FoodItem>();
                String queryStringItems = "SELECT * FROM " + TABLE_FOOD_ITEM + " WHERE " + KEY_FOOD_ITEM_STALL_ID + " = " + stall.getID() + ";";
                Cursor subCursor = db.rawQuery(queryStringItems, null);
                if (subCursor.moveToFirst()){
                    do {
                        FoodItem it = new FoodItem();
                        it.setID(subCursor.getInt(0));
                        it.setStallID(subCursor.getInt(1));
                        it.setName(subCursor.getString(2));
                        it.setPrice(subCursor.getDouble(3));
                        items.add(it);
                    } while (subCursor.moveToNext());
                }

                stall.setFoodItems(items);

                results.add(stall);

            } while (cursor.moveToNext());
        }
        return results;
    }

    //Get Food Stall based on Stall Name
    public FoodStall getFoodStall(int stallId){
        FoodStall results = new FoodStall();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_FOOD + " WHERE " + KEY_FOOD_ID + " = '" + stallId + "';";

        Cursor cursor = db.rawQuery(queryString, null);

        //Loop through and add to list
        if (cursor.moveToFirst()){
            do {
                results.setID(cursor.getInt(0));
                results.setName(cursor.getString(1));
                results.setLocation(cursor.getString(2));

                //Get food item
                ArrayList<FoodItem> items = new ArrayList<FoodItem>();
                String queryStringItems = "SELECT * FROM " + TABLE_FOOD_ITEM + " WHERE " + KEY_FOOD_ITEM_STALL_ID + " = " + results.getID() + ";";
                Cursor subCursor = db.rawQuery(queryStringItems, null);
                if (subCursor.moveToFirst()){
                    do {
                        FoodItem it = new FoodItem();
                        it.setID(subCursor.getInt(0));
                        it.setStallID(subCursor.getInt(1));
                        it.setName(subCursor.getString(2));
                        it.setPrice(subCursor.getDouble(3));
                        items.add(it);
                    } while (subCursor.moveToNext());
                }

                results.setFoodItems(items);

            } while (cursor.moveToNext());
        }
        return results;
    }

    //Get number of counts in Food Stalls
    public int getFoodStallCount(){
        String query = "SELECT * FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        //return
        return cursor.getCount();
    }

}
