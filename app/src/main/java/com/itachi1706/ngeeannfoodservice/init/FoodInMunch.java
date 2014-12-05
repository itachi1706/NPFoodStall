package com.itachi1706.ngeeannfoodservice.init;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014, 8:14 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class FoodInMunch {

	/*
	 * Stalls in Munch [1-5] (And their code)
	 * 1 - Nasi Ayam Panggang
	 * 2 - Western
	 * 3 - Pepper Lunch
	 * 4 - Drink Stall
	 * 5 - Wanton Noodles
	 *
	 */

    public static ArrayList<FoodStall> getFoodInMunch(){
        ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
        stalls.add(new FoodStall(1,"Nasi Ayam Panggang", "Munch", pangangStall()));
        stalls.add(new FoodStall(2,"Western", "Munch", westernStall()));
        stalls.add(new FoodStall(3,"Pepper Lunch", "Munch", deprecratedPepperLunchStall()));
        stalls.add(new FoodStall(4,"Drink Stall", "Munch", drinkStall()));
        stalls.add(new FoodStall(5,"Wanton Noodles", "Munch", wantonStall()));
        return stalls;
    }

    private static ArrayList<FoodItem> pangangStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 1, "Nasi Ayam Panggang", 2.8));
        items.add(new FoodItem(0, 1, "Roasted Panggang", 3.5));
        return items;
    }
    private static ArrayList<FoodItem> westernStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 2, "Chicken Chop", 3.5));
        return items;
    }
    private static ArrayList<FoodItem> deprecratedPepperLunchStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 3, "Curry Sizzling Rice", 4.5));
        return items;
    }
    private static ArrayList<FoodItem> drinkStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 4, "Iced Milo", 1.5));
        items.add(new FoodItem(0, 4, "Iced Tea", 1));
        items.add(new FoodItem(0, 4, "Coffee O", 0.6));
        return items;
    }
    private static ArrayList<FoodItem> wantonStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 5, "Fishball Soup", 3));
        return items;
    }
}

