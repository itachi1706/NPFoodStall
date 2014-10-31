package com.itachi1706.ngeeannfoodservice.cart;

/**
 * Created by Kenneth on 31/10/2014, 8:20 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.cart
 */
public class CartItem {

    private int _id, _cart_id, _qty;
    private String _name, _location;
    private double _price;
    int count = 0;

    public CartItem(){}
    public CartItem(int cartid, String name, String location, double price, int qty){
        this._id = count;
        count++;
        this._name = name;
        this._location = location;
        this._price = price;
        this._qty = qty;
    }

    public int getID(){ return this._id; }
    public void setCartID(int cart_id) { this._cart_id = cart_id; }
    public int getCartID(){ return this._cart_id; }
    public String get_location() {return _location;}
    public void set_location(String _location) {this._location = _location;}
    public String get_name() {return _name;}
    public void set_name(String _name) {this._name = _name;}
    public int get_qty() {return _qty;}
    public void set_qty(int _qty) {this._qty = _qty;}
    public double get_price() {return _price;}
    public void set_price(double _price) {this._price = _price;}
}
