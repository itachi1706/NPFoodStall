package com.itachi1706.ngeeannfoodservice.cart;

import android.content.Context;
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
public class CartItemListAdapter extends ArrayAdapter<CartItem> {

    private ArrayList<CartItem> items;
    @SuppressWarnings("unused")
    private Context act;

    public CartItemListAdapter(Context context, int textViewResourceId, ArrayList<CartItem> objects){
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.act = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_cart_item, parent, false);
        }

        CartItem i = items.get(position);
        if (i != null){
            TextView itemName = (TextView) v.findViewById(R.id.tvCartName);
            TextView itemPrice = (TextView) v.findViewById(R.id.tvCartPrice);
            TextView itemQty = (TextView) v.findViewById(R.id.tvCartQty);
            TextView itemLocation = (TextView) v.findViewById(R.id.tvCartLocation);

            if (itemName != null){
                itemName.setText(i.get_name());
            }
            if (itemPrice != null){
                itemPrice.setText("$" + String.format("%.2f", i.get_price()));
            }
            if (itemQty != null){
                itemQty.setText("Qty: " + i.get_qty());
            }
            if (itemLocation != null){
                itemLocation.setText(i.get_location());
            }
        }
        return v;
    }
}
