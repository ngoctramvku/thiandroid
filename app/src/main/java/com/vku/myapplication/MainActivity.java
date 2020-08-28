package com.vku.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.vku.myapplication.Adapter.FashionAdapter;
import com.vku.myapplication.Adapter.FoodAdapter;
import com.vku.myapplication.Adapter.ProductAdapter;
import com.vku.myapplication.Model.Fashion;
import com.vku.myapplication.Model.Food;
import com.vku.myapplication.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity<onFashionClick>
        extends AppCompatActivity
        implements ProductAdapter.OnProductListener, FashionAdapter.OnFashionListener, FoodAdapter.OnFoodListener {
    BottomNavigationView bottomNavigationView;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    RecyclerView recyclerViewfas;
    RecyclerView recyclerViewfo;
    ProductAdapter productAdapter;
    ArrayList<Product> arrayList;
    FashionAdapter fashionAdapter;
    ArrayList<Fashion> arrayListfas;
    FoodAdapter foodAdapter;
    ArrayList<Food> arrayListfo;
    private Context context;
    Button btnmorepro, btnmorefas, btnmorefo;
    int[] image = {R.drawable.trangchu2, R.drawable.trangchu3,R.drawable.cho1};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        for (int i=0; i<image.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setImageResource(image[i]);
            viewFlipper.addView(imageView);
        }
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        btnmorepro =  findViewById(R.id.btnmore);
        btnmorefas =  findViewById(R.id.btnmorefas);
        btnmorefo =   findViewById(R.id.btnmorefood);
        btnmorepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("dasdas","sadasd");
                Intent i = new Intent(MainActivity.this, ProductGrid.class);
                if (getIntent().getIntExtra("ctg_id",0)==1){
                    i.putExtra("ctg_id",1);

                }else if (getIntent().getIntExtra("ctg_id",2)==2){
                    i.putExtra("ctg_id",2);
                }
                startActivity(i);
            }
        });
        btnmorefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             Intent i1 = new Intent(MainActivity.this, FashionGrid.class);
             if (getIntent().getIntExtra("ctg_id", 0)==1){
                 i1.putExtra("ctg_id", 1);
             }else if (getIntent().getIntExtra("ctg_id",0)==2){
                 i1.putExtra("ctg_id",2);
             }
             startActivity(i1);
            }
        });
        btnmorefo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i2 = new Intent(MainActivity.this, FoodGrid.class);
                if (getIntent().getIntExtra("ctg_id",0)==1){
                    i2.putExtra("ctg_id",1);

                }else  if (getIntent().getIntExtra("ctg_id",0)==2){
                    i2.putExtra("ctg_id", 2);
                }
                startActivity(i2);
            }
        }

        );
        recyclerView = findViewById(R.id.rc_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        recyclerViewfas = findViewById(R.id.rc_view1);
        recyclerViewfas.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewfas.setLayoutManager(layoutManager1);
        arrayListfas = new ArrayList<>();

        recyclerViewfo = findViewById(R.id.rc_view2);
        recyclerViewfo.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewfo.setLayoutManager(layoutManager2);
        arrayListfo = new ArrayList<>();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_cart:
                        Intent cart= new Intent(MainActivity.this, CartActivity.class);
                        startActivity(cart);
                        break;

                }
                return true;
            }
        });
        String url = "";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url = "http://192.168.2.113/doancoso/public/api/getDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url = "http://192.168.2.113/doancoso/public/api/getCat";
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("ttttt","oke");
                showProduct(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("error","qưeqwe");
            }
        }){

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        String url1 ="";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url1 = "http://192.168.2.113/doancoso/public/api/getfasDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url1 = "http://192.168.2.113/doancoso/public/api/getfasCat";
        }
        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showFashion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){

        };
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(request1);


        String url2 = "";
        if(getIntent().getIntExtra("ctg_id",0)==1){
            url2 = "http://192.168.2.113/doancoso/public/api/getfoodDog";
        }else if(getIntent().getIntExtra("ctg_id",0)==2){
            url2 = "http://192.168.2.113/doancoso/public/api/getfoodCat";
        }
        StringRequest request2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               showFood(response);
        }


            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
        queue2.add(request2);
    }

    private void showFood(String body) {
        try {
            JSONObject object = new JSONObject(body);
            JSONArray array = object.getJSONArray("data");
            for(int i=0; i<array.length(); i++){
                Food food = new Gson().fromJson(array.get(i).toString(), Food.class);
                arrayListfo.add(food);
            }
            FoodAdapter foodAdapter = new FoodAdapter(arrayListfo, context, this);
            recyclerViewfo.setAdapter(foodAdapter);
            foodAdapter.notifyDataSetChanged();

        }catch (JSONException e2)
        {
            e2.printStackTrace();
        }
    }

    private void showFashion(String body) {
        try {
            JSONObject object = new JSONObject(body);
            JSONArray array = object.getJSONArray("data");
            for (int i=0; i<array.length();i++){
                Fashion fashion = new Gson().fromJson(array.get(i).toString(), Fashion.class);
                arrayListfas.add(fashion);
            }
            FashionAdapter fashionAdapter = new FashionAdapter(arrayListfas, context, this);
            recyclerViewfas.setAdapter(fashionAdapter);
            fashionAdapter.notifyDataSetChanged();

        } catch (JSONException e1){
            e1.printStackTrace();
        }
    }

    private void showProduct(String body) {
        try
        {
            JSONObject object = new JSONObject(body);
            JSONArray array = object.getJSONArray("data");
            Log.d("qqqq", String.valueOf(array));
            for(int i=0;i<array.length();i++){
                Product product = new Gson().fromJson(array.get(i).toString(), Product.class);
                arrayList.add(product);
                Log.d("rrrr",arrayList.get(i).getPrice()+"");
                Log.d("rrrr",arrayList.get(i).getImg()+"");
            }
            ProductAdapter productAdapter = new ProductAdapter(arrayList, context, this);
            recyclerView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
            Log.d("itemcount",productAdapter.getItemCount()+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("id", arrayList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onFashionClick(int position) {
        Intent intent = new Intent(MainActivity.this, Fashion_Activity.class);
        intent.putExtra("fasid", arrayListfas.get(position).getFasid());
        startActivity(intent);
    }

    @Override
    public void onFoodClick(int position) {
        Intent intent = new Intent(MainActivity.this, Food_Activity.class);
        intent.putExtra("foodid", arrayListfo.get(position).getFoodid());
        startActivity(intent);
    }
}
