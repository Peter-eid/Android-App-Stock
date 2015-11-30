package com.grizly.stockapp.beans;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grizly.stockapp.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class User {

    String userName;
    String password;
    String permission;

    public User(String userName, String password, String permission) {
        this.userName = userName;
        this.password = password;
        this.permission = permission;
    }

    static public ArrayList getPrefArraylist(String Key, Context context) {
        ArrayList<User> list = null;
        try {
            SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
            Gson gson = new Gson();
            String json = settings.getString(Key, null);
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            if (json != null)
                list = gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
