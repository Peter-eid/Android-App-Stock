package com.grizly.stockapp.beans;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grizly.stockapp.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Customer {

    String fullName;
    String imageUrl;
    String role;
    String phoneNumber;

    public Customer(String fullName, String imageUrl, String phoneNumber, String role) {
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }
    static public ArrayList getPrefArraylist(String Key, Context context) {
        ArrayList<Customer> list = null;
        try {
            SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
            Gson gson = new Gson();
            String json = settings.getString(Key, null);
            Type type = new TypeToken<ArrayList<Customer>>() {
            }.getType();
            if (json != null)
                list = gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
