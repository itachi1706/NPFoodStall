package com.itachi1706.ngeeannfoodservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;

import java.io.File;

/**
 * Created by Kenneth on 31/10/2014, 8:07 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice
 */
public class ShoppingCartDBHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "CartDB";

    //Table Names
    private static final String TABLE_CART = "cart";
    private static final String TABLE_CART_ITEM = "cartitem";

    //Food Table Keys
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_CART_DATETIME = "datetime";
    private static final String KEY_CART_CHECK = "reserved";

    //Food Item Table Keys
    private static final String KEY_CART_ITEM_ID = "id";
    private static final String KEY_CART_ITEM_CART_ID = "stall_id";
    private static final String KEY_CART_ITEM_NAME = "food_name";
    private static final String KEY_CART_ITEM_PRICE = "food_price";
    private static final String KEY_CART_ITEM_LOCATION = "food_location";
    private static final String KEY_CART_ITEM_QTY = "food_qty";

    public ShoppingCartDBHandler(Context context){
        super(context, context.getExternalFilesDir(null)
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the 2 tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "(" + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CART_DATETIME +
                " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + KEY_CART_CHECK + " INTEGER);";
        String CREATE_CART_ITEM_TABLE = "CREATE TABLE " + TABLE_CART_ITEM + "(" + KEY_CART_ITEM_ID + " INTEGER AUTOINCREMENT,"
                + KEY_CART_ITEM_CART_ID + " INTEGER," + KEY_CART_ITEM_NAME + " TEXT," + KEY_CART_ITEM_PRICE + " DOUBLE," +
                KEY_CART_ITEM_LOCATION + " TEXT," + KEY_CART_ITEM_QTY + " INTEGER," +
                "FOREIGN KEY (" + KEY_CART_ITEM_CART_ID + ") REFERENCES " + TABLE_CART + " (" + KEY_CART_ID + "), PRIMARY KEY (" + KEY_CART_ITEM_ID + ", " + KEY_CART_ITEM_CART_ID + "));";
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_CART_ITEM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEM);

        // Create tables again
        onCreate(db);
    }

    public int createCartRecordWithId(String date){
        String sql = "INSERT INTO " + TABLE_CART + "(" + KEY_CART_CHECK + ", " + KEY_CART_DATETIME + ") VALUES (0, " + date + ");";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        db = this.getReadableDatabase();
        String query = "SELECT * FROM SQLITE_SEQUENCE";
        Cursor cursor = db.rawQuery(query, null);
        int identity = -1;
        if (cursor.moveToFirst()){
            do{
                System.out.println("tableName: " +cursor.getString(cursor.getColumnIndex("name")));
                System.out.println("autoInc: " + cursor.getString(cursor.getColumnIndex("seq")));
                identity = cursor.getInt(cursor.getColumnIndex("seq"));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return identity;
    }

    /**
     * Check if there is a cart that has not been confirmed yet
     */
    public boolean checkIfCartAlreadyExist(){
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + KEY_CART_CHECK + " = 0;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        db.close();

        if (cursor.getCount() != 0){
            return true;
        }
        return false;
    }

    /**
     * Add a cart item into cart
     */
    public void addItemToCart(CartItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CART_ITEM_CART_ID, item.getCartID());
        values.put(KEY_CART_ITEM_LOCATION, item.get_location());
        values.put(KEY_CART_ITEM_NAME, item.get_name());
        values.put(KEY_CART_ITEM_QTY, item.get_qty());
        values.put(KEY_CART_ITEM_PRICE, item.get_price());
        db.insert(TABLE_CART_ITEM, null, values);
        db.close();
    }

    /**
     * Remove a cart item from the cart
     */
    public void removeItemFromCart(CartItem item){
        String query = "DELETE FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + item.getCartID() +
                " AND " + KEY_CART_ITEM_NAME + " = '" + item.get_name() + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    /**
     * Check if cart item exists already
     */
    public boolean checkIfCartItemExist(CartItem item){
        String query = "DELETE FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + item.getCartID() +
                " AND " + KEY_CART_ITEM_NAME + " = '" + item.get_name() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        db.close();

        if (cursor.getCount() != 0){
            return true;
        }
        return false;
    }

    /**
     * Modify a cart item qty
     */
    public void modifyCartItemQty(CartItem newitem){
        String query = "UPDATE " + TABLE_CART_ITEM + " SET " + KEY_CART_ITEM_QTY + " = " + newitem.get_qty() + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + newitem.getCartID() +
                " AND " + KEY_CART_ITEM_NAME + " = '" + newitem.get_name() + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
