package com.grizly.newposapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.grizly.newposapp.Config;
import com.grizly.newposapp.Methods;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.Order;
import com.grizly.newposapp.beans.Operation;
import com.grizly.newposapp.beans.OperationRequest;
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.beans.SpinnerItem;
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    MyApiEndpointInterface apiCall;
    Call<String> call;
    public ArrayList<Order> orderList = new ArrayList<Order>();
    public ArrayList<Order> useorderList = new ArrayList<Order>();

    ArrayList productlist = new ArrayList<SpinnerItem>();
    ArrayList typeList = new ArrayList<SpinnerItem>();
    ArrayList custList = new ArrayList<SpinnerItem>();
    SpinnerAdapter orderAdapter;
    SpinnerAdapter typeAdapter;
    SpinnerAdapter custAdapter;
    Spinner spinner;
    Spinner typeSpinner;
    Spinner custSpinner;

    AppCompatButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Create Order");
        setSupportActionBar(toolbar);


        btn = (AppCompatButton) findViewById(R.id.register);

        productlist = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_SPINNER, OrderActivity.this);
        custList = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_USERS_SPINNER, OrderActivity.this);
        typeList.add(new SpinnerItem("0", "Take"));
        typeList.add(new SpinnerItem("1", "Borrow"));

        orderAdapter = new SpinnerAdapter();
        typeAdapter = new SpinnerAdapter();
        custAdapter = new SpinnerAdapter();
        if(productlist != null && !productlist.isEmpty()){
            orderAdapter.addItems(productlist);
        }
        typeAdapter.addItems(typeList);
        if(custList != null && !custList.isEmpty()){
            custAdapter.addItems(custList);
        }
        spinner = (Spinner) findViewById(R.id.product_spinner);
        typeSpinner = (Spinner) findViewById(R.id.type_sp);
        custSpinner = (Spinner) findViewById(R.id.customer_sp);
        spinner.setAdapter(orderAdapter);
        typeSpinner.setAdapter(typeAdapter);
        custSpinner.setAdapter(custAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object customer = custSpinner.getSelectedItem();
                Object type = typeSpinner.getSelectedItem();
                Object product = spinner.getSelectedItem();
                Gson gson = new Gson();
                String cust_json = gson.toJson(customer);
                String prod_json = gson.toJson(product);
                String type_json = gson.toJson(type);
                try {
                    JSONObject cust = new JSONObject(cust_json);
                    String cust_id = cust.optString("id");
                    String cust_name = cust.optString("name");

                    JSONObject prod = new JSONObject(prod_json);
                    String prod_id = prod.optString("id");
                    String prod_name = prod.optString("name");

                    JSONObject typ = new JSONObject(type_json);
                    String type_id = typ.optString("id");
                    String type_name = typ.optString("name");
                    createOrderRequest(prod_id,prod_name,cust_id,cust_name,type_id,type_name);
                } catch (JSONException e) {
                    Toast.makeText(OrderActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        orderAdapter.notifyDataSetChanged();
    }

    public class SpinnerAdapter extends BaseAdapter {
        private List<SpinnerItem> mItems = new ArrayList<SpinnerItem>();
        LayoutInflater mInflater;

        public void clear() {
            mItems.clear();
        }

        public void addItem(SpinnerItem item) {
            mItems.add(item);
        }

        public void addItems(List<SpinnerItem> item) {
            mItems.addAll(item);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public SpinnerItem getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getDropDownView(int position, View view, ViewGroup parent) {
            if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
                mInflater = LayoutInflater.from(OrderActivity.this);
                view = mInflater.inflate(R.layout.spinner_item_dropdown, parent, false);
                view.setTag("DROPDOWN");
            }

            TextView textView = (TextView) view.findViewById(R.id.item_text);
            textView.setText(getTitle(position));

            return view;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
                mInflater = LayoutInflater.from(OrderActivity.this);
                view = mInflater.inflate(R.layout.spinner_item, parent, false);
                view.setTag("NON_DROPDOWN");
            }
            TextView textView = (TextView) view.findViewById(R.id.item_text);
            textView.setText(getTitle(position));
            return view;
        }

        private String getTitle(int position) {
            return position >= 0 && position < mItems.size() ? mItems.get(position).name : "";
        }
    }

    public void createOrderRequest(final String pid, final String pn, String uid, final String un, String tid, final String tn) {
        apiCall = Factory.create();
//        10/5/16, 9:22:22 PM
       final String date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(new Date());
        Operation oneOperation = new Operation(uid, pid, "1", tid, date);
        OperationRequest operationRequest = new OperationRequest();
        final Gson gson1 = new Gson();
        String post = gson1.toJson(operationRequest.withOperation(oneOperation));
        try {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            call = apiCall.addOperation(post);
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
                                ArrayList<Product> productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, OrderActivity.this);
                                for (int x = 0; x < productList.size(); x++) {
                                    if(productList.get(x).getPid().equals(pid)){
                                        int stock = Integer.parseInt(productList.get(x).getInStock()) - 1;
                                        Product oneProd = new Product(Integer.toString(stock),productList.get(x).getImageUrl(),productList.get(x).getBarcode(),productList.get(x).getProduct(),productList.get(x).getPid());
                                        productList.set(x,oneProd);
                                        break;
                                    }
                                }
                                Methods.savePrefObject(productList, Config.PREF_KEY_LIST_PRODUCTS, OrderActivity.this);
                                JSONObject json_data = json.getJSONObject("data");
                                String json_oid = json_data.optString("_id");
                                ArrayList<Order> orderList = Order.getPrefArraylist(Config.PREF_KEY_LIST_ORDERS, OrderActivity.this);
                                orderList.add(new Order(json_oid,un, pn, tn, date));

                                finish();
                            } else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                JSONObject jsonCodeMsg = json_details.getJSONObject(0);
                                String msg = jsonCodeMsg.optString("message");
                                Toast.makeText(OrderActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(OrderActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(OrderActivity.this, "Invalid or missing fields", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(OrderActivity.this, "Error While Reaching The Server", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
