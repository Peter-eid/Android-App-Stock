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
import com.grizly.newposapp.beans.RegisterRequest;
import com.grizly.newposapp.beans.SpinnerItem;
import com.grizly.newposapp.beans.UserRequest;
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    MyApiEndpointInterface apiCall;
    Call<String> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Register");
        setSupportActionBar(toolbar);

        getRegister_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUsername_et().getText().toString().trim().length() < 1
                        || getPassword_et().getText().toString().trim().length() < 1
                        || getFirstName_et().getText().toString().trim().length() < 1
                        || getLastName_et().getText().toString().trim().length() < 1
                        || getEmail_et().getText().toString().trim().length() < 1) {
                    Toast.makeText(RegisterActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                } else {
                    Register(getUsername_et().getText().toString(), getPassword_et().getText().toString(), getFirstName_et().getText().toString(), getLastName_et().getText().toString(), getEmail_et().getText().toString());
                }
            }
        });

        getSignin_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void Register(final String username, String password, final String firstName, final String lastName, String email) {

        apiCall = Factory.create();

        UserRequest userRequest = new UserRequest(username, password, firstName, lastName, email);
        RegisterRequest registerRequest = new RegisterRequest();
        final Gson gson1 = new Gson();
        String post = gson1.toJson(registerRequest.withUser(userRequest));
        try {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            call = apiCall.addUser(post);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    if (response.code() == 200) {
                        String resp = response.body();
                        try {

                            JSONObject json = new JSONObject(resp);
                            Boolean json_result = json.getBoolean("result");
                            JSONObject json_data = json.getJSONObject("data");
                            String json_uid = json_data.optString("_id");
                            if (json_result) {
                                Methods.savePre(RegisterActivity.this, getUsername_et().getText().toString().trim(), Config.PREF_KEY_USERNAME);
                                Methods.savePre(RegisterActivity.this, getPassword_et().getText().toString().trim(), Config.PREF_KEY_PASSWORD);
                                Methods.savePre(RegisterActivity.this, getFirstName_et().getText().toString().trim(), Config.PREF_KEY_FIRSTNAME);
                                Methods.savePre(RegisterActivity.this, getLastName_et().getText().toString().trim(), Config.PREF_KEY_LASTNAME);
                                Methods.savePre(RegisterActivity.this, getEmail_et().getText().toString().trim(), Config.PREF_KEY_EMAIL);
                                Methods.savePre(RegisterActivity.this, "1", Config.PREF_KEY_REGISTERED);
                                String name = firstName + " " + lastName;
                                ArrayList<SpinnerItem> spinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_USERS_SPINNER, RegisterActivity.this);
                                if(spinnerItemList == null){
                                    spinnerItemList = new ArrayList<>();
                                }
                              SpinnerItem userName = new SpinnerItem(json_uid, username);
                                spinnerItemList.add(userName);
                                Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_USERS_SPINNER, RegisterActivity.this);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                JSONObject jsonCodeMsg = json_details.getJSONObject(0);
                                String msg = jsonCodeMsg.optString("message");
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Error While Reaching The Server", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AppCompatEditText getUsername_et() {
        return (AppCompatEditText) findViewById(R.id.username);
    }

    public AppCompatEditText getPassword_et() {
        return (AppCompatEditText) findViewById(R.id.password);
    }

    public AppCompatEditText getFirstName_et() {
        return (AppCompatEditText) findViewById(R.id.firstName);
    }

    public AppCompatEditText getLastName_et() {
        return (AppCompatEditText) findViewById(R.id.lastName);
    }

    public AppCompatEditText getEmail_et() {
        return (AppCompatEditText) findViewById(R.id.email);
    }

    public AppCompatButton getRegister_btn() {
        return (AppCompatButton) findViewById(R.id.register);
    }

    public AppCompatButton getSignin_btn() {
        return (AppCompatButton) findViewById(R.id.signin);
    }

}
