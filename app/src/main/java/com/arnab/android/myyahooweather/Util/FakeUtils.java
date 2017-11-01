package com.arnab.android.myyahooweather.Util;

import android.content.ContentValues;
import android.content.Context;

import com.arnab.android.myyahooweather.Data.WeatherContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Created by arnab on 11/1/17.
 */

public class FakeUtils {


    private static int [] weatherIDs = {200,300,500,711,900,962};

    /**
     * Creates a single ContentValues object with random weather data for the provided date
     * @param date a normalized date
     * @return ContentValues object filled with random weather data
     */
    private static ContentValues createTestWeatherContentValues(long date) {
        ContentValues testWeatherValues = new ContentValues();
        testWeatherValues.put(WeatherContract.WeatherEntry.DATE, String.valueOf(date));
        int maxTemp = (int)(Math.random()*100);
        testWeatherValues.put(WeatherContract.WeatherEntry.HIGH, maxTemp);
        testWeatherValues.put(WeatherContract.WeatherEntry.LOW, maxTemp - (int) (Math.random()*10));
        testWeatherValues.put(WeatherContract.WeatherEntry.CODE, weatherIDs[(int)(Math.random()*10)%5]);
        return testWeatherValues;
    }
    public static long normalizeDate(long date) {
        long daysSinceEpoch = elapsedDaysSinceEpoch(date);
        long millisFromEpochToTodayAtMidnightUtc = daysSinceEpoch * DAY_IN_MILLIS;
        return millisFromEpochToTodayAtMidnightUtc;
    }
    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }
    /**
     * Creates random weather data for 7 days starting today
     * @param context
     */
    public static void insertFakeData(Context context) {
        //Get today's normalized date
        long today = FakeUtils.normalizeDate(System.currentTimeMillis());
        List<ContentValues> fakeValues = new ArrayList<ContentValues>();
        //loop over 7 days starting today onwards
        for(int i=0; i<7; i++) {
            fakeValues.add(FakeUtils.createTestWeatherContentValues(today + TimeUnit.DAYS.toMillis(i)));
        }
        // Bulk Insert our new weather data into Sunshine's Database
        context.getContentResolver().bulkInsert(
                WeatherContract.WeatherEntry.CONTENT_URI,
                fakeValues.toArray(new ContentValues[7]));
    }
}
