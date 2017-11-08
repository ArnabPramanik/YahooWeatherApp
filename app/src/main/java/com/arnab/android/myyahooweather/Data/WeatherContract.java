package com.arnab.android.myyahooweather.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arnab on 10/30/17.
 */

public class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.arnab.android.myyahooweather";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";
    public static final class WeatherEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();
        public static final String TABLE_NAME = "weather";
        public static final String CODE = "code";
        public static final String DATE = "date";
        public static final String DAY = "day";
        public static final String HIGH = "high";
        public static final String LOW = "low";
        public static final String TEXT = "text";


        public static Uri buildWeatherUriWithDate(Long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(String.valueOf(date))
                    .build();
        }
        public static Uri buildWeatherUriWithDateStr(String date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(String.valueOf(date))
                    .build();
        }

    }
}
