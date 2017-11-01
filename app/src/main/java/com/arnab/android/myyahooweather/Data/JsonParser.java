package com.arnab.android.myyahooweather.Data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arnab.android.myyahooweather.Util.Utils;

/**
 * Created by arnab on 10/25/17.
 */

public class JsonParser {
    public static JSONArray getWeather(String data){

        try{
            JSONObject allData = new JSONObject(data);
            Log.v("All data", allData.toString());
            JSONObject trimJO = (((allData.getJSONObject("query")).getJSONObject("results")).getJSONObject("channel").getJSONObject("item"));

            JSONArray forecast = Utils.getJsonArray("forecast",trimJO);
            Log.v("Forecast", forecast.toString());
            //for(int count = 0; count < forecast.length(); count++){
              //  JSONObject dayWeather = forecast.getJSONObject(count);
                //String date = Utils.getString("date",dayWeather);
                //Log.v("THE DATE: ", date);
           // }
            return  forecast;
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return null;
    }

}
