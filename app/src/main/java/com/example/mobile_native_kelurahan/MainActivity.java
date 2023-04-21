package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_native_kelurahan.Auth.AuthServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText nik_log,pass_log;
    Button loginButton;
    TextView signinText;
    private static String LOGIN_URL = "http://192.168.0.117:8000/api/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nik_log = findViewById(R.id.usernamek);
        pass_log = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signinText = findViewById(R.id.signinText);

        loginButton.setOnClickListener(this);
        signinText.setOnClickListener(this);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (nik.getText().toString().equals("111") && password.getText().toString().equals("111")) {
//                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, homeAdapter.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        signinText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, registrasi.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (v == signinText) {
                Intent intent = new Intent(MainActivity.this, registrasi.class);
                startActivity(intent);
                finish();
        }else if (v == loginButton){
            String nik = nik_log.getText().toString().trim();
            String password = pass_log.getText().toString().trim();
            if (nik.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, "Nik atau Password tidak bole kosong", Toast.LENGTH_SHORT).show();
            }else {
                AuthServices.login(this, nik, password, new AuthServices.LoginResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Intent intent = new Intent(MainActivity.this, homeAdapter.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTokenReceived(String token) {
                        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                        editor.putString("token", token);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, homeAdapter.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void Login(String nik, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (message.equals("Berhasil Login")) {
                        Intent intent = new Intent(MainActivity.this, homeAdapter.class);
                        startActivity(intent);
                        finish();
                        String token = jsonObject.getString("token");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("user");
                        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                        editor.putString("token", token);
                        editor.apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Login gagal" + error.getMessage(), Toast.LENGTH_LONG);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}