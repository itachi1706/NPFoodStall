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
	 * 1 - Nasi Lemak
	 * 2 - Western
	 * 3 - Vegetarian
	 * 4 - Drink Stall
	 * 5 - Soya Bean Stall
	 *
	 */

    public static ArrayList<FoodStall> getFoodInMunch(){
        ArrayList<FoodStall> stalls = new ArrayList<FoodStall>();
        stalls.add(new FoodStall(1,"Wanton Noodles", "Munch", nasiLemakStall()));
        stalls.add(new FoodStall(2,"Wanton Noodles", "Munch", nasiLemakStall2()));
        stalls.add(new FoodStall(3,"Wanton Noodles", "Munch", nasiLemakStall3()));
        stalls.add(new FoodStall(4,"Wanton Noodles", "Munch", nasiLemakStall4()));
        stalls.add(new FoodStall(5,"Wanton Noodles", "Munch", nasiLemakStall5()));
        return stalls;
    }

    private static ArrayList<FoodItem> nasiLemakStall(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 1, "Rice", 0.5));
        return items;
    }
    private static ArrayList<FoodItem> nasiLemakStall2(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 2, "Rice", 0.5));
        return items;
    }
    private static ArrayList<FoodItem> nasiLemakStall3(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 3, "Rice", 0.5));
        return items;
    }
    private static ArrayList<FoodItem> nasiLemakStall4(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 4, "Rice", 0.5));
        return items;
    }
    private static ArrayList<FoodItem> nasiLemakStall5(){
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        items.add(new FoodItem(0, 5, "Rice", 0.5));
        return items;
    }
}

