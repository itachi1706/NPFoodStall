package com.itachi1706.ngeeannfoodservice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.ngeeannfoodservice.cart.Cart;
import com.itachi1706.ngeeannfoodservice.cart.CartItem;
import com.itachi1706.ngeeannfoodservice.cart.MainMenuUnclaimedItems;
import com.itachi1706.ngeeannfoodservice.init.InitializeDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainScreen extends ActionBarActivity {

    TextView label, stdId;
    ProgressDialog pDialog;
    ListView unclaimedFood, mainMenu;
    String[] menuItems = {"Reserve your food", "View Cart", "View Reserved Item History"};
    static final int DB_VER = 3;
    boolean sdkWarn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        label = (TextView) findViewById(R.id.lblUnclaimed);
        stdId = (TextView) findViewById(R.id.tvStdId);
        mainMenu = (ListView) findViewById(R.id.lvMainMenu);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        mainMenu.setAdapter(adapter);
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) mainMenu.getItemAtPosition(position);
                Intent intent = null;
                switch (selected) {
                    case "Reserve your food":
                        intent = new Intent(MainScreen.this, SelectCanteen.class);

                        break;
                    case "View Cart":
                        intent = new Intent(MainScreen.this, CartActivity.class);

                        break;
                    case "View Reserved Item History":
                        intent = new Intent(MainScreen.this, ReservedItems.class);

                        break;
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
                            .setMessage(String.format("Location: " + foodSel.get_location() + "\nQuantity: " + foodSel.get_qty() + "\nPrice: $%.2f \n" +
                                    "Reserved List: " + foodSel.getCartID(),foodSel.get_price()))
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
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();   //Call superclass method first

        initDatabase();
        checkUnclaimed();

        //Check if student ID is inserted
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String stdID = pref.getString("studentID", null);
        if (stdID == null){
            //No Student ID detected
            stdId.setText("Student/Staff ID: None registered");
            callStudent();
        } else {
            stdId.setText("Student/Staff ID: " + stdID);
        }
    }

    private void callStudent(){
        final EditText inputSID = new EditText(this);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        inputSID.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        inputSID.setHint("Enter Student ID");
        new AlertDialog.Builder(this).setTitle("Enter Student ID").setView(inputSID)
                .setMessage("Enter your Ngee Ann Polytechnic Student ID Number (E.g. S10123123A). This would be used for identification purposes.")
                .setCancelable(false).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (inputSID.length() != 0) {
                    Log.d("REGEX CHECK", IsStudentID(inputSID.getText().toString()) + "");
                    if (!IsStudentID(inputSID.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Invalid Student ID. eg.:S10123123A", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        pref.edit().putString("studentID", inputSID.getText().toString().toUpperCase() + "").apply();
                        stdId.setText("Student/Staff ID: " + pref.getString("studentID", "None Registered"));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Student ID cannot be blank", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).setNeutralButton("I am a NP Staff", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callStaff();
            }
        }).show();
    }

    private void callStaff(){
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final EditText inputSIDs = new EditText(MainScreen.this);
        inputSIDs.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        inputSIDs.setHint("Enter Staff Email");
        new AlertDialog.Builder(MainScreen.this).setTitle("Enter NP Staff Email").setView(inputSIDs)
                .setMessage("Enter your Staff Ngee Ann Polytechnic Email Address (E.g. staffemail@np.edu.sg). This would be used for identification purposes.")
                .setCancelable(false).setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (inputSIDs.length() != 0) {
                    Log.d("REGEX CHECK", IsStaffID(inputSIDs.getText().toString()) + "");
                    if (!IsStaffID(inputSIDs.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Invalid Staff ID. eg.:staffemail@np.edu.sg", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        pref.edit().putString("studentID", inputSIDs.getText().toString().toLowerCase() + "").apply();
                        stdId.setText("Student/Staff ID: " + pref.getString("studentID", "None Registered"));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Staff Email Address cannot be blank", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).setNeutralButton("I am a NP Student", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callStudent();
            }
        }).show();
    }

    private static boolean IsStudentID(String s) {
        String pattern = "[S,s][0-9]{8}[A-Z,a-z]";
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static boolean IsStaffID(String s) {
        String pattern = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@np\\.edu\\.sg";
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private boolean sdkCheck(){
        if (DatabaseHandler.checkIfSDK(getApplicationContext())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Detected SDK Emulator Note")
                    .setMessage("There might be an error if you use an SDK emulator to access this app if you do not have SD Card enabled.\n"
                            + "If it crashes after you click OK, please enable SD Card in your emulator settings, or switch to an actual Android Device")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //MainScreen.this.finish();
                            startInit();
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainScreen.this.finish();
                        }
                    });
            if (!sdkWarn) {
                builder.show();
                sdkWarn = true;
            }
            return true;
        }
        return false;
    }

    public void initDatabase(){
        //Toast.makeText(getApplicationContext(), Build.PRODUCT.toString(), Toast.LENGTH_SHORT).show();
         //else {
        if (!sdkCheck()) {
            startInit();
            //}
        }

    }

    private void startInit(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPref.getInt("dbValue", 0) != DB_VER) {
            new DatabaseHandler(this.getApplicationContext());
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
