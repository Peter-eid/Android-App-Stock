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
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
                login(getUsername_et().getText().toString(), getPassword_et().getText().toString());
//                if (getUsername_et().getText().toString().equals(Methods.getPref(LoginActivity.this, Config.PREF_KEY_USERNAME))
//                        && getPassword_et().getText().toString().equals(Methods.getPref(LoginActivity.this, Config.PREF_KEY_PASSWORD))) {
//
//                    Intent intent = new Intent(LoginActivity.this, StockActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
//                }
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

    public void login(String username, String password) {

        apiCall = Factory.create();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);

        final Gson gson1 = new Gson();
        String post = gson1.toJson(loginRequest).toString();
        try {
            call = apiCall.login(post);//testPost
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        String resp = response.body();
                        try {

                            JSONObject json = new JSONObject(resp);
                            Boolean json_result= json.getBoolean("result");
                            if(json_result){
                                Intent intent = new Intent(LoginActivity.this, StockActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                Toast.makeText(LoginActivity.this, json_details.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
