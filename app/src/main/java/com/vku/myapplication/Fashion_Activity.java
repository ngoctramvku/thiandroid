package com.vku.myapplication;

import android.content.Context;
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
import com.vku.myapplication.Model.Cart;
import com.vku.myapplication.Model.Fashion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fashion_Activity extends AppCompatActivity {
    private static final String TAG = "Fashion_Activity";
    TextView txtName, txtPrice, txtsize, txtnoisx;
    ImageView imgImage;
    Button btnadd;
    private Fashion cloneFashion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fashion);
        Init();
        getinfor();
    }
    public void Init(){
        txtName = (TextView)findViewById(R.id.txtName);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtsize = (TextView)findViewById(R.id.txtsize);
        txtnoisx = (TextView) findViewById(R.id.txtnoisx);
        imgImage =(ImageView) findViewById(R.id.imgImage);
        btnadd = (Button)findViewById(R.id.btnadd);
    }

    public void getinfor(){
        String fasid = getIntent().getStringExtra("fasid");
        String url = "http://192.168.2.113/doancoso/public/api/getIdFashion/"+fasid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            Fashion fashion;
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("data");
                    JSONObject f = array.getJSONObject(0);
                    fashion = new Gson().fromJson(f.toString(),Fashion.class);
                    infor(fashion);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void infor(Fashion f){
        cloneFashion = f;
        String name ="";
        Integer price= 0;
        String size="";
        String nsx ="";
        String image="";

        name=f.getName();
        price=f.getPrice();
        size=f.getSize();
        nsx=f.getNsx();
        image=f.getImg();

        txtName.setText(name);
        txtPrice.setText(price.toString()+" "+"VNĐ");
        txtsize.setText(size);
        txtnoisx.setText(nsx);
        Picasso.with(this).load("http://192.168.2.113/doancoso/public/img/"+image).into(imgImage);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFashionToCart(cloneFashion);
            }
        });
    }
    private void addFashionToCart(Fashion fashion) {
        if (DanhmucActivity.mainCartArrayList.size() > 0) {
            boolean exists = false;
            for (int i = 0; i < DanhmucActivity.mainCartArrayList.size(); i++) {
                if (DanhmucActivity.mainCartArrayList.get(i).equals(fashion.getFasid()) ) {
                    DanhmucActivity.mainCartArrayList.get(i).setQuantity(
                            DanhmucActivity.mainCartArrayList.get(i).getQuantity() + 1
                    );
                    if (DanhmucActivity.mainCartArrayList.get(i).getQuantity() >= 10) {
                        DanhmucActivity.mainCartArrayList.get(i).setQuantity(10);
                    }
                    DanhmucActivity.mainCartArrayList.get(i).setPrice(
                            fashion.getPrice() * DanhmucActivity.mainCartArrayList.get(i).getQuantity()
                    );
                    exists = true;
                }
            }
            if (!exists) {
                DanhmucActivity.mainCartArrayList
                        .add(new Cart(fashion.getImg()
                                , fashion.getName()
                                , fashion.getPrice()
                                , 0));
            }
        } else {
            DanhmucActivity.mainCartArrayList
                    .add(new Cart(fashion.getImg()
                            , fashion.getName()
                            , fashion.getPrice()
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
