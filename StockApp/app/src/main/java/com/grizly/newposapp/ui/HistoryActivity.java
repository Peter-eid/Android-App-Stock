package com.grizly.newposapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grizly.newposapp.Config;
import com.grizly.newposapp.Methods;
import com.grizly.newposapp.R;
import com.grizly.newposapp.beans.Order;
import com.grizly.newposapp.beans.Product;
import com.grizly.newposapp.connectivity.Factory;
import com.grizly.newposapp.connectivity.MyApiEndpointInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    MyApiEndpointInterface apiCall;
    Call<String> call;

    public ArrayList<Order> orderList = new ArrayList<Order>();
    public ArrayList<Order> useorderList = new ArrayList<Order>();

    public ProductsAdapter productAdapter;
    public ListView productLV;

    public ArrayList<Product> xvxv = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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
        productLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                deleteDialog(position);
                return false;
            }
        });

    }

    void deleteDialog(final int p) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Are you sure you want to delete")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteOrderRequest(orderList.get(p).getOid(), p);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void deleteOrderRequest(String oid, final int p) {

        apiCall = Factory.create();
        try {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            call = apiCall.deleteOperation(oid);
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
                                orderList.remove(p);
                                productAdapter.notifyDataSetChanged();
                                Methods.savePrefObject(orderList, Config.PREF_KEY_LIST_ORDERS, HistoryActivity.this);
                                Toast.makeText(HistoryActivity.this, "Order Deleted Successfully", Toast.LENGTH_LONG).show();
                            } else {
                                JSONObject json_error = json.getJSONObject("errors");
                                JSONArray json_details = json_error.getJSONArray("details");
                                JSONObject jsonCodeMsg = json_details.getJSONObject(0);
                                String msg = jsonCodeMsg.optString("message");
                                Toast.makeText(HistoryActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(HistoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(HistoryActivity.this, "Error while deleting Order", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Toast.makeText(HistoryActivity.this, "Error While Reaching The Server", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
                convertView = inflater.inflate(R.layout.row_history, null);

                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();

            }

//            holder.type.get().setText(getItem(i).getType());
            holder.date.get().setText(getItem(i).getDate());
            holder.name.get().setText(getItem(i).getProductName());
            holder.username.get().setText(getItem(i).getName());
            holder.type.get().setText(getItem(i).getType());
            holder.leftIcon.get().setText(getItem(i).getProductName().substring(0, 1).toUpperCase());

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

        public WeakReference<TextView> name;
        public WeakReference<TextView> type;
        public WeakReference<TextView> date;
        public WeakReference<TextView> username;
        public WeakReference<TextView> leftIcon;
        public WeakReference<View> circularBackgroud;

        public ViewHolder(View view) {
            name = new WeakReference<TextView>((TextView) view.findViewById(R.id.name));
            type = new WeakReference<TextView>((TextView) view.findViewById(R.id.type));
            date = new WeakReference<TextView>((TextView) view.findViewById(R.id.date));
            username = new WeakReference<TextView>((TextView) view.findViewById(R.id.username));
            leftIcon = new WeakReference<TextView>((TextView) view.findViewById(R.id.leftIcon));
            circularBackgroud = new WeakReference<View>((View) view.findViewById(R.id.circularView));
        }
    }
}
