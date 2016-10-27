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
        Methods.clearPref(this);
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
                                Methods.savePre(LoginActivity.this, priv, Config.PREF_KEY_REGISTERED);
                                //todo add all users return from login response
                                ArrayList<SpinnerItem> spinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_USERS_SPINNER, LoginActivity.this);
                                if(spinnerItemList == null){
                                    spinnerItemList = new ArrayList<>();
                                }
                                SpinnerItem userName = new SpinnerItem(json_uid, username);
                                spinnerItemList.add(userName);
                                Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_USERS_SPINNER, LoginActivity.this);
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
