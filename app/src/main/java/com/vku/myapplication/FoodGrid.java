package com.vku.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vku.myapplication.Adapter.FoodGridAdapter;
import com.vku.myapplication.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodGrid extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    FoodGridAdapter foodGridAdapter;
    ArrayList<Food> foods;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_gridview);

        recyclerView = findViewById(R.id.rc_grid_food);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        foods = new ArrayList<>();

        String url ="";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url = "http://192.168.2.113/doancoso/public/api/getfoodDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url = "http://192.168.2.113/doancoso/public/api/getfoodCat";
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i=0; i<array.length(); i++){
                        Food food = new Gson().fromJson(array.get(i).toString(),Food.class);
                        foods.add(food);
                        Log.d("img", food.getImg()+"");
                    }
                    FoodGridAdapter foodGridAdapter = new FoodGridAdapter(foods, getApplicationContext());
                    recyclerView.setAdapter(foodGridAdapter);
                    foodGridAdapter.notifyDataSetChanged();
                } catch (JSONException e){
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
}
