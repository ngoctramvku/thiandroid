package com.vku.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vku.myapplication.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText username = null;
    EditText password = null;
    Button btn = null;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edittxtuserk);
        password = findViewById(R.id.edittxtpassword);
        btn = findViewById(R.id.btndn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url = "http://192.168.2.113/doancoso/public/api/login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    JSONArray array = object.getJSONArray("data");
                                    Log.d("ABC", array.toString());
                                    for (int i  =0; i< array.length(); i++){
                                        JSONObject item = array.getJSONObject(i);
                                        User user = new User();
                                        user.setId(item.getString("id"));
                                        user.setFullname(item.getString("fullname"));
                                        user.setAddress(item.getString("address"));
                                        user.setPhonenumber(item.getString("phonenumber"));

                                        Gson gson = new Gson();
                                        String json = gson.toJson(user);
                                        DanhmucActivity.sharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE);
                                        DanhmucActivity.editor = DanhmucActivity.sharedPreferences.edit();
                                        DanhmucActivity.editor.putString("user", json);
                                        DanhmucActivity.editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), DanhmucActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("Response", response);

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}
