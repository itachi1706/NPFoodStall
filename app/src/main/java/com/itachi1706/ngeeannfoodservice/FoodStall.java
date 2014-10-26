package com.itachi1706.ngeeannfoodservice;

import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014.
 */
public class FoodStall {

    //Variables
    int _id;
    String _name;
    String _location;
    ArrayList<FoodItem> _itemsInStall;

    public FoodStall(){}
    public FoodStall(int id, String name, String location){
        this._id = id;
        this._name = name;
        this._location = location;
    }
    public FoodStall(int id, String name, String location, ArrayList<FoodItem> itemsInStall){
        this._id = id;
        this._name = name;
        this._location = location;
        this._itemsInStall = itemsInStall;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getLocation(){
        return this._location;
    }

    public void setLocation(String location){
        this._location = location;
    }

    public ArrayList<FoodItem> getFoodItems(){
        return this._itemsInStall;
    }

    public void setFoodItems(ArrayList<FoodItem> items){
        this._itemsInStall = items;
    }


}
