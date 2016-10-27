package com.grizly.newposapp.beans;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grizly.newposapp.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SpinnerItem {

    public String id, name;

    public SpinnerItem(String id, String name) {
        this.id = id;
        this.name = name;
    }
    static public ArrayList getPrefArraylist(String Key, Context context) {
        ArrayList<SpinnerItem> list = new ArrayList<SpinnerItem>();
        try {
            SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
            Gson gson = new Gson();
            String json = settings.getString(Key, null);
            Type type = new TypeToken<ArrayList<SpinnerItem>>() {
            }.getType();
            if (json != null)
                list = gson.fromJson(json, type);
        } catch (Exception ex) {
        }
        return list;
    }
}
