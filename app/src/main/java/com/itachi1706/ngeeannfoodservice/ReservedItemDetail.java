package com.itachi1706.ngeeannfoodservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;
import com.itachi1706.ngeeannfoodservice.cart.MainReservedItem;

import java.util.ArrayList;


public class ReservedItemDetail extends ActionBarActivity {

    TextView id, date, subTotal;
    ListView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_item_detail);

        id = (TextView) findViewById(R.id.tvReservedId);
        date = (TextView) findViewById(R.id.tvReservedDate);
        items = (ListView) findViewById(R.id.lvReservedItems);
        subTotal = (TextView) findViewById(R.id.tvReservedSubtotal);

        if (this.getIntent().hasExtra("cartID")) {

            int cartIdFromBundle = this.getIntent().getIntExtra("cartID", -1);
            if (cartIdFromBundle == -1){
                new AlertDialog.Builder(this).setTitle("EXCEPTION OCCURED").setMessage("Uh Oh! This ain't supposed to happen! Please notify the dev with error #BUNDLECARTINVALID")
                        .setCancelable(false).setPositiveButton("EXIT ACTIVITY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            } else {

                this.getSupportActionBar().setTitle(this.getResources().getString(R.string.title_activity_reserved_item_detail) + cartIdFromBundle);
                ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                ArrayList<Cart> carts = db.getReservedItems();
                Cart cart = null;
                for (Cart c : carts){
                    if (c.get_cartId() == cartIdFromBundle)
                        cart = c;
                }

                if (cart == null){
                    new AlertDialog.Builder(this).setTitle("EXCEPTION OCCURED").setMessage("Uh Oh! This ain't supposed to happen! Please notify the dev with error #INVALIDCART")
                            .setCancelable(false).setPositiveButton("EXIT ACTIVITY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    ArrayList<CartItem> cartitem = cart.get_cartItems();

                    id.setText(cart.get_cartId() + "");
                    date.setText(cart.get_datetime() + "");
                    final MainReservedItem adapter = new MainReservedItem(this, R.layout.listview_reserved_item, cartitem);
                    items.setAdapter(adapter);
                    calculateSubtotal();

                    items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final CartItem item = (CartItem) items.getItemAtPosition(position);
                            new AlertDialog.Builder(ReservedItemDetail.this).setTitle(item.get_name()).setMessage("Have you collected this item from the stall?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                                            db.itemArrived(item);
                                            adapter.notifyDataSetChanged();
                                            items.setAdapter(adapter);
                                            Toast.makeText(getApplicationContext(), "Recieved " + item.get_name() + " from " + item.get_location(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).setNegativeButton("No", null).show();
                        }
                    });

                }
            }
        } else {
            new AlertDialog.Builder(this).setTitle("EXCEPTION OCCURED").setMessage("Uh Oh! This ain't supposed to happen! Please notify the dev with error #BUNDLEINVALID")
                    .setCancelable(false).setPositiveButton("EXIT ACTIVITY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }

    private void calculateSubtotal(){
        double total = 0.00;
        for (int i = 0; i < items.getCount(); i++) {
            CartItem item = (CartItem) items.getItemAtPosition(i);
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
        getMenuInflater().inflate(R.menu.menu_reserved_item_detail, menu);
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
            startActivity(new Intent(ReservedItemDetail.this, AppSettings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
