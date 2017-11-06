package com.arnab.android.myyahooweather.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arnab.android.myyahooweather.R;

/**
 * Created by arnab on 11/5/17.
 */

public class WeatherPreferences {

    public static boolean isMetric(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForUnits = context.getString(R.string.pref_units_key);
        String defaultUnits = context.getString(R.string.pref_units_imperial);
        String preferredUnits = sp.getString(keyForUnits, defaultUnits);
        String imperial = context.getString(R.string.pref_units_imperial);

        boolean userPrefersImperial = false;
        if (imperial.equals(preferredUnits)) {
            userPrefersImperial = true;
        }

        return userPrefersImperial;
    }
}
