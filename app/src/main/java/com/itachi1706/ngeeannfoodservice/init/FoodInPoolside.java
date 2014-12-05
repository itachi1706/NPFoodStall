package com.itachi1706.ngeeannfoodservice.init;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.FoodStall;

import java.util.ArrayList;

/**
 * Created by Kenneth on 26/10/2014, 8:14 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class FoodInPoolside {

    /*
	 * Stalls in Poolside [6-10] (And their code)
	 * 6 - Malay Store
	 * 7 - Hot Potato (Western Stall)
	 * 8 - Drink Stall
	 *
	 */

    public static ArrayList<FoodStall> getFoodInPoolside(){
        ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
        stalls.add(new FoodStall(6,"Malay Store", "Poolside", example1()));
        stalls.add(new FoodStall(7,"Hot Potato (Western)", "Poolside", example2()));
        stalls.add(new FoodStall(8,"Drinks Stall", "Poolside", example3()));
        return stalls;
    }

    private static ArrayList<FoodItem> example1(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 6, "Nasi Ayam Penyet", 3));
        return items;
    }
    private static ArrayList<FoodItem> example2(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 7, "Chicken Chop w Rice", 4));
        items.add(new FoodItem(1, 7, "Scrambled Egg", 1));
        items.add(new FoodItem(2, 7, "Cheese Fries", 2));
        items.add(new FoodItem(3, 7, "Grilled Chicken w Noodle", 4));
        return items;
    }
    private static ArrayList<FoodItem> example3(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 8, "Iced Milo", 1));
        items.add(new FoodItem(1, 8, "Barley", 1));
        return items;
    }

}

