package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.collection.ArrayMap;

public final class CountryCodeUtils {

    private static ArrayMap<String, String> countryCodeName = new ArrayMap<>();

    /**
     * get 国家名字
     *
     * @param context Context
     * @param code    Code
     * @return name
     */
    public static String getCountryName(Context context, String code) {
        if (countryCodeName.size() == 0) {
            String country = ReadJsonUtil.getJson(context, "country.json");
            try {
                JSONObject jsonObject = new JSONObject(country);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    countryCodeName.put(jsonArray.getJSONObject(i).getString("code"),
                            jsonArray.getJSONObject(i).getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return countryCodeName.containsKey(code) ? countryCodeName.get(code) : "";
    }
}
