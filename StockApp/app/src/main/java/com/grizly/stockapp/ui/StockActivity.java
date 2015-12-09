package com.grizly.stockapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.grizly.stockapp.R;

public class StockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Stock");
        setSupportActionBar(toolbar);

        getCustomer_iv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, CustomersActivity.class);
                startActivity(intent);
            }
        });

        getProduct_iv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        getOrder_iv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        getMore_iv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public ImageView getProduct_iv() {
        return (ImageView) findViewById(R.id.product);
    }

    public ImageView getCustomer_iv() {
        return (ImageView) findViewById(R.id.customer);
    }

    public ImageView getOrder_iv() {
        return (ImageView) findViewById(R.id.order);
    }

    public ImageView getMore_iv() {
        return (ImageView) findViewById(R.id.more);
    }

}
