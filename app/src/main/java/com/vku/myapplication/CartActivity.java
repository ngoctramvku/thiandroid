package com.vku.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vku.myapplication.Adapter.CartAdapter;
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private TextView txtphonenumber, txttenkh;
    private TextView txtaddress;
    private TextView txttotal;
    private CartAdapter cartAdapter;
    private ArrayList<Cart> dsgh;
    private User user;
    private Button btndathang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        Init();
    }
    public void Init(){
        recyclerView = findViewById(R.id.rc_cart);
        cartAdapter = new CartAdapter(DanhmucActivity.mainCartArrayList, getApplicationContext());
        cartAdapter.setOnRemoveItem(new CartAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {
                Toast.makeText(CartActivity.this, "Delete"+DanhmucActivity.mainCartArrayList.remove(i).getName(), Toast.LENGTH_SHORT).show();
                cartAdapter.notifyDataSetChanged();
                calculateTotalPrice();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setHasFixedSize(true);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.action_cart).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_cart:

                        Intent cart= new Intent(CartActivity.this, CartActivity.class);
                        startActivity(cart);
                        finish();
                        break;

                    case R.id.action_tc:
                        finish();
                        break;


                }
                return true;
            }
        });
        txtphonenumber = findViewById(R.id.txtphonenumber);
        txtaddress = findViewById(R.id.txtaddress);
        txttotal = findViewById(R.id.txttotal);
        txttenkh = findViewById(R.id.txttenkh);
        btndathang = findViewById(R.id.btnthanhtoan);
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(CartActivity.this, DetailOrderActivity.class);
                startActivity(t);
            }
        });
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < DanhmucActivity.mainCartArrayList.size(); i++) {
            totalPrice += DanhmucActivity.mainCartArrayList.get(i).getPrice();
        }
        txttotal.setText(String.valueOf(totalPrice)+" "+"VNĐ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("USER",Context.MODE_PRIVATE);
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

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String json = gson.toJson(DanhmucActivity.mainCartArrayList);
        DanhmucActivity.sharedPreferences = getSharedPreferences("cartArray", Context.MODE_PRIVATE);
        DanhmucActivity.editor = DanhmucActivity.sharedPreferences.edit();
        DanhmucActivity.editor.putString("cartList", json);
        DanhmucActivity.editor.commit();
    }

}
