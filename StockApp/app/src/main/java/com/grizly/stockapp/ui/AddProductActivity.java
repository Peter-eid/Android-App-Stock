package com.grizly.stockapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.grizly.stockapp.R;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("New Product");
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
                if(getProduct_et().getText().toString().trim().length() < 1 ||
                        getQuantity_et().getText().toString().trim().length() < 1){
                    Toast.makeText(AddProductActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                }else{

                }
            }
        });
    }

    public AppCompatEditText getProduct_et(){
        return (AppCompatEditText) findViewById(R.id.product_id);
    }

    public AppCompatEditText getQuantity_et(){
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
