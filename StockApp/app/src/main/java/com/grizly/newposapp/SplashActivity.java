package com.grizly.newposapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.grizly.newposapp.beans.Customer;
import com.grizly.newposapp.beans.Order;
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.beans.SpinnerItem;
import com.grizly.newposapp.beans.User;
import com.grizly.newposapp.ui.LoginActivity;
import com.grizly.newposapp.ui.RegisterActivity;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<SpinnerItem> spinnerList = new ArrayList<>();
    ArrayList<SpinnerItem> userSpinnerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (Methods.getPref(SplashActivity.this, Config.PREF_KEY_REGISTERED).equals("1") || Methods.getPref(SplashActivity.this, Config.PREF_KEY_REGISTERED).equals("0")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Methods.savePrefObject(customerList, Config.PREF_KEY_LIST_CUSTOMERS, SplashActivity.this);
                    Methods.savePrefObject(orderList, Config.PREF_KEY_LIST_ORDERS, SplashActivity.this);
                    Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, SplashActivity.this);
                    Methods.savePrefObject(userList, Config.PREF_KEY_LIST_USERS, SplashActivity.this);
                    Methods.savePrefObject(spinnerList, Config.PREF_KEY_LIST_SPINNER, SplashActivity.this);
                    Methods.savePrefObject(userSpinnerList, Config.PREF_KEY_LIST_USERS_SPINNER, SplashActivity.this);
                    Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

}
