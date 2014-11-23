package com.itachi1706.ngeeannfoodservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;

import java.io.File;
import java.util.ArrayList;

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
    private static final String KEY_CART_ITEM_CART_ID = "cart_id";
    private static final String KEY_CART_ITEM_NAME = "food_name";
    private static final String KEY_CART_ITEM_PRICE = "food_price";
    private static final String KEY_CART_ITEM_LOCATION = "food_location";
    private static final String KEY_CART_ITEM_QTY = "food_qty";
    private static final String KEY_CART_ITEM_STATUS = "food_status";

    public ShoppingCartDBHandler(Context context){
        super(context, context.getExternalFilesDir(null)
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the 2 tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "(" + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CART_DATETIME +
                " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + KEY_CART_CHECK + " INTEGER);";
        String CREATE_CART_ITEM_TABLE = "CREATE TABLE " + TABLE_CART_ITEM + "(" + KEY_CART_ITEM_ID + " INTEGER,"
                + KEY_CART_ITEM_CART_ID + " INTEGER," + KEY_CART_ITEM_NAME + " TEXT," + KEY_CART_ITEM_PRICE + " DOUBLE," +
                KEY_CART_ITEM_LOCATION + " TEXT," + KEY_CART_ITEM_QTY + " INTEGER," + KEY_CART_ITEM_STATUS + " INTEGER," +
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

    public void dropAndRebuildDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEM);
        onCreate(db);
    }

    public int createCartRecordWithId(String date){
        String sql = "INSERT INTO " + TABLE_CART + "(" + KEY_CART_CHECK + ", " + KEY_CART_DATETIME + ") VALUES (0, '" + date + "');";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        db = this.getReadableDatabase();
        db.close();
        return getLatestCartID();
    }

    public int getLatestCartID(){
        SQLiteDatabase db = this.getReadableDatabase();
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
        db.close();
        return identity;
    }

    /**
     * Check if there is a cart that has not been confirmed yet
     */
    public boolean checkIfCartAlreadyExist(){
        String query = "SELECT * FROM " + TABLE_CART + " WHERE " + KEY_CART_CHECK + " = 0;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Add a cart item into cart
     */
    public boolean addItemToCart(CartItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        if (!checkIfCartItemExist(item)) {
            ContentValues values = new ContentValues();
            values.put(KEY_CART_ITEM_CART_ID, item.getCartID());
            values.put(KEY_CART_ITEM_LOCATION, item.get_location());
            values.put(KEY_CART_ITEM_NAME, item.get_name());
            values.put(KEY_CART_ITEM_QTY, item.get_qty());
            values.put(KEY_CART_ITEM_PRICE, item.get_price());
            values.put(KEY_CART_ITEM_STATUS, 0);
            db.insert(TABLE_CART_ITEM, null, values);
            db.close();
            return true;
        }
        return false;
    }

    public void itemArrived(CartItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CART_ITEM + " SET " + KEY_CART_ITEM_STATUS + " = 1 WHERE " + KEY_CART_ITEM_CART_ID + " = " + item.getCartID() +
                " AND " + KEY_CART_ITEM_NAME + " = '" + item.get_name() + "';";
        db.execSQL(query);
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
        String query = "SELECT * FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + item.getCartID() +
                " AND " + KEY_CART_ITEM_NAME + " = '" + item.get_name() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * Checkout Cart
     */
    public void checkoutCart(){
        String query = "UPDATE " + TABLE_CART + " SET " + KEY_CART_CHECK + " =1 WHERE " + KEY_CART_CHECK + " =0;";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
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

    public ArrayList<Cart> getCartAndItem(){
        String queryString = "SELECT * FROM " + TABLE_CART + " WHERE " + KEY_CART_CHECK + " =0;";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cart> results = new ArrayList<Cart>();

        Cursor cursor = db.rawQuery(queryString, null);
        //Loop through and add to list
        if (cursor.moveToFirst()){
            do {
                Cart cart = new Cart();
                cart.set_cartId(cursor.getInt(0));
                cart.set_datetime(cursor.getString(1));
                if (cursor.getInt(2) == 0)
                    cart.set_confirmed(false);
                else
                    cart.set_confirmed(true);

                //Get food item
                ArrayList<CartItem> items = new ArrayList<CartItem>();
                String queryStringItems = "SELECT * FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + cart.get_cartId() + ";";
                Cursor subCursor = db.rawQuery(queryStringItems, null);
                if (subCursor.moveToFirst()){
                    do {
                        CartItem it = new CartItem();
                        it.setCartID(subCursor.getInt(1));
                        it.set_name(subCursor.getString(2));
                        it.set_location(subCursor.getString(4));
                        it.set_price(subCursor.getDouble(3));
                        it.set_qty(subCursor.getInt(5));
                        if (subCursor.getInt(6) == 1) {
                            it.set_status(true);
                        } else {
                            it.set_status(false);
                        }
                        items.add(it);
                    } while (subCursor.moveToNext());
                }

                cart.set_cartItems(items);
                results.add(cart);

            } while (cursor.moveToNext());
        }
        return results;
    }

    public ArrayList<CartItem> getListOfUnrecievedCartItems(){
        String queryString = "SELECT * FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_CHECK + " =0";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CartItem> results = new ArrayList<CartItem>();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                CartItem it = new CartItem();
                it.setCartID(cursor.getInt(1));
                it.set_name(cursor.getString(2));
                it.set_location(cursor.getString(4));
                it.set_price(cursor.getDouble(3));
                it.set_qty(cursor.getInt(5));
                if (cursor.getInt(6) == 1) {
                    it.set_status(true);
                } else {
                    it.set_status(false);
                }
                results.add(it);
            } while (cursor.moveToNext());
        }
        return results;
    }

    public CartItem getCartItem(String name, String location){
        String query = "SELECT * FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_LOCATION + " = '" + location +
                "' AND " + KEY_CART_ITEM_NAME + " = '" + name + "' AND " + KEY_CART_ITEM_STATUS + " =0;";
        SQLiteDatabase db = this.getReadableDatabase();
        CartItem result = new CartItem();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                result.set_name(cursor.getString(2));
                result.setCartID(cursor.getInt(1));
                result.set_qty(cursor.getInt(5));
                result.set_location(cursor.getString(4));
                result.set_price(cursor.getDouble(3));
                if (cursor.getInt(6) == 0)
                    result.set_status(false);
                else
                    result.set_status(true);

            } while (cursor.moveToNext());
        }
        return result;
    }

    public ArrayList<Cart> getReservedItems(){
        String queryString = "SELECT * FROM " + TABLE_CART + " WHERE " + KEY_CART_CHECK + " =1;";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Cart> results = new ArrayList<Cart>();

        Cursor cursor = db.rawQuery(queryString, null);
        //Loop through and add to list
        if (cursor.moveToFirst()){
            do {
                Cart cart = new Cart();
                cart.set_cartId(cursor.getInt(0));
                cart.set_datetime(cursor.getString(1));
                if (cursor.getInt(2) == 0)
                    cart.set_confirmed(false);
                else
                    cart.set_confirmed(true);

                //Get food item
                ArrayList<CartItem> items = new ArrayList<CartItem>();
                String queryStringItems = "SELECT * FROM " + TABLE_CART_ITEM + " WHERE " + KEY_CART_ITEM_CART_ID + " = " + cart.get_cartId() + ";";
                Cursor subCursor = db.rawQuery(queryStringItems, null);
                if (subCursor.moveToFirst()){
                    do {
                        CartItem it = new CartItem();
                        it.setCartID(subCursor.getInt(1));
                        it.set_name(subCursor.getString(2));
                        it.set_location(subCursor.getString(4));
                        it.set_price(subCursor.getDouble(3));
                        it.set_qty(subCursor.getInt(5));
                        if (subCursor.getInt(6) == 1) {
                            it.set_status(true);
                        } else {
                            it.set_status(false);
                        }
                        items.add(it);
                    } while (subCursor.moveToNext());
                }

                cart.set_cartItems(items);
                results.add(cart);

            } while (cursor.moveToNext());
        }
        return results;
    }
}
