package com.itachi1706.ngeeannfoodservice.init;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014, 8:14 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class FoodInC4 {

	/*
	 * Stalls in Canteen 4 [16-18] (And their code)
	 * 16 - Wanton Noodles
	 * 17 - Western Food (Hot Potatoes)
	 * 18 - Drink Stall
	 */

    public static ArrayList<FoodStall> getFoodInC4(){
        ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
        stalls.add(new FoodStall(16,"Wanton Noodles", "Canteen 4", wantonNoodlesStall()));
        stalls.add(new FoodStall(17,"Western?", "Canteen 4", westernFoodStall()));
        stalls.add(new FoodStall(18,"Drink Stall", "Canteen 4", drinkFoodStall()));
        return stalls;
    }

    private static ArrayList<FoodItem> wantonNoodlesStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(1, 16, "Wanton Noodles", 3.5));
        items.add(new FoodItem(2, 16, "Wanton Soup", 3));
        return items;
    }

    private static ArrayList<FoodItem> westernFoodStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(1,17, "Fish & Chips", 4.5));
        items.add(new FoodItem(2,17, "Chicken Chop", 4.5));
        items.add(new FoodItem(3,17, "Spaghetti", 4.5));
        return items;
    }

    private static ArrayList<FoodItem> drinkFoodStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(1,18, "Iced Milo", 1.5));
        items.add(new FoodItem(2,18, "Iced Tea", 1));
        items.add(new FoodItem(3,18, "Coffee O", 0.6));
        return items;
    }
}

