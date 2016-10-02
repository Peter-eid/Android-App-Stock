package com.grizly.newposapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grizly.newposapp.Config;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.Product;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    public ArrayList<Product> productList = new ArrayList<Product>();
    public ArrayList<Product> useproductList = new ArrayList<Product>();

    public ProductsAdapter productAdapter;
    public ListView productLV;

    public ArrayList<Product> xvxv = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Products");
        setSupportActionBar(toolbar);

//      productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, this);
        productLV = (ListView) findViewById(R.id.list);
        productAdapter = new ProductsAdapter(ProductsActivity.this, productList);
        try {
            productLV.setAdapter(productAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        productList.clear();
        useproductList.clear();
        useproductList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, this);
        for(int i = 0 ;  i < useproductList.size(); i++){
            productList.add((Product)((useproductList).get(i)));
        }
        productAdapter.notifyDataSetChanged();

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_action:
                Intent intent = new Intent(ProductsActivity.this, AddProductActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ProductsAdapter extends BaseAdapter {

        ArrayList<Product> productList;
        ViewHolder holder;
        Context context;

        public ProductsAdapter(Context context, ArrayList<Product> productList) {
            this.productList = productList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Product getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {

            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_product, null);

                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.productName.get().setText(getItem(i).getProduct());
            holder.productInStock.get().setText(getItem(i).getInStrock());
            holder.leftIcon.get().setText(getItem(i).getProduct().substring(0, 1).toUpperCase());

            switch (i % 6) {
                case 0:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_green));
                    break;

                case 1:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_blue));
                    break;

                case 2:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_purple));
                    break;

                case 3:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_yellow));
                    break;

                case 4:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_grey));
                    break;

                case 5:
                    holder.circularBackgroud.get().setBackground(context.getResources().getDrawable(R.drawable.circular_background_red));
                    break;

                default:
                    break;
            }

            return convertView;
        }
    }

    public class ViewHolder {

        public WeakReference<TextView> productName;
        public WeakReference<TextView> productInStock;
        public WeakReference<TextView> leftIcon;
        public WeakReference<View> circularBackgroud;

        public ViewHolder(View view) {
            productName = new WeakReference<TextView>((TextView) view.findViewById(R.id.name));
            productInStock = new WeakReference<TextView>((TextView) view.findViewById(R.id.inStock));
            leftIcon = new WeakReference<TextView>((TextView) view.findViewById(R.id.leftIcon));
            circularBackgroud = new WeakReference<View>((View) view.findViewById(R.id.circularView));
        }
    }
}
