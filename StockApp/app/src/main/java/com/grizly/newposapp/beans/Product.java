package com.grizly.newposapp.beans;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grizly.newposapp.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Product {

    String inStock;
    String barcode;
    String imageUrl;
    String product;
    String pid;

    public Product(String inStock, String imageUrl, String barcode, String product, String pid) {
        this.inStock = inStock;
        this.imageUrl = imageUrl;
        this.barcode = barcode;
        this.product = product;
        this.pid = pid;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProduct() {
        return product;
    }

    public String getInStock() {
        return inStock;

    }
    public String getPid() {
        return pid;

    }

    static public ArrayList getPrefArraylist(String Key, Context context) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
            Gson gson = new Gson();
            String json = settings.getString(Key, null);
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();
            if (json != null)
                list = gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
