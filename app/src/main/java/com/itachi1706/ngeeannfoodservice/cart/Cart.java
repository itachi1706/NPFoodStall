package com.itachi1706.ngeeannfoodservice.cart;

import android.content.Context;

import com.itachi1706.ngeeannfoodservice.ShoppingCartDBHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kenneth on 31/10/2014, 8:18 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.cart
 */
public class Cart {

    private int _cartId;
    private String _datetime;
    private boolean _confirmed;
    private ArrayList<CartItem> _cartItems;

    public Cart(){
    }

    public Cart(Context c){
        ShoppingCartDBHandler cart = new ShoppingCartDBHandler(c);
        this._datetime = getDateTime();
        this._confirmed = false;
        this._cartId = cart.createCartRecordWithId(_datetime);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int get_cartId() {
        return _cartId;
    }

    public boolean is_confirmed() {
        return _confirmed;
    }

    public void set_confirmed(boolean _confirmed) {
        this._confirmed = _confirmed;
    }

    public ArrayList<CartItem> get_cartItems() {
        return _cartItems;
    }

    public void set_cartItems(ArrayList<CartItem> _cartItems) {
        this._cartItems = _cartItems;
    }
}
