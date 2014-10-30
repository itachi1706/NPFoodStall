package com.itachi1706.ngeeannfoodservice.init;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itachi1706.ngeeannfoodservice.FoodItem;
import com.itachi1706.ngeeannfoodservice.R;

import java.util.ArrayList;

/**
 * Created by Kenneth on 30/10/2014, 12:53 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class FoodItemListAdapter extends ArrayAdapter<FoodItem> {

    private ArrayList<FoodItem> items;
    @SuppressWarnings("unused")
    private Context act;

    public FoodItemListAdapter(Context context, int textViewResourceId, ArrayList<FoodItem> objects){
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.act = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_food_items, parent, false);
        }

        FoodItem i = items.get(position);
        if (i != null){
            TextView itemName = (TextView) v.findViewById(R.id.tvItemName);
            TextView itemPrice = (TextView) v.findViewById(R.id.tvItemPrice);

            if (itemName != null){
                itemName.setText(i.getName());
            }
            if (itemPrice != null){
                itemPrice.setText("$" + i.getPrice());
            }
        }
        return v;
    }
}
