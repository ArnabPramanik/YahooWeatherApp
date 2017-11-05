package com.arnab.android.myyahooweather.Util;

import java.util.concurrent.TimeUnit;



/**
 * Created by arnab on 11/5/17.
 */

public class WeatherDateUtils {
    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }
    public static long normalizeDate(long date) {
        long daysSinceEpoch = elapsedDaysSinceEpoch(date);
        long millisFromEpochToTodayAtMidnightUtc = daysSinceEpoch * DAY_IN_MILLIS;
        return millisFromEpochToTodayAtMidnightUtc;
    }
}
