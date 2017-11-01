package com.arnab.android.myyahooweather.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnab on 10/25/17.
 */

public class Utils {

    public static JSONObject getObject(String tagname, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagname);
        return jObj;
    }

    public static String getString(String tagName, JSONObject jsonObject) throws JSONException{

        return jsonObject.getString(tagName);
    }

    public static JSONArray getJsonArray(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONArray(tagName);
    }

}
