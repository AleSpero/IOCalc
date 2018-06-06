package com.sperotti.alessandro.iocalc.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.sperotti.alessandro.iocalc.R;

/**
 * Created by Alessandro on 17/11/2016.
 */

public class SettingsFragment extends PreferenceFragment {

    private ListPreference binencoding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }

   /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binencoding = (ListPreference)  getPreferenceManager().findPreference(Constants.BINARY_CODEC);
        binencoding.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences preferences = pre
            }
        }

        return inflater.inflate(R.layout., container, false);
    }*/

}
