package com.itachi1706.ngeeannfoodservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class AppSettings extends ActionBarActivity {


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
            addPreferencesFromResource(R.xml.pref_general);
            getPreferenceManager().setSharedPreferencesMode(MODE_MULTI_PROCESS);
            Preference generalPref = findPreference("dbValue");
            SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(getActivity());
            generalPref.setSummary(pre.getInt("dbValue", 0) + "");
            generalPref = findPreference("reloaddb");
            generalPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity().getApplicationContext(), "Force Refreshing Database", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                    sharedPref.edit().putInt("dbValue", 0).apply();
                    getActivity().finish();
                    Intent i = new Intent(getActivity(), MainScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    return true;
                }
            });

            Preference loadPref = findPreference("reloadCdb");
            loadPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ShoppingCartDBHandler db = new ShoppingCartDBHandler(getActivity().getApplicationContext());
                    db.dropAndRebuildDB();
                    Toast.makeText(getActivity().getApplicationContext(), "Reset Database", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            Preference devInfoPref = findPreference("vDevInfo");
            devInfoPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), DebugSettings.class);
                    startActivity(intent);
                    return true;
                }
            });

            //Debug Info Get
            String version = "NULL", packName = "NULL";
            try {
                PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                version = pInfo.versionName;
                packName = pInfo.packageName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Preference verPref = findPreference("view_app_version");
            verPref.setSummary(version);
            Preference pNamePref = findPreference("view_app_name");
            pNamePref.setSummary(packName);

            final Preference pStdIDPref = findPreference("studentID");
            final SharedPreferences pe = PreferenceManager.getDefaultSharedPreferences(getActivity());
            pStdIDPref.setSummary(pe.getString("studentID", "No ID Registered"));

            Preference pLogPref = findPreference("studentIDLog");
            pLogPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    p.edit().putString("studentID", null).apply();
                    Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                    pStdIDPref.setSummary(pe.getString("studentID", "No ID Registered"));
                    return true;
                }
            });

            Preference prefss = findPreference("sendToOwner");
            prefss.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), NotifyVendorActivity.class));
                    return true;
                }
            });

            Preference prefsss = findPreference("sendToStudent");
            prefsss.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), NotifyUserActivity.class));
                    return true;
                }
            });

            Preference prefs = findPreference("view_sdk_version");
            prefs.setSummary(android.os.Build.VERSION.RELEASE);
        }
    }

}
