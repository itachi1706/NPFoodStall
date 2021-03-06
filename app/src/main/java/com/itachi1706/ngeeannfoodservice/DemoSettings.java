package com.itachi1706.ngeeannfoodservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class DemoSettings extends ActionBarActivity {


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GeneralPreferenceFragment())
                .commit();
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        @SuppressWarnings("deprecation")
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_demo);
            getPreferenceManager().setSharedPreferencesMode(MODE_MULTI_PROCESS);
            Preference link = findPreference("lnk_update");
            link.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getResources().getString(R.string.link_updates)));
                    startActivity(i);
                    return true;
                }
            });

            //DEMO
            Preference demo1 = findPreference("demoInstall");
            demo1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem1 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_install)));
                    startActivity(dem1);
                    return true;
                }
            });

            Preference demo2 = findPreference("demoUpgrade");
            demo2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_update)));
                    startActivity(dem2);
                    return true;
                }
            });

            Preference demo3 = findPreference("demoAdd");
            demo3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem3 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_order)));
                    startActivity(dem3);
                    return true;
                }
            });

            Preference demo4 = findPreference("demoCheckout");
            demo4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem4 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_modify)));
                    startActivity(dem4);
                    return true;
                }
            });

            Preference demo5 = findPreference("demoCollection");
            demo5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem5 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_confirm)));
                    startActivity(dem5);
                    return true;
                }
            });

            Preference demo6 = findPreference("demiDirections");
            demo6.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem6 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_location)));
                    startActivity(dem6);
                    return true;
                }
            });

            Preference demo7 = findPreference("demoConfirm");
            demo7.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent dem7 = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.yt_notify)));
                    startActivity(dem7);
                    return true;
                }
            });
        }
    }

}
