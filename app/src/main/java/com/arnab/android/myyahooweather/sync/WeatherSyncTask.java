package com.arnab.android.myyahooweather.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateUtils;

import com.arnab.android.myyahooweather.Data.JsonParser;
import com.arnab.android.myyahooweather.Data.WeatherContract;
import com.arnab.android.myyahooweather.Data.WeatherHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arnab.android.myyahooweather.Data.WeatherPreferences;
import com.arnab.android.myyahooweather.Util.NotificationUtils;
import com.arnab.android.myyahooweather.model.DayForecast;

/**
 * Created by arnab on 11/4/17.
 */

public class WeatherSyncTask {
    synchronized public static void syncWeather(Context context){
        try{
            String data =  new WeatherHttpClient().getWeatherData(WeatherPreferences.getPreferredWeatherLocation(context));
            JSONArray weatherJA = JsonParser.getWeather(data);
            DayForecast[] dayForecasts = new DayForecast[weatherJA.length()];
            for(int count = 0; count < weatherJA.length(); count++){
                DayForecast dayForecast = new DayForecast();
                JSONObject weatherJO = null;
                try {

                    weatherJO = weatherJA.getJSONObject(count);
                    dayForecast.setCode(weatherJO.getString("code"));
                    dayForecast.setDate(weatherJO.getString("date"));
                    dayForecast.setHigh(weatherJO.getString("high"));
                    dayForecast.setLow(weatherJO.getString("low"));
                    dayForecast.setText(weatherJO.getString("text"));
                    dayForecast.setDay(weatherJO.getString("day"));
                    dayForecasts[count] = dayForecast;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            ContentValues[] contentValuesArr = getContentValues(dayForecasts);
            ContentResolver weatherContentResolver = context.getContentResolver();

//               If we have valid results, delete the old data and insert the new
                /* Delete old weather data because we don't need to keep multiple days' data */
            weatherContentResolver.delete(
                    WeatherContract.WeatherEntry.CONTENT_URI,
                    null,
                    null);

                /* Insert our new weather data into Sunshine's ContentProvider */
            weatherContentResolver.bulkInsert(
                    WeatherContract.WeatherEntry.CONTENT_URI,
                    contentValuesArr);
            boolean notificationsEnabled = WeatherPreferences.areNotificationsEnabled(context);

                /*
                 * If the last notification was shown was more than 1 day ago, we want to send
                 * another notification to the user that the weather has been updated. Remember,
                 * it's important that you shouldn't spam your users with notifications.
                 */
            long timeSinceLastNotification = WeatherPreferences
                    .getEllapsedTimeSinceLastNotification(context);

            boolean oneDayPassedSinceLastNotification = false;

//              COMPLETED (14) Check if a day has passed since the last notification
            if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS) {
                oneDayPassedSinceLastNotification = true;
            }
                /*
                 * We only want to show the notification if the user wants them shown and we
                    * haven't shown a notification in the past day.
                    */
//              COMPLETED (15) If more than a day have passed and notifications are enabled, notify the user
            if (notificationsEnabled && oneDayPassedSinceLastNotification) {
                NotificationUtils.notifyUserOfNewWeather(context);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static ContentValues[] getContentValues(DayForecast [] dayForecasts){
        ContentValues [] contentValuesArr = new ContentValues[dayForecasts.length];
        for(int count = 0; count < dayForecasts.length; count++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(WeatherContract.WeatherEntry.CODE,dayForecasts[count].getCode() );
            contentValues.put(WeatherContract.WeatherEntry.TEXT,dayForecasts[count].getText() );
            contentValues.put(WeatherContract.WeatherEntry.DATE,dayForecasts[count].getDate() );
            contentValues.put(WeatherContract.WeatherEntry.DAY,dayForecasts[count].getDay() );
            contentValues.put(WeatherContract.WeatherEntry.HIGH,dayForecasts[count].getHigh() );
            contentValues.put(WeatherContract.WeatherEntry.LOW,dayForecasts[count].getLow() );
            contentValuesArr[count] = contentValues;
        }
        return contentValuesArr;
    }
}
