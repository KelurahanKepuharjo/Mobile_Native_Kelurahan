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
import com.example.mobile_native_kelurahan.Auth.AuthServices;

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
    }

    @Override
    public void onClick(View v) {
        if (v == signupText) {
            Intent intent = new Intent(registrasi.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if(v == btnDaftar){
            String nik = nik_reg.getText().toString().trim();
            String no_tlp = notelp_reg.getText().toString().trim();
            String pass = pass_reg.getText().toString().trim();
            String cpass = cpass_reg.getText().toString().trim();

            if (!nik.isEmpty() && !no_tlp.isEmpty() && !pass.isEmpty() && !cpass.isEmpty() && !no_tlp.isEmpty()) {
                 if (nik.length() < 16) {
                    nik_reg.setError("NIK harus terdiri dari 16 digit");
                } else if (pass.length() < 8) {
                    pass_reg.setError("Password harus terdiri dari 8 digit atau lebih");
                } else if(!pass.equals(cpass)){
                     pass_reg.setError("Password harus sama");
                     cpass_reg.setError("Password harus sama");
                 } else {
                     AuthServices.register(this, nik, pass, no_tlp, new AuthServices.RegisterResponseListener() {
                         @Override
                         public void onSuccess(JSONObject response) {
                             Toast.makeText(registrasi.this, "Berhasil Mengaktifkan Akun Anda", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(registrasi.this, MainActivity.class);
                             startActivity(intent);
                             finish();
                         }

                         @Override
                         public void onError(String message) {
                             nik_reg.setError(message);
                             Toast.makeText(registrasi.this, message, Toast.LENGTH_LONG).show();
                         }
                     });
                 }
            } else {
                nik_reg.setError("Silahkan masukan nik anda");
                notelp_reg.setError("Silahkan masukan nomor telepon anda");
                pass_reg.setError("Silahkan masukan kata sandi anda");
                cpass_reg.setError("Silahkan masukan konfirmasi kata sandi anda");
            }
        }
    }
}
