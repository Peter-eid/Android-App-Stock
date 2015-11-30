package com.grizly.stockapp.ui;

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

import com.grizly.stockapp.R;
import com.grizly.stockapp.beans.Customer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {

    public ArrayList<Customer> customerList = new ArrayList<Customer>();
    public CustomerAdapter customerAdapter;
    public ListView customerLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatTextView title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("Customers");
        setSupportActionBar(toolbar);

//        productList = Product.getPrefArraylist(Config.PREF_KEY_LIST_PRODUCTS, this);
        customerLV = (ListView) findViewById(R.id.list);
        customerAdapter = new CustomerAdapter(CustomersActivity.this, customerList);
        try {
            customerLV.setAdapter(customerAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));
        customerList.add(new Customer("Michel Tabib", "", "70049308", "dev"));

        customerAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(CustomersActivity.this, AddCustomerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CustomerAdapter extends BaseAdapter {

        ArrayList<Customer> customerList;
        ViewHolder holder;
        Context context;

        public CustomerAdapter(Context context, ArrayList<Customer> customerList) {
            this.customerList = customerList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return customerList.size();
        }

        @Override
        public Customer getItem(int position) {
            return customerList.get(position);
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
                convertView = inflater.inflate(R.layout.row_customer, null);

                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.customerName.get().setText(getItem(i).getFullName());
            holder.customerPn.get().setText(getItem(i).getPhoneNumber());
            holder.leftIcon.get().setText(getItem(i).getFullName().substring(0, 1).toUpperCase());

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

        public WeakReference<TextView> customerName;
        public WeakReference<TextView> customerPn;
        public WeakReference<TextView> leftIcon;
        public WeakReference<View> circularBackgroud;

        public ViewHolder(View view) {
            customerName = new WeakReference<TextView>((TextView) view.findViewById(R.id.name));
            customerPn = new WeakReference<TextView>((TextView) view.findViewById(R.id.phone));
            leftIcon = new WeakReference<TextView>((TextView) view.findViewById(R.id.leftIcon));
            circularBackgroud = new WeakReference<View>((View) view.findViewById(R.id.circularView));
        }
    }

}
