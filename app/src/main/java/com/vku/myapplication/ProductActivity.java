package com.vku.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vku.myapplication.Adapter.FashionAdapter;
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.Fashion;
import com.vku.myapplication.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {
    TextView txtName, txtPrice, txtheight, txtweight, txtgender, txtcolor;
    ImageView imgImage;
    private Product cloneProduct;
    Button btnadd;
    private static final String TAG = "ProductActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        Init();
        getinformation();
    }

    public void Init() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtheight = (TextView) findViewById(R.id.txtHeight);
        txtweight = (TextView) findViewById(R.id.txtweight);
        txtgender = (TextView) findViewById(R.id.txtgender);
        txtcolor = (TextView) findViewById(R.id.txtcolor);
        imgImage = (ImageView) findViewById(R.id.imgImage);
        btnadd = (Button) findViewById(R.id.btnadd);
    }

    public void getinformation() {
        int id = getIntent().getIntExtra("id", 0);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.2.113/doancoso/public/api/getIdProduct/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            Product product;

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray contacts = jsonObj.getJSONArray("data");
                    JSONObject c = contacts.getJSONObject(0);
                    product = new Gson().fromJson(c.toString(), Product.class);
                    getinfor(product);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);

    }

    public void getinfor(Product product) {
        cloneProduct = product;
        String name = "";
        Integer price = 0;
        String height = "";
        String weight = "";
        String gender = "";
        String color = "";
        String image = "";

        name = product.getName();
        price = product.getPrice();
        height = product.getHeight();
        weight = product.getWeight();
        gender = product.getGender();
        color = product.getColor();
        image = product.getImg();


        txtName.setText(name);
        txtPrice.setText(price.toString()+" "+"VNĐ");
        txtheight.setText(height+" "+"cm");
        txtweight.setText(weight+" "+"kg");
        txtgender.setText(gender);
        txtcolor.setText(color);
        Picasso.with(this).load("http://192.168.2.113/doancoso/public/img/" + image).into(imgImage);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(cloneProduct);
            }
        });
    }

    private void addProductToCart(Product product) {
        if (DanhmucActivity.mainCartArrayList.size() > 0) {
            boolean exists = false;
            for (int i = 0; i < DanhmucActivity.mainCartArrayList.size(); i++) {
                if (DanhmucActivity.mainCartArrayList.get(i).getId() == product.getId()) {

                    DanhmucActivity.mainCartArrayList.get(i).setQuantity(
                            DanhmucActivity.mainCartArrayList.get(i).getQuantity() + 1
                    );
                    if (DanhmucActivity.mainCartArrayList.get(i).getQuantity() >= 10) {
                        DanhmucActivity.mainCartArrayList.get(i).setQuantity(10);
                    }
                    DanhmucActivity.mainCartArrayList.get(i).setPrice(
                            product.getPrice() * DanhmucActivity.mainCartArrayList.get(i).getQuantity()
                    );
                    exists = true;
                }
            }
            if (!exists) {
                DanhmucActivity.mainCartArrayList
                        .add(new Cart(product.getImg()
                                , product.getName()
                                , product.getPrice()
                                , 1));
            }
        } else {
            DanhmucActivity.mainCartArrayList
                    .add(new Cart(product.getImg()
                            , product.getName()
                            , product.getPrice()
                            , 1));


        }
        saveCartData();
    }

    private void saveCartData() {
        Gson gson = new Gson();
        String json = gson.toJson(DanhmucActivity.mainCartArrayList);
        DanhmucActivity.sharedPreferences = getSharedPreferences("cartArray", Context.MODE_PRIVATE);
        DanhmucActivity.editor = DanhmucActivity.sharedPreferences.edit();
        DanhmucActivity.editor.putString("cartList", json);
        Log.d(TAG, "saveCartData: " + getSharedPreferences("cartArray", Context.MODE_PRIVATE).getString("cartList", null));
        DanhmucActivity.editor.apply();
    }
}
