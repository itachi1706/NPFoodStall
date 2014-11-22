package com.itachi1706.ngeeannfoodservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;
import com.itachi1706.ngeeannfoodservice.cart.CartItemListAdapter;

import java.util.ArrayList;


public class CartActivity extends ActionBarActivity {

    TextView id, date, subTotal;
    ListView itemsList;
    int cartIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        id = (TextView) findViewById(R.id.tvId);
        date = (TextView) findViewById(R.id.tvDate);
        itemsList = (ListView) findViewById(R.id.lvItems);
        subTotal = (TextView) findViewById(R.id.tvSubtotal);

        ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
        Cart cart;
        if (db.checkIfCartAlreadyExist()){
            cart = db.getCartAndItem().get(0);
            id.setText(cart.get_cartId() + "");
            date.setText(cart.get_datetime() + "");
            final ArrayList<CartItem> itemCart = cart.get_cartItems();
            final CartItemListAdapter adapter = new CartItemListAdapter(this, R.layout.listview_cart_item, itemCart);
            itemsList.setAdapter(adapter);
            Log.d("Cart", "Set Adapter Successfully");
            calculateSubtotal();
            Log.d("Cart", "Subtotal Calculated!");
            Log.d("CartItem", "Number of Items in cart: " + cart.get_cartItems().size());
            cartIDs = cart.get_cartId();
            itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final CartItem item = (CartItem) itemsList.getItemAtPosition(position);
                    String itemName = item.get_name();
                    new AlertDialog.Builder(CartActivity.this).setTitle("Item Details")
                            .setMessage("Location: " + item.get_location() + "\nQuantity: " + item.get_qty() + "\nPrice: " + item.get_price())
                            .setPositiveButton("Close",null).setNeutralButton("Change Quantity", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final EditText input = new EditText(getApplicationContext());
                            input.setHint("Insert New Quantity");
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            input.setTextColor(Color.BLACK);
                            new AlertDialog.Builder(CartActivity.this).setTitle("Change Quantity")
                                    .setMessage("Input new Quantity for " + item.get_name()).setView(input)
                                    .setNegativeButton(android.R.string.cancel, null).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                                    CartItem newItem = item;
                                    newItem.set_qty(Integer.parseInt(input.getText().toString()));
                                    Toast.makeText(getApplicationContext(), "Quantity Changed", Toast.LENGTH_SHORT).show();
                                    db.modifyCartItemQty(newItem);
                                    adapter.notifyDataSetChanged();
                                    itemsList.setAdapter(adapter);
                                    calculateSubtotal();
                                }
                            }).show();
                        }
                    }).setNegativeButton("Delete Item", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                            db.removeItemFromCart(item);
                            itemCart.remove(item);
                            Toast.makeText(getApplicationContext(), "Removed Item", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            itemsList.setAdapter(adapter);
                        }
                    }).show();
                }
            });
        } else {
            new AlertDialog.Builder(this).setTitle("No Cart").setMessage("No Cart Found!").setCancelable(false)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

    }

    private void calculateSubtotal(){
        double total = 0.00;
        for (int i = 0; i < itemsList.getCount(); i++) {
            CartItem item = (CartItem) itemsList.getItemAtPosition(i);
            int qty = item.get_qty();
            double baseCost = item.get_price();
            double totalItemCost = qty * baseCost;
            total += totalItemCost;
        }
        subTotal.setText(String.format("Subtotal: $%.2f", total));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
            startActivity(new Intent(CartActivity.this, AppSettings.class));
            return true;
        } else if (id == R.id.action_checkout){
            ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
            db.checkoutCart();
            new AlertDialog.Builder(this).setCancelable(false).setTitle("Checkout Completed!")
                    .setMessage("Food Items have been reserved. Please head over to the respective canteens when you receive the notification to collect the food!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("View Reserved Item", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "LAUNCH RESERVE ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, ReservedItems.class);
                    intent.putExtra("forward", cartIDs);
                    startActivity(intent);
                    finish();
                }
            }).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
