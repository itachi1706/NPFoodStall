package com.itachi1706.ngeeannfoodservice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
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
import com.itachi1706.ngeeannfoodservice.cart.MainMenuUnclaimedItems;
import com.itachi1706.ngeeannfoodservice.init.NotifyVendorIntent;

import org.w3c.dom.Text;

import java.util.ArrayList;

//PROTOTYPE
public class NotifyVendorActivity extends ActionBarActivity {

    ListView items;
    TextView lol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_vendor);

        items = (ListView) findViewById(R.id.lvItems);
        lol = (TextView) findViewById(R.id.tvLol);
        updateList();

    }

    private void updateList(){
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
            items.setVisibility(View.GONE);
            lol.setText("No Items. Please checkout a cart.");
        } else {
            items.setVisibility(View.VISIBLE);
            lol.setText(this.getResources().getString(R.string.list_vendor_notify));
            final MainMenuUnclaimedItems ada = new MainMenuUnclaimedItems(this, R.layout.listview_reserved_item, finalizedItems);
            items.setAdapter(ada);
            final Intent resultIntent = new Intent(this, NotifyVendorIntent.class);

            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final CartItem foodSel = (CartItem) items.getItemAtPosition(position);
                    resultIntent.putExtra("location", foodSel.get_location());
                    resultIntent.putExtra("food", foodSel.get_name());
                    resultIntent.putExtra("qty", foodSel.get_qty());
                    final PendingIntent notifyPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Toast.makeText(getApplicationContext(), "Sent a test notification for " + foodSel.get_name(), Toast.LENGTH_SHORT).show();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotifyVendorActivity.this)
                            .setSmallIcon(R.drawable.ic_launcher).setContentTitle("Item Reservation")
                            .setSubText(foodSel.get_qty() + "x " + foodSel.get_name() + " reserved!")
                            .setContentIntent(notifyPendingIntent).setAutoCancel(true)
                            .setContentText("Student/Staff ID: " + pref.getString("studentID", "None"))
                            .setTicker("New Food Reservation from " + pref.getString("studentID", "Unknown"));
                    int notifyID = (int) (100 * (Math.random()*100));
                    NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mgr.notify(notifyID, builder.build());
                }
            });

        }
    }
}
