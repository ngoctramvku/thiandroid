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
import com.vku.myapplication.Model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Food_Activity extends AppCompatActivity {
    TextView txtName, txtPrice, txtKhoiluong, txtnoisx, txtngaysx, txthsd;
    ImageView imgImage;
    Button btnadd;
    private Food cloneFood;
    private String TAG="Food_Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_food);
        Init();
        getinfor();
    }
    public void Init(){
        txtName = (TextView)findViewById(R.id.txtName);
        txtPrice =(TextView)findViewById(R.id.txtPrice);
        txtKhoiluong = (TextView)findViewById(R.id.txtKhoiluong);
        txtnoisx =(TextView)findViewById(R.id.txtnoisx);
        txtngaysx =(TextView)findViewById(R.id.txtngaysx);
        txthsd = (TextView)findViewById(R.id.txthsd);
        imgImage =(ImageView) findViewById(R.id.imgImage);
        btnadd = (Button)findViewById(R.id.btnadd);
    }

    public void getinfor(){
        String foodid= getIntent().getStringExtra("foodid");
        String url ="http://192.168.2.113/doancoso/public/api/getIdFood/"+foodid;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            Food food;
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("data");
                    JSONObject fo = array.getJSONObject(0);
                    food = new Gson().fromJson(fo.toString(), Food.class);
                    infor(food);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
    public void infor(Food food){
        cloneFood = food;
        String name ="";
        Integer price= 0;
        Integer khoiluong= 0;
        String noisx ="";
        String ngaysx="";
        String hsd ="";
        String image="";

        name = food.getFoodname();
        price = food.getPrice();
        khoiluong = food.getKhoiluong();
        noisx = food.getNsx();
        ngaysx = food.getNgsx();
        hsd = food.getHsd();
        image = food.getImg();

        txtName.setText(name);
        txtPrice.setText(price.toString()+" "+"VNĐ");
        txtKhoiluong.setText(khoiluong.toString()+" "+"g");
        txtnoisx.setText(noisx);
        txtngaysx.setText(ngaysx);
        txthsd.setText(hsd);
        Picasso.with(this).load("http://192.168.2.113/doancoso/public/img/"+image).into(imgImage);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToCart(cloneFood);
            }
        });
    }
    private void addFoodToCart(Food food) {
        if (DanhmucActivity.mainCartArrayList.size() > 0) {
            boolean exists = false;
            for (int i = 0; i < DanhmucActivity.mainCartArrayList.size(); i++) {
                if (DanhmucActivity.mainCartArrayList.get(i).equals(food.getFoodid()) ) {
                    DanhmucActivity.mainCartArrayList.get(i).setQuantity(
                            DanhmucActivity.mainCartArrayList.get(i).getQuantity() + 1
                    );
                    if (DanhmucActivity.mainCartArrayList.get(i).getQuantity() >= 10) {
                        DanhmucActivity.mainCartArrayList.get(i).setQuantity(10);
                    }
                    DanhmucActivity.mainCartArrayList.get(i).setPrice(
                            food.getPrice() * DanhmucActivity.mainCartArrayList.get(i).getQuantity()
                    );
                    exists = true;
                }
            }
            if (!exists) {
                DanhmucActivity.mainCartArrayList
                        .add(new Cart(food.getImg()
                                , food.getFoodname()
                                , food.getPrice()
                                , 1));
            }
        } else {
            DanhmucActivity.mainCartArrayList
                    .add(new Cart(food.getImg()
                            , food.getFoodname()
                            , food.getPrice()
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
