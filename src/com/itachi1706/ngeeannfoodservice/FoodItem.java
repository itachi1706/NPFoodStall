package com.itachi1706.ngeeannfoodservice;

public class FoodItem {
	
	//Initialize Variables
	int _id;
	int _stallid;
	String _name;
	double _price;
	
	public FoodItem(){}
	public FoodItem(int id, int stallid, String name, double price){
		this._id = id;
		this._stallid = stallid;
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
		return this._stallid;
	}
	
	public void setStallID(int stallid){
		this._stallid = stallid;
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
