package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;
import com.itachi1706.ngeeannfoodservice.cart.MainMenuUnclaimedItems;
import com.itachi1706.ngeeannfoodservice.init.FoodInC4;
import com.itachi1706.ngeeannfoodservice.init.FoodInMakan;
import com.itachi1706.ngeeannfoodservice.init.FoodInMunch;
import com.itachi1706.ngeeannfoodservice.init.FoodInPoolside;
import com.itachi1706.ngeeannfoodservice.init.InitializeDatabase;

import java.util.ArrayList;
import java.util.Iterator;


public class MainScreen extends ActionBarActivity {

    TextView label;
    ProgressDialog pDialog;
    ListView unclaimedFood, mainMenu;
    String[] menuItems = {"Reserve your food", "View Cart", "View Item reserved"};
    static final int DB_VER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        label = (TextView) findViewById(R.id.lblUnclaimed);
        mainMenu = (ListView) findViewById(R.id.lvMainMenu);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        mainMenu.setAdapter(adapter);
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) mainMenu.getItemAtPosition(position);
                Intent intent = null;
                if (selected.equals("Reserve your food")) {
                    intent = new Intent(MainScreen.this, SelectCanteen.class);

                } else if (selected.equals("View Cart")) {
                    intent = new Intent(MainScreen.this, CartActivity.class);

                } else if (selected.equals("View Item reserved")) {
                    intent = new Intent(MainScreen.this, ReservedItems.class);

                }
                startActivity(intent);

            }
        });
        initDatabase();

        unclaimedFood = (ListView) findViewById(R.id.lvMainItemsUnclaimed);
        checkUnclaimed();
    }

    private void checkUnclaimed(){
        ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
        ArrayList<Cart> carts = db.getReservedItems();
        final ArrayList<CartItem> finalizedItems = new ArrayList<CartItem>();
        //Check if any is still unclaimed
        for (Cart c : carts){
            ArrayList<CartItem> ci = c.get_cartItems();
            for (CartItem cii: ci){
                if (!cii.is_status()){
                    finalizedItems.add(cii);
                }
            }
        }
        if (finalizedItems.size() == 0){
            unclaimedFood.setVisibility(View.GONE);
            label.setVisibility(View.GONE);
        } else {
            unclaimedFood.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            final MainMenuUnclaimedItems ada = new MainMenuUnclaimedItems(this, R.layout.listview_reserved_item, finalizedItems);
            unclaimedFood.setAdapter(ada);
            unclaimedFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final CartItem foodSel = (CartItem) unclaimedFood.getItemAtPosition(position);
                    new AlertDialog.Builder(MainScreen.this).setTitle(foodSel.get_name())
                            .setMessage("Location: " + foodSel.get_location() + "\nQuantity: " + foodSel.get_qty() + "\nPrice: " + foodSel.get_price() + "\nReserved List: " + foodSel.getCartID())
                            .setNegativeButton("Close", null).setPositiveButton("Confirm Food Recieved", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(MainScreen.this).setTitle(foodSel.get_name()).setMessage("Have you collected this item from the stall?\nWARNING: Clicking Yes is irreversible!")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ShoppingCartDBHandler db = new ShoppingCartDBHandler(getApplicationContext());
                                            db.itemArrived(foodSel);
                                            foodSel.set_status(true);
                                            finalizedItems.remove(foodSel);
                                            ada.notifyDataSetChanged();
                                            unclaimedFood.setAdapter(ada);
                                            if (finalizedItems.size() == 0) {
                                                unclaimedFood.setVisibility(View.GONE);
                                                label.setVisibility(View.GONE);
                                            }
                                            Toast.makeText(getApplicationContext(), "Recieved " + foodSel.get_name() + " from " + foodSel.get_location(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).setNegativeButton("No", null).show();
                        }
                    }).show();
                }
            });
        }
    }

    @Override
    public void onPause(){
        super.onPause();    //Call superclass method first
        try {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }  catch (NullPointerException e){

        }
    }

    @Override
    public void onResume(){
        super.onResume();   //Call superclass method first

        initDatabase();
        checkUnclaimed();
    }

    public void initDatabase(){
        //Toast.makeText(getApplicationContext(), Build.PRODUCT.toString(), Toast.LENGTH_SHORT).show();
        if (DatabaseHandler.checkIfSDK()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Error with SDK :(")
                    .setMessage("There will be an error if you use an SDK to access emulator this app\n"
                                    + "Please use an actual Android device to test this application.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainScreen.this.finish();
                        }
                    });
            builder.show();
        } else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (sharedPref.getInt("dbValue", 0) != DB_VER) {
                DatabaseHandler db = new DatabaseHandler(this.getApplicationContext());
                pDialog = new ProgressDialog(this);
                pDialog.setCancelable(false);
                pDialog.setIndeterminate(true);
                pDialog.setTitle("Refreshing Database...");
                pDialog.setMessage("Updating Database of food stalls...");
                pDialog.show();

                new InitializeDatabase(pDialog, this).execute();
                sharedPref.edit().putInt("dbValue", DB_VER).apply();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Toast.makeText(getApplicationContext(), "LAUNCH SETTING ACTIVITY (UNIMPLEMENTED)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainScreen.this, AppSettings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
