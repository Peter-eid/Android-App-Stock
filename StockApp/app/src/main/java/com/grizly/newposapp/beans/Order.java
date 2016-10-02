package com.grizly.newposapp.beans;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grizly.newposapp.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Order {
    String type;
    String date;

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public Order(String type, String date) {
        this.type = type;
        this.date = date;
    }

    static public ArrayList getPrefArraylist(String Key, Context context) {
        ArrayList<Order> list = null;
        try {
            SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
            Gson gson = new Gson();
            String json = settings.getString(Key, null);
            Type type = new TypeToken<ArrayList<Order>>() {
            }.getType();
            if (json != null)
                list = gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
