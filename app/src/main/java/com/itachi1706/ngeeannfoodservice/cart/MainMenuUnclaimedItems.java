package com.itachi1706.ngeeannfoodservice.cart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itachi1706.ngeeannfoodservice.R;

import java.util.ArrayList;

/**
 * Created by Kenneth on 30/10/2014, 12:53 PM
 * for NgeeAnnFoodService in package com.itachi1706.ngeeannfoodservice.init
 */
public class MainMenuUnclaimedItems extends ArrayAdapter<CartItem> {

    private ArrayList<CartItem> items;
    @SuppressWarnings("unused")
    private Context act;

    public MainMenuUnclaimedItems(Context context, int textViewResourceId, ArrayList<CartItem> objects){
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.act = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_reserved_item, parent, false);
        }

        CartItem i = items.get(position);
        if (i != null){
            TextView itemName = (TextView) v.findViewById(R.id.tvCartName);
            TextView itemPriceQty = (TextView) v.findViewById(R.id.tvPriceAndQty);
            TextView status = (TextView) v.findViewById(R.id.tvStatus);
            TextView itemLocation = (TextView) v.findViewById(R.id.tvCartLocation);

            if (itemName != null){
                itemName.setText(i.get_name());
            }
            if (itemPriceQty != null){
                itemPriceQty.setText("" + String.format("Qty: " + i.get_qty() + " ($%.2f)", i.get_price()));
            }
            if (status != null){
                if (i.is_status()) {  //Completed
                    status.setText("RECEIVED");
                    status.setTextColor(Color.rgb(0,100,0));
                } else {
                    status.setText("NOT RECEIVED");
                    status.setTextColor(Color.RED);
                }
            }
            if (itemLocation != null){
                itemLocation.setText(i.get_location() + " (List: " + i.getCartID() + ")");
            }
        }
        return v;
    }
}
