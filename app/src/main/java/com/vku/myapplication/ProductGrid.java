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
import com.vku.myapplication.Adapter.ProductGridAdapter;
import com.vku.myapplication.Model.Product;
import com.vku.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductGrid extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    ProductGridAdapter productGridAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_gridview);
        recyclerView =findViewById(R.id.rc_gird_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        products = new ArrayList<>();

        String url = "";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url = "http://192.168.2.113/doancoso/public/api/getDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url = "http://192.168.2.113/doancoso/public/api/getCat";
        }
        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i =0; i<array.length();i++){
                        Product product = new Gson().fromJson(array.get(i).toString(), Product.class);
                        products.add(product);
                        Log.d("img",product.getImg()+"");
                    }
                    ProductGridAdapter productGridAdapter = new ProductGridAdapter(products, ProductGrid.this);
                    recyclerView.setAdapter(productGridAdapter);
                    productGridAdapter.notifyDataSetChanged();
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
