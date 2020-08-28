package com.vku.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vku.myapplication.Adapter.CartAdapter;
import com.vku.myapplication.Adapter.DetailOrderAdapter;
import com.vku.myapplication.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class DetailOrderActivity extends AppCompatActivity {
    private TextView txttenkh, txtphonenumber, txtaddress, txttotal,txtiddonhang;
    private RecyclerView recyclerView;
//    ArrayList<DetailOrder> arrayListdetailOrders;
    DetailOrderAdapter cartAdapter;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order_activity);
        Init();
//        getId();
    }
    public void Init(){
        recyclerView = findViewById(R.id.rc_detailorder);
        cartAdapter = new DetailOrderAdapter(DanhmucOrderActivity.mainCartArrayList, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setHasFixedSize(true);
        txttenkh = (TextView) findViewById(R.id.txttenkh);
        txtiddonhang = (TextView) findViewById(R.id.txtiddonhang);
        txtphonenumber = (TextView) findViewById(R.id.txtphonenumber);
        txtaddress = (TextView) findViewById(R.id.txtaddress);
        txttotal = (TextView) findViewById(R.id.txttotal);
//        t = getIntent();
//        id =
        calculateTotalPrice();
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            Gson gson = new Gson();
            String cartList = sharedPreferences.getString("user", null);
            Type type = new TypeToken<User>(){}.getType();
            user = gson.fromJson(cartList, type);
            txttenkh.setText(user.getFullname());
            txtaddress.setText(user.getAddress());
            txtphonenumber.setText(user.getPhonenumber());
        }
    }
    public void getId(){
        String url ="http://192.168.2.113/doancoso/public/api/getIdOrder/";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for(int i=0; i<array.length(); i++){
//                        DetailOrder detailOrder = new Gson().fromJson(array.get(i).toString(), DetailOrder.class);
//                        arrayListdetailOrders.add(detailOrder);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    private void calculateTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < DanhmucActivity.mainCartArrayList.size(); i++) {
            totalPrice += DanhmucActivity.mainCartArrayList.get(i).getPrice();
        }
        Date date = new Date();
        long timeMilli = date.getTime();
        txtiddonhang.setText(timeMilli+"");
        recyclerView = findViewById(R.id.rc_detailorder);

        txttotal.setText(String.valueOf(totalPrice)+" "+"VNĐ");
    }
}

