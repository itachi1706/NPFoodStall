package com.itachi1706.ngeeannfoodservice.init;

import java.util.ArrayList;

import com.itachi1706.ngeeannfoodservice.FoodStall;
import com.itachi1706.ngeeannfoodservice.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodStallIListAdapter extends ArrayAdapter<FoodStall>{
	
	private ArrayList<FoodStall> objects;
	@SuppressWarnings("unused")
	private Context act;
	
	public FoodStallIListAdapter(Context context, int textViewResourceId, ArrayList<FoodStall> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
		this.act = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listview_food_stalls, parent, false);
		}
		
		FoodStall i = objects.get(position);
		if (i != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView stallName = (TextView) v.findViewById(R.id.tvStallName);	
			TextView stallCount = (TextView) v.findViewById(R.id.tvStallCount);	

			// check to see if each individual textview is null.
			// if not, assign some text!
			//Server Name
			if (stallName != null){
				stallName.setText(i.getName());
			}
			if (stallCount != null){
				stallCount.setText(i.getFoodItems().size() + "");
			}
		}
		return v;
	}

}
