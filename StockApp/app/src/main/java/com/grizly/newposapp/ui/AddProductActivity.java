package com.grizly.newposapp.ui;

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
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.beans.SpinnerItem;
import com.grizly.newposapp.beans.ProductRequest;
import com.grizly.newposapp.beans.addProduct;
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    MyApiEndpointInterface apiCall;
    Call<String> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("New Product");
        setSupportActionBar(toolbar);

        getCancel_btn().setOnClickListener(new View.OnClickListener() {
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
                    addProductRequest(getProduct_et().getText().toString().trim(),getQuantity_et().getText().toString().trim(), getImage_et().getText().toString().trim(), getBarcode_et().getText().toString().trim());
                }
            }
        });
    }
    public void addProductRequest(final String name, String stock, final String barcode, final String productimage) {

        apiCall = Factory.create();

        addProduct oneProduct = new addProduct(name, stock, barcode, productimage);
       ProductRequest productRequest = new ProductRequest();
        final Gson gson1 = new Gson();
        String post = gson1.toJson(productRequest.withProduct(oneProduct));
        try {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            call = apiCall.addProduct(post);
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
                                String json_pid = json_data.optString("_id");
                                ArrayList<Product> productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, AddProductActivity.this);
                                ArrayList<SpinnerItem> spinnerItemList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_SPINNER, AddProductActivity.this);

                                String hh = getProduct_et().getText().toString();
                                spinnerItemList.add(new SpinnerItem(
                                        json_pid,
                                        getProduct_et().getText().toString()));

                                productList.add(new Product(getQuantity_et().getText().toString(),
                                        getImage_et().getText().toString(),
                                        getBarcode_et().getText().toString(),
                                        getProduct_et().getText().toString(),
                                        json_pid));
                                Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, AddProductActivity.this);
                                Methods.savePrefObject(spinnerItemList, Config.PREF_KEY_LIST_SPINNER, AddProductActivity.this);
                                finish();
                            } else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                JSONObject jsonCodeMsg = json_details.getJSONObject(0);
                                String msg = jsonCodeMsg.optString("message");
                                Toast.makeText(AddProductActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(AddProductActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AddProductActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(AddProductActivity.this, "Error While Reaching The Server", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public AppCompatEditText getBarcode_et() {
        return (AppCompatEditText) findViewById(R.id.barcode_et);
    }

    public AppCompatButton getCancel_btn() {
        return (AppCompatButton) findViewById(R.id.cancel);
    }

    public AppCompatButton getCreate_btn() {
        return (AppCompatButton) findViewById(R.id.create);
    }
}
