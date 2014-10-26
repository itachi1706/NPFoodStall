package com.itachi1706.ngeeannfoodservice;

/**
 * Created by Kenneth on 26/10/2014.
 */
public class FoodItem {

    //Initialize Variables
    int _id;
    int _stall_id;
    String _name;
    double _price;

    public FoodItem(){}
    public FoodItem(int id, int stall_id, String name, double price){
        this._id = id;
        this._stall_id = stall_id;
        this._name = name;
        this._price = price;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getStallID(){
        return this._stall_id;
    }

    public void setStallID(int stall_id){
        this._stall_id = stall_id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public double getPrice(){
        return this._price;
    }

    public void setPrice(double price){
        this._price = price;
    }

}

