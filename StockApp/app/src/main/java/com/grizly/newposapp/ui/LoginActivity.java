package com.grizly.newposapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.grizly.newposapp.Config;
import com.grizly.newposapp.Methods;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.LoginRequest;
import com.grizly.newposapp.beans.Order;
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.beans.SpinnerItem;
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    MyApiEndpointInterface apiCall;
    Call<String> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Login");
        setSupportActionBar(toolbar);

        getLogin_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUsername_et().getText().toString().trim().length() < 1
                        || getPassword_et().getText().toString().trim().length() < 1) {
                    Toast.makeText(LoginActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                } else {
                    login(getUsername_et().getText().toString(), getPassword_et().getText().toString());
                }
            }
        });

        getGoToRegister_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public AppCompatEditText getUsername_et() {
        return (AppCompatEditText) findViewById(R.id.username);
    }

    public AppCompatEditText getPassword_et() {
        return (AppCompatEditText) findViewById(R.id.password);
    }

    public AppCompatButton getLogin_btn() {
        return (AppCompatButton) findViewById(R.id.login);
    }

    public AppCompatButton getGoToRegister_btn() {
        return (AppCompatButton) findViewById(R.id.goToRegister);
    }

    public void login(final String username, String password) {

        apiCall = Factory.create();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);

        final Gson gson1 = new Gson();
        String post = gson1.toJson(loginRequest).toString();
        try {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            call = apiCall.login(post);//testPost
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    if (response.code() == 200) {
                        String resp = response.body();
                        try {
                            JSONObject json = new JSONObject(resp);
                            Boolean json_result = json.getBoolean("result");
                            if (json_result) {
                                JSONObject json_data = json.getJSONObject("data");
                                int json_privilege = json_data.optInt("privilege");
                                String json_uid = json_data.optString("uid");
                                String priv = Integer.toString(json_privilege);
                                JSONArray json_products = json_data.getJSONArray("products");
                                JSONArray json_operations = json_data.getJSONArray("operations");
                                Methods.clearPref(LoginActivity.this);
                                Methods.savePre(LoginActivity.this, priv, Config.PREF_KEY_REGISTERED);
                                ArrayList<SpinnerItem> spinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_USERS_SPINNER, LoginActivity.this);
                                if (spinnerItemList == null || spinnerItemList.size() == 1) {
                                    spinnerItemList = new ArrayList<>();
                                }
                                JSONArray json_users = new JSONArray();
                                if (json_privilege == 0) {
                                   json_users = json_data.getJSONArray("users");
                                    for (int i = 0; i < json_users.length(); i++) {
                                        JSONObject user = json_users.getJSONObject(i);
                                        String uid = user.optString("_id");
                                        String userName = user.optString("username");
                                        SpinnerItem un = new SpinnerItem(uid, userName);
                                        spinnerItemList.add(un);
                                        Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_USERS_SPINNER, LoginActivity.this);
                                    }
                                } else {
                                    SpinnerItem un = new SpinnerItem(json_uid, username);
                                    spinnerItemList.add(un);
                                    Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_USERS_SPINNER, LoginActivity.this);
                                }
                                ArrayList<Product> productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, LoginActivity.this);
                                ArrayList<SpinnerItem> prodSpinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_SPINNER, LoginActivity.this);
                                if (prodSpinnerItemList == null || prodSpinnerItemList.size() == 1) {
                                    prodSpinnerItemList = new ArrayList<>();
                                }
                                if (productList == null || productList.size() == 1) {
                                    productList = new ArrayList<>();
                                }
                                for (int x = 0; x < json_products.length(); x++) {
                                    JSONObject product = json_products.getJSONObject(x);
                                    String pid = product.optString("_id");
                                    int stock = product.optInt("stock");
                                    String sStock = Integer.toString(stock);
                                    String barcode = product.optString("barcode");
                                    String name = product.optString("name");
                                    String productimage = product.optString("productimage");
                                    prodSpinnerItemList.add(new SpinnerItem(pid, name));
                                    productList.add(new Product(sStock, productimage, barcode, name, pid));
                                    Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, LoginActivity.this);
                                    Methods.savePrefObject(prodSpinnerItemList, Config.PREF_KEY_LIST_SPINNER, LoginActivity.this);
                                }
                                ArrayList<Order> orderList = Order.getPrefArraylist(Config.PREF_KEY_LIST_ORDERS, LoginActivity.this);
                                if (orderList == null || orderList.size() == 1) {
                                    orderList = new ArrayList<>();
                                }
                                for (int y = 0; y < json_operations.length(); y++) {
                                    JSONObject operation = json_operations.getJSONObject(y);
                                    JSONObject product = operation.getJSONObject("productDetails");
                                    String json_oid = operation.optString("_id");
                                    String uid = operation.optString("uid");
                                    String pn= product.optString("name");
                                    String type = operation.optString("type");
                                    String tn = "Take";
                                    if(type.equals("1")){
                                        tn = "Borrow";
                                    }
                                    String date = operation.optString("date");
                                    String un = username;
                                    if(json_privilege == 1){
                                        orderList.add(new Order(json_oid,un, pn, tn, date));
                                    }
                                    else {
                                        for (int i = 0; i < json_users.length(); i++) {
                                            JSONObject user = json_users.getJSONObject(i);
                                            String userName = user.optString("username");
                                            String uuid = user.optString("_id");
                                            if(uuid.equals(uid)){
                                                un = userName;
                                                break;
                                            }
                                        }
                                        orderList.add(new Order(json_oid,un, pn, tn, date));
                                    }
                                    Methods.savePrefObject(orderList, Config.PREF_KEY_LIST_ORDERS, LoginActivity.this);
                                }

                                Intent intent = new Intent(LoginActivity.this, StockActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                JSONObject jsonCodeMsg = json_details.getJSONObject(0);
                                String msg = jsonCodeMsg.optString("message");
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Error While Reaching The Server", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
