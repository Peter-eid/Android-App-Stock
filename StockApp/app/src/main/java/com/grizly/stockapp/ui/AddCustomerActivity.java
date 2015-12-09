package com.grizly.stockapp.ui;

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
import com.grizly.stockapp.beans.Customer;

import java.util.ArrayList;

public class AddCustomerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("New Customer");
        setSupportActionBar(toolbar);

        getCreate_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getCreate_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFullName_et().getText().toString().trim().length() < 1 ||
                        getPhoneNumber_et().getText().toString().trim().length() < 1){
                    Toast.makeText(AddCustomerActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                }else{
                    ArrayList<Customer> customerList = Customer.getPrefArraylist(Config.PREF_KEY_LIST_CUSTOMERS, AddCustomerActivity.this);

                    customerList.add(new Customer(getFullName_et().getText().toString(),
                            getImage_et().getText().toString(),
                            getPhoneNumber_et().getText().toString(),
                            ""));
                    Methods.savePrefObject(customerList, Config.PREF_KEY_LIST_CUSTOMERS, AddCustomerActivity.this);
                    finish();
                }
            }
        });
    }

    public AppCompatEditText getFullName_et(){
        return (AppCompatEditText) findViewById(R.id.product_id);
    }

    public AppCompatEditText getPhoneNumber_et(){
        return (AppCompatEditText) findViewById(R.id.quantity_et);
    }
    public AppCompatEditText getImage_et(){
        return (AppCompatEditText) findViewById(R.id.image_et);
    }

    public AppCompatButton getCancel_btn(){
        return (AppCompatButton) findViewById(R.id.cancel);
    }

    public AppCompatButton getCreate_btn(){
        return (AppCompatButton) findViewById(R.id.create);
    }

}
