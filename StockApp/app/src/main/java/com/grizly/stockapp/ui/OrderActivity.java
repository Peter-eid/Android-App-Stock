package com.grizly.stockapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.grizly.stockapp.R;
import com.grizly.stockapp.beans.SpinnerItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    List<SpinnerItem> productlist = new ArrayList<SpinnerItem>();
    SpinnerAdapter orderAdapter;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Create Order");
        setSupportActionBar(toolbar);

//        productlist = SpinnerItem.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, OrderActivity.this);
        productlist.add(new SpinnerItem("1", "Pen"));
        productlist.add(new SpinnerItem("1", "Pencil"));
        productlist.add(new SpinnerItem("1", "laptop"));
        productlist.add(new SpinnerItem("1", "Dossier"));

        orderAdapter = new SpinnerAdapter();
        orderAdapter.addItems(productlist);
        spinner = (Spinner) findViewById(R.id.product_spinner);
        spinner.setAdapter(orderAdapter);

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
                view = mInflater.inflate(R.layout.
                        spinner_item, parent, false);
                view.setTag("NON_DROPDOWN");
            }
            TextView textView = (TextView) view.findViewById(R.id.item_text);
            textView.setText(getTitle(position));
            return view;
        }

        private String getTitle(int position) {
            return position >= 0 && position < mItems.size() ? mItems.get(position).name: "";
        }
    }
}
