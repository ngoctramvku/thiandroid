package com.vku.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {
    EditText name = null;
    EditText address = null;
    EditText phone = null;
    EditText email = null;
    EditText user = null;
    EditText password1 = null;
    EditText password2 = null;
    Button click = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name = findViewById(R.id.edittxtname);
        address = findViewById(R.id.edittxtdc);
        phone = findViewById(R.id.edittxtsdt);
        email = findViewById(R.id.edittxtemail);
        user = findViewById(R.id.edittxtuser);
        password1 = findViewById(R.id.edittxtmk);
        password2 = findViewById(R.id.edittxtcfmk);

        click = findViewById(R.id.btndk);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RequestQueue queue = Volley.newRequestQueue(Register_Activity.this);
                String url = "http://192.168.2.113/doancoso/public/api/Register";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
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
                        params.put("fullname", name.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("username", user.getText().toString());
                        params.put("address", address.getText().toString());
                        params.put("phonenumber", phone.getText().toString());
                        params.put("password", password1.getText().toString());
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}
