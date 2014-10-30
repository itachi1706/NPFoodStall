package com.itachi1706.ngeeannfoodservice;

import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AppSettings extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

            Preference prefs = findPreference("view_sdk_version");
            prefs.setSummary(android.os.Build.VERSION.RELEASE);
            prefs =  findPreference("view_board_ver");
            prefs.setSummary(android.os.Build.BOARD);
            prefs = findPreference("view_bootloader_ver");
            prefs.setSummary(android.os.Build.BOOTLOADER);
            prefs = findPreference("view_brand_ver");
            prefs.setSummary(android.os.Build.BRAND);
            prefs = findPreference("view_cpu1_ver");
            prefs.setSummary(android.os.Build.CPU_ABI);
            prefs = findPreference("view_cpu2_ver");
            prefs.setSummary(android.os.Build.CPU_ABI2);
            prefs = findPreference("view_device_ver");
            prefs.setSummary(android.os.Build.DEVICE);
            prefs = findPreference("view_display_ver");
            prefs.setSummary(android.os.Build.DISPLAY);
            prefs = findPreference("view_fingerprint_ver");
            prefs.setSummary(android.os.Build.FINGERPRINT);
            prefs = findPreference("view_hardware_ver");
            prefs.setSummary(android.os.Build.HARDWARE);
            prefs = findPreference("view_host_ver");
            prefs.setSummary(android.os.Build.HOST);
            prefs = findPreference("view_id_ver");
            prefs.setSummary(android.os.Build.ID);
            prefs = findPreference("view_manufacturer_ver");
            prefs.setSummary(android.os.Build.MANUFACTURER);
            prefs = findPreference("view_model_ver");
            prefs.setSummary(android.os.Build.MODEL);
            prefs = findPreference("view_product_ver");
            prefs.setSummary(android.os.Build.PRODUCT);
            prefs = findPreference("view_radio_ver");
            if (android.os.Build.getRadioVersion() != null){
                prefs.setSummary(android.os.Build.getRadioVersion());
            }
            prefs = findPreference("view_serial_ver");
            prefs.setSummary(android.os.Build.SERIAL);
            prefs = findPreference("view_tags_ver");
            prefs.setSummary(android.os.Build.TAGS);
            prefs = findPreference("view_type_ver");
            prefs.setSummary(android.os.Build.TYPE);
            prefs = findPreference("view_user_ver");
            if (android.os.Build.USER != null){
                prefs.setSummary(android.os.Build.USER);
            }
        }
    }

}
