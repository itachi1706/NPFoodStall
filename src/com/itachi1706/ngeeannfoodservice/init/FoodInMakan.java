package com.itachi1706.ngeeannfoodservice.init;

import java.util.ArrayList;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

public class FoodInMakan {
	
	/*
	 * Stalls in Makan Place [11-15](And their code)
	 * 11 - Drink Stall
	 * 12 - Fruit Stall
	 * 13 - Chicken Rice
	 * 14 - Japanese
	 * 15 - Italian Pasta?
	 * 
	 */

	public static ArrayList<FoodStall> getFoodInMakan(){
		ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
		stalls.add(new FoodStall(11,"Wanton Noodles", "Makan Place", example()));
		stalls.add(new FoodStall(12,"Wanton Noodles", "Makan Place", example2()));
		stalls.add(new FoodStall(13,"Wanton Noodles", "Makan Place", example3()));
		stalls.add(new FoodStall(14,"Wanton Noodles", "Makan Place", example4()));
		stalls.add(new FoodStall(15,"Wanton Noodles", "Makan Place", example5()));
		return stalls;
	}
	
	private static ArrayList<FoodItem> example(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 11, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example2(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 12, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example3(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 13, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example4(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 14, "Rice", 0.5));
		return items;
	}
	private static ArrayList<FoodItem> example5(){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		items.add(new FoodItem(0, 15, "Rice", 0.5));
		return items;
	}
}
