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
import com.vku.myapplication.Adapter.FashionGridAdapter;
import com.vku.myapplication.Model.Fashion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FashionGrid extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    FashionGridAdapter fashionGridAdapter;
    ArrayList<Fashion> fashions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fashion_gridview);

        recyclerView = findViewById(R.id.rc_grid_fashion);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        fashions = new  ArrayList<>();
        String url ="";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url = "http://192.168.2.113/doancoso/public/api/getfasDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url = "http://192.168.2.113/doancoso/public/api/getfasCat";
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i<array.length();i++){
                        Fashion fashion = new Gson().fromJson(array.get(i).toString(), Fashion.class);
                        fashions.add(fashion);
                        Log.d("img",fashion.getImg()+"");
                    }
                    FashionGridAdapter fashionGridAdapter = new FashionGridAdapter(fashions, getApplicationContext());
                    recyclerView.setAdapter(fashionGridAdapter);
                    fashionGridAdapter.notifyDataSetChanged();
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
}
