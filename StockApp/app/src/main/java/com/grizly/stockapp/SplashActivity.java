package com.grizly.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.grizly.stockapp.beans.Customer;
import com.grizly.stockapp.beans.Order;
import com.grizly.stockapp.beans.Product;
import com.grizly.stockapp.beans.User;
import com.grizly.stockapp.ui.LoginActivity;
import com.grizly.stockapp.ui.RegisterActivity;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (Methods.getPref(SplashActivity.this, Config.PREF_KEY_REGISTERED).equals("1")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Methods.savePrefObject(customerList, Config.PREF_KEY_LIST_CUSTOMERS, SplashActivity.this);
                    Methods.savePrefObject(orderList, Config.PREF_KEY_LIST_ORDERS, SplashActivity.this);
                    Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, SplashActivity.this);
                    Methods.savePrefObject(userList, Config.PREF_KEY_LIST_USERS, SplashActivity.this);

                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

}
