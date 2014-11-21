package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.MainReservedItemListAdapter;

import java.util.ArrayList;


public class ReservedItems extends ActionBarActivity {

    ListView reservedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_items);

        if (this.getIntent().hasExtra("forward")){
            Intent intent = new Intent(ReservedItems.this, ReservedItemDetail.class);
            intent.putExtra("cartID", this.getIntent().getIntExtra("forward", -1));
            startActivity(intent);
        }

        reservedList = (ListView) findViewById(R.id.lvReservedList);
        ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
        ArrayList<Cart> carts = db.getReservedItems();
        MainReservedItemListAdapter adapter = new MainReservedItemListAdapter(this, R.layout.listview_reserved_item_main, carts);
        reservedList.setAdapter(adapter);
        reservedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cart cart = (Cart) reservedList.getItemAtPosition(position);
                Intent intent = new Intent(ReservedItems.this, ReservedItemDetail.class);
                intent.putExtra("cartID", cart.get_cartId());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reserved_items, menu);
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
            startActivity(new Intent(ReservedItems.this, AppSettings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
