package com.arnab.android.myyahooweather.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.arnab.android.myyahooweather.model.Weather;

/**
 * Created by arnab on 11/4/17.
 */

public class WeatherSyncIntentService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public WeatherSyncIntentService() {
        super("WeatherSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherSyncTask.syncWeather(this);
    }
}
