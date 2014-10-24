package com.itachi1706.ngeeannfoodservice.init;

import java.util.ArrayList;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

public class FoodInPoolside {
	
	/*
	 * Stalls in Poolside [6-10] (And their code)
	 * 6 - MOS Burger
	 * 7 - Hot Potato (Western Stall)
	 * 8 - Drink Stall
	 * 9 - Waffles Stall
	 * 10 - Handmade Noodles Stall
	 * 
	 */
	
	public static ArrayList<FoodStall> getFoodInPoolside(){
		ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
		stalls.add(new FoodStall(6,"Wanton Noodles", "Poolside", example1()));
		stalls.add(new FoodStall(7,"Wanton Noodles", "Poolside", example2()));
		stalls.add(new FoodStall(8,"Wanton Noodles", "Poolside", example3()));
		stalls.add(new FoodStall(9,"Wanton Noodles", "Poolside", example4()));
		stalls.add(new FoodStall(10,"Wanton Noodles", "Poolside", example5()));
		return stalls;
	}
	
	private static ArrayList<FoodItem> example1(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 6, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example2(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 7, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example3(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 8, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example4(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 9, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example5(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 10, "Rice", 0.5));
		return items;
	}

}
