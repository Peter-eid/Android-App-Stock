package com.grizly.stockapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.grizly.stockapp.Config;
import com.grizly.stockapp.Methods;
import com.grizly.stockapp.R;

public class LoginActivity extends AppCompatActivity {

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
                if (getUsername_et().getText().toString().equals(Methods.getPref(LoginActivity.this, Config.PREF_KEY_USERNAME))
                        && getPassword_et().getText().toString().equals(Methods.getPref(LoginActivity.this, Config.PREF_KEY_PASSWORD))) {

                    Intent intent = new Intent(LoginActivity.this, StockActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                }
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

}
