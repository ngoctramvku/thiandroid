package com.vku.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.DetailOrder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DanhmucOrderActivity extends AppCompatActivity {

    Button btn_cat, btn_dog, btndn, button;
    public static ArrayList<DetailOrder> mainCartArrayList;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private static final String TAG = "DanhmucActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuc);
        btn_dog = findViewById(R.id.btnDog);
        btn_cat = findViewById(R.id.btnCat);
        btn_dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DanhmucOrderActivity.this, MainActivity.class);
                i.putExtra("ctg_id", 1);
                startActivity(i);
            }
        });
        btn_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DanhmucOrderActivity.this, MainActivity.class);
                i.putExtra("ctg_id", 2);
                startActivity(i);
            }
        });
        btndn = findViewById(R.id.btndn);
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DanhmucOrderActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        button = findViewById(R.id.btndk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DanhmucOrderActivity.this, Register_Activity.class);
                startActivity(i);
            }
        });
        String user = getIntent().getStringExtra("user");
        loadData();
    }

    private void loadData() {
        sharedPreferences = getSharedPreferences("cartArray", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String cartList = sharedPreferences.getString("cartList", null);
        Type type = new TypeToken<List<Cart>>(){}.getType();
        mainCartArrayList = gson.fromJson(cartList, type);
        Log.d(TAG, "loadData: " + mainCartArrayList);
        if (mainCartArrayList == null) {
            mainCartArrayList = new ArrayList<>();
        }
    }
}
