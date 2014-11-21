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
public class MainReservedItemListAdapter extends ArrayAdapter<Cart> {

    private ArrayList<Cart> items;
    @SuppressWarnings("unused")
    private Context act;

    public MainReservedItemListAdapter(Context context, int textViewResourceId, ArrayList<Cart> objects){
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.act = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_reserved_item_main, parent, false);
        }

        Cart i = items.get(position);
        if (i != null){
            TextView itemName = (TextView) v.findViewById(R.id.tvCartName);
            TextView itemTime = (TextView) v.findViewById(R.id.tvCartTime);

            if (itemName != null){
                itemName.setText("Reserved Items #" + i.get_cartId());
            }
            if (itemTime != null){
                itemTime.setText(i.get_datetime());
            }
        }
        return v;
    }
}
