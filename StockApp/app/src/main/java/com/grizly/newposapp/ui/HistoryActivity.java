package com.grizly.newposapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grizly.newposapp.Config;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.Order;
import com.grizly.newposapp.beans.Product;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    public ArrayList<Order> orderList = new ArrayList<Order>();
    public ArrayList<Order> useorderList = new ArrayList<Order>();

    public ProductsAdapter productAdapter;
    public ListView productLV;

    public ArrayList<Product> xvxv = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("History");
        setSupportActionBar(toolbar);

//      orderList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, this);
        productLV = (ListView) findViewById(R.id.list);
        productAdapter = new ProductsAdapter(HistoryActivity.this, orderList);
        try {
            productLV.setAdapter(productAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        orderList.clear();
        useorderList.clear();
        useorderList = Order.getPrefArraylist(Config.PREF_KEY_LIST_ORDERS, this);
        for(int i = 0 ;  i < useorderList.size(); i++){
            orderList.add((Order) ((useorderList).get(i)));
        }
        productAdapter.notifyDataSetChanged();

        super.onResume();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.add_action:
//                Intent intent = new Intent(HistoryActivity.this, AddProductActivity.class);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public class ProductsAdapter extends BaseAdapter {

        ArrayList<Order> orderList;
        ViewHolder holder;
        Context context;

        public ProductsAdapter(Context context, ArrayList<Order> orderList) {
            this.orderList = orderList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return orderList.size();
        }

        @Override
        public Order getItem(int position) {
            return orderList.get(position);
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

            holder.type.get().setText(getItem(i).getType());
            holder.date.get().setText(getItem(i).getDate());
            holder.leftIcon.get().setText(getItem(i).getType().substring(0, 1).toUpperCase());

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

        public WeakReference<TextView> type;
        public WeakReference<TextView> date;
        public WeakReference<TextView> leftIcon;
        public WeakReference<View> circularBackgroud;

        public ViewHolder(View view) {
            type = new WeakReference<TextView>((TextView) view.findViewById(R.id.name));
            date = new WeakReference<TextView>((TextView) view.findViewById(R.id.inStock));
            leftIcon = new WeakReference<TextView>((TextView) view.findViewById(R.id.leftIcon));
            circularBackgroud = new WeakReference<View>((View) view.findViewById(R.id.circularView));
        }
    }
}
