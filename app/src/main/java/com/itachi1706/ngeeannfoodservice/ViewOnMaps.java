package com.itachi1706.ngeeannfoodservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewOnMaps extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener{

    static final MarkerOptions makanPlace = new MarkerOptions()
            .position(new LatLng(1.332238, 103.774526))
            .title("Makan Place").flat(true)
            .snippet("Blk 51, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions munch =  new MarkerOptions()
            .position(new LatLng(1.331901, 103.776829))
            .title("Munch").flat(true)
            .snippet("Blk 73, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions canteen4 =  new MarkerOptions()
            .position(new LatLng(1.332796, 103.771441))
            .title("Canteen 4").flat(true)
            .snippet("Blk 43, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions poolside =  new MarkerOptions()
            .position(new LatLng(1.335158, 103.776358))
            .title("Poolside").flat(true)
            .snippet("Sports Complex (Blk 16), Ngee Ann Polytechnic, Singapore");

    //Special
    static final MarkerOptions kfc = new MarkerOptions()
            .position(new LatLng(1.339022, 103.778815))
            .title("KFC").flat(true)
            .snippet("Bukit Timah Plaza, 1 Jalan Anak Bukit, Singapore");
    static final MarkerOptions pizzaHut = new MarkerOptions()
            .position(new LatLng(1.339022, 103.778815))
            .title("Pizza Hut").flat(true)
            .snippet("Bukit Timah Plaza, 1 Jalan Anak Bukit, Singapore");
    static final MarkerOptions mcdonald = new MarkerOptions()
            .position(new LatLng(1.341804, 103.776423))
            .title("McDonalds").flat(true)
            .snippet("Beauty World Plaza, 140 Upper Bukit Timah Rd, Singapore");
    static final MarkerOptions starbucks = new MarkerOptions()
            .position(new LatLng(1.309649, 103.779213))
            .title("Starbucks Coffee").flat(true)
            .snippet("Food Court 5, Singapore Polytechnic, 500 Dover Road, Singapore");
    static final MarkerOptions coffeebean = new MarkerOptions()
            .position(new LatLng(1.332811, 103.774624))
            .title("Coffee Bean").flat(true)
            .snippet("Blk 53, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions sushi = new MarkerOptions()
            .position(new LatLng(1.331881, 103.775616))
            .title("Sakae Sushi").flat(true)
            .snippet("OurSpace@72, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions subway = new MarkerOptions()
            .position(new LatLng(1.332238, 103.774526))
            .title("Subway").flat(true)
            .snippet("Makan Place, Blk 51, Ngee Ann Polytechnic, Singapore");
    static final MarkerOptions mosBurger =  new MarkerOptions()
            .position(new LatLng(1.335158, 103.776358))
            .title("MOS Burger").flat(true)
            .snippet("Poolside, Sports Complex (Blk 16), Ngee Ann Polytechnic, Singapore");
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("location").isEmpty()) {
                Toast.makeText(getApplicationContext(),"Error loading maps", Toast.LENGTH_SHORT).show();
            } else {
                setContentView(R.layout.activity_view_on_maps);
                location = bundle.getString("location");
                setUpMapIfNeeded();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("location").isEmpty()) {
                Toast.makeText(getApplicationContext(),"Error loading maps", Toast.LENGTH_SHORT).show();
            } else {
                location = bundle.getString("location");
                setUpMapIfNeeded();
            }
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setBuildingsEnabled(true);
                mMap.setIndoorEnabled(true);
                mMap.setTrafficEnabled(true);
                //mMap.setInfoWindowAdapter(new ViewOnMaps());
                mMap.setOnInfoWindowClickListener(this);
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        Toast t = Toast.makeText(getApplicationContext(), "Click for directions to " + location, Toast.LENGTH_SHORT);
        if (location.equals("Makan Place")) {
            t.show();
            mMap.addMarker(makanPlace).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(makanPlace.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Poolside")) {
            t.show();
            mMap.addMarker(poolside).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(poolside.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Canteen 4")) {
            t.show();
            mMap.addMarker(canteen4).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(canteen4.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Munch")) {
            t.show();
            mMap.addMarker(munch).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(munch.getPosition(), 20);
            mMap.animateCamera(update);
        //Special

        } else if (location.equals("Subway")) {
            t.show();
            mMap.addMarker(subway).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(subway.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Coffee Bean")) {
            t.show();
            mMap.addMarker(coffeebean).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coffeebean.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Sakae Sushi")) {
            t.show();
            mMap.addMarker(sushi).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sushi.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("McDonalds")) {
            t.show();
            mMap.addMarker(mcdonald).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mcdonald.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("KFC")) {
            t.show();
            mMap.addMarker(kfc).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(kfc.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Pizza Hut")) {
            t.show();
            mMap.addMarker(pizzaHut).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pizzaHut.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("MOS Burger")) {
            t.show();
            mMap.addMarker(mosBurger).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mosBurger.getPosition(), 20);
            mMap.animateCamera(update);
        } else if (location.equals("Starbucks Coffee")) {
            t.show();
            mMap.addMarker(starbucks).showInfoWindow();
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(starbucks.getPosition(), 20);
            mMap.animateCamera(update);
        } else {
            Toast.makeText(getApplicationContext(), "Unknown Location: " + location, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker){
        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}
