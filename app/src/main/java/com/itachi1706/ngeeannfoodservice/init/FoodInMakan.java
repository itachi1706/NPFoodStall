package com.itachi1706.ngeeannfoodservice.init;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014, 8:14 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class FoodInMakan {

	/*
	 * Stalls in Makan Place [11-15](And their code)
	 * 9 - A&J
	 * 10 - Pines
	 * 11 - Hotplate Stall
	 * 12 - Ban Mian Stall
	 * 13 - Japanese Cuisine
	 *
	 */

    public static ArrayList<FoodStall> getFoodInMakan(){
        ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
        stalls.add(new FoodStall(9,"A&J", "Makan Place", westernStall()));
        stalls.add(new FoodStall(10,"Pines", "Makan Place", pinesStall()));
        stalls.add(new FoodStall(11,"Hotplate Stall", "Makan Place", hotplateStall()));
        stalls.add(new FoodStall(12,"Ban Mian Stall", "Makan Place", banMianStall()));
        stalls.add(new FoodStall(13,"Japanese Cuisine", "Makan Place", japStall()));
        return stalls;
    }

    private static ArrayList<FoodItem> westernStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 9, "Cheese Baked Rice", 4));
        return items;
    }
    private static ArrayList<FoodItem> pinesStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 10, "Nasi Goreng Kampung", 3.5));
        return items;
    }
    private static ArrayList<FoodItem> hotplateStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 11, "Fried Chicken with Spicy Tofu", 3.5));
        return items;
    }
    private static ArrayList<FoodItem> banMianStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 12, "Ban Mian", 2.8));
        return items;
    }
    private static ArrayList<FoodItem> japStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 13, "Kaki Fuyong", 3));
        return items;
    }
}

