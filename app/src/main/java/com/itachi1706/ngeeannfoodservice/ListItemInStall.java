package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;
import com.itachi1706.ngeeannfoodservice.init.FoodItemListAdapter;

import java.util.ArrayList;
import java.util.Date;


public class ListItemInStall extends ActionBarActivity {

    ListView lvItems;
    FoodStall stall;
    ArrayList<FoodItem> foodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_in_stall);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getInt("foodStall", -1) == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error!");
                builder.setMessage("Unable to get Food Stall Info");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ListItemInStall.this.finish();
                    }

                });
            } else {
                DatabaseHandler db = new DatabaseHandler(this);
                stall = db.getFoodStall(bundle.getInt("foodStall"));
                foodItems = stall.getFoodItems();
                lvItems = (ListView) findViewById(R.id.lvItems);
                FoodItemListAdapter adapter = new FoodItemListAdapter(this, R.layout.listview_food_items, foodItems);
                lvItems.setAdapter(adapter);
                lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final FoodItem itemSelected = (FoodItem) lvItems.getItemAtPosition(position);
                        final EditText input = new EditText(getApplicationContext());
                        input.setHint("Insert Quantity to Reserve");
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                        input.setTextColor(Color.BLACK);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListItemInStall.this);
                        dialog.setView(input);
                        dialog.setTitle("Item Selected").setMessage("Selected Item: " + itemSelected.getName() + "\nPrice: " +
                                itemSelected.getPrice());
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (input.getText().length() == 0){
                                    Toast.makeText(getApplicationContext(), "Quantity must be filled before confirming", Toast.LENGTH_SHORT).show();
                                } else {
                                    int qty = Integer.parseInt(input.getText().toString());
                                    ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                                    Cart cart;
                                    if (db.checkIfCartAlreadyExist()) {
                                        cart = db.getCartAndItem().get(0);
                                    } else {
                                        cart = new Cart(getApplicationContext());
                                    }
                                    CartItem ci = new CartItem(cart.get_cartId(), itemSelected.getName(), stall.getLocation() + " - " + stall.getName(), itemSelected.getPrice(), qty);
                                    ArrayList<CartItem> carts = cart.get_cartItems();
                                    carts.add(ci);
                                    cart.set_cartItems(carts);
                                    if (db.addItemToCart(ci)) {
                                        new AlertDialog.Builder(ListItemInStall.this).setTitle("Item Added").setMessage("Reserved " + qty + " " + itemSelected.getName())
                                                .setPositiveButton(android.R.string.ok, null).setNegativeButton("Go to Cart", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(ListItemInStall.this, CartActivity.class));
                                            }
                                        }).show();
                                    } else {
                                        new AlertDialog.Builder(ListItemInStall.this).setTitle("Error").setMessage("Item Already Exists in the cart!")
                                                .setPositiveButton(android.R.string.ok, null).setNegativeButton("Go to Cart", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(ListItemInStall.this, CartActivity.class));
                                            }
                                        }).show();
                                    }
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_item_in_stall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ListItemInStall.this, AppSettings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_checkout){
            startActivity(new Intent(ListItemInStall.this, CartActivity.class));
            return true;
        } else if (id == R.id.action_viewmap){
            //Toast.makeText(getApplicationContext(), "LAUNCH MAP ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ListItemInStall.this, ViewOnMaps.class).putExtra("location", stall.getLocation()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
