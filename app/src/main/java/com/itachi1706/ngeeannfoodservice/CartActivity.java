package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;

import java.util.ArrayList;


public class CartActivity extends Activity {

    private TextView id, date;
    private ListView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        id = (TextView) findViewById(R.id.tvId);
        date = (TextView) findViewById(R.id.tvDate);
        items = (ListView) findViewById(R.id.lvItems);

        ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
        Cart cart;
        if (db.checkIfCartAlreadyExist()){
            cart = db.getCartAndItem().get(0);
            id.setText(cart.get_cartId());
            date.setText(cart.get_datetime());
            ArrayList<CartItem> itemCart = cart.get_cartItems();
            //TODO Present Cart Items into a listview (Requires custom listview)
        } else {
            new AlertDialog.Builder(getApplicationContext()).setTitle("No Cart").setMessage("No Cart Found!")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
