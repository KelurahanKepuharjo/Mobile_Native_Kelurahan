package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class registrasi extends AppCompatActivity implements View.OnClickListener{

    EditText nik_reg,notelp_reg,pass_reg,cpass_reg;
    Button btnDaftar;
    TextView signupText;
    private static String REGISTER_URL = "http://192.168.0.117:8000/api/auth/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        nik_reg = findViewById(R.id.NIK);
        pass_reg = findViewById(R.id.password);
        notelp_reg = findViewById(R.id.notelp);
        cpass_reg = findViewById(R.id.KonfirmasiKataSandi);
        btnDaftar = findViewById(R.id.btnDaftar);
        signupText = findViewById(R.id.signupText);

        btnDaftar.setOnClickListener(this);
        signupText.setOnClickListener(this);



//        btnDaftar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(registrasi.this, MainActivity.class));
//            }
//        });

        signupText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrasi.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == signupText) {
            Intent intent = new Intent(registrasi.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if(v == btnDaftar){
            String nik = nik_reg.getText().toString().trim();
            String no_tlp = notelp_reg.getText().toString().trim();
            String pass = pass_reg.getText().toString().trim();
            String cpass = cpass_reg.getText().toString().trim();

            if (!nik.isEmpty() && !no_tlp.isEmpty() && !pass.isEmpty() && !cpass.isEmpty() && !no_tlp.isEmpty()) {
                if (pass.equals(cpass)) {
                    Register();
                }else {
                    Toast.makeText(this, "Kata sandi harus sama", Toast.LENGTH_SHORT).show();
                }
            }else{
                nik_reg.setError("Silahkan masukan nik anda");
                notelp_reg.setError("Silahkan masukan nomor telepon anda");
                pass_reg.setError("Silahkan masukan kata sandi anda");
                cpass_reg.setError("Silahkan masukan konfirmasi kata sandi anda");
            }
        }
    }

    private  void  Register(){
        final String nik = this.nik_reg.getText().toString().trim();
        final String pass = this.pass_reg.getText().toString().trim();
        final String no_tlp = notelp_reg.getText().toString().trim();
        final String cpass = this.cpass_reg.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("Berhasil Register")){
                        JSONObject userObj = jsonObject.getJSONObject("user");
                        Toast.makeText(registrasi.this, "Berhasil Ya", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(registrasi.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String message = jsonObject.getString("message");
                            if (message.equals("Akun sudah terdaftar")) {
                                nik_reg.setError("Akun sudah terdaftar");
                            } else if (message.equals("Nik anda belum terdaftar")) {
                                nik_reg.setError("Nik anda belum terdaftar");
                            }
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                            nik_reg.setError("Gagal register: " + e.getMessage());
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("password", pass);
                params.put("no_hp", no_tlp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(registrasi.this);
        requestQueue.add(stringRequest);
    }
}