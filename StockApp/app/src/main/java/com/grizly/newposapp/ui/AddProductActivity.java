package com.grizly.newposapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.grizly.newposapp.Config;
import com.grizly.newposapp.Methods;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.beans.SpinnerItem;

import java.util.ArrayList;

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
                if (getProduct_et().getText().toString().trim().length() < 1 ||
                        getQuantity_et().getText().toString().trim().length() < 1) {
                    Toast.makeText(AddProductActivity.this, "Missing Fields", Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<Product> productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, AddProductActivity.this);
                    ArrayList<SpinnerItem> spinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_SPINNER, AddProductActivity.this);

                    String hh = getProduct_et().getText().toString();
                    spinnerItemList.add(new SpinnerItem(
                            getProduct_et().getText().toString(),
                            getProduct_et().getText().toString()));

                    productList.add(new Product(getQuantity_et().getText().toString(),
                            getImage_et().getText().toString(),
                            getProduct_et().getText().toString(),
                            getProduct_et().getText().toString()));
                    Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, AddProductActivity.this);
                    Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_SPINNER, AddProductActivity.this);

                    finish();
                }
            }
        });
    }

    public AppCompatEditText getProduct_et() {
        return (AppCompatEditText) findViewById(R.id.product_id);
    }

    public AppCompatEditText getQuantity_et() {
        return (AppCompatEditText) findViewById(R.id.quantity_et);
    }

    public AppCompatEditText getImage_et() {
        return (AppCompatEditText) findViewById(R.id.image_et);
    }

    public AppCompatButton getCancel_btn() {
        return (AppCompatButton) findViewById(R.id.cancel);
    }

    public AppCompatButton getCreate_btn() {
        return (AppCompatButton) findViewById(R.id.create);
    }
}
