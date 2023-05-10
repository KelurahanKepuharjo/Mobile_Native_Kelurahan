package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
    boolean showpass;


    @SuppressLint("ClickableViewAccessibility")
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

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean("isLogin", false);

        if (isLogin) {
            Intent intent = new Intent(MainActivity.this, homeAdapter.class);
            startActivity(intent);
            finish();
        }
        pass_log.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=pass_log.getRight()-pass_log.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = pass_log.getSelectionEnd();
                        if (showpass) {
                           pass_log.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,R.drawable.ic_baseline_visibility_off_24,0);
                           pass_log.setTransformationMethod(PasswordTransformationMethod.getInstance());
                           showpass = false;
                        } else {
                            pass_log.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_lock_24,0,R.drawable.ic_baseline_visibility_off_24,0);
                            pass_log.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            showpass = true;
                        }
                        pass_log.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
//        pass_log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pass_log.performClick();
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        if (v == signinText) {
                Intent intent = new Intent(MainActivity.this, registrasi.class);
                startActivity(intent);
                finish();
        } else if (v == loginButton){
            String nik = nik_log.getText().toString().trim();
            String password = pass_log.getText().toString().trim();
            if (nik.isEmpty()) {
                nik_log.setError("Silahkan masukan NIK anda");
            } else if(password.isEmpty()){
                pass_log.setError("Silahkan masukan Password anda");
            } else if (nik.length() < 16) {
                nik_log.setError("NIK harus terdiri dari 16 digit");
            } else if (password.length() < 8) {
                pass_log.setError("Password harus terdiri dari 8 digit atau lebih");
            }else {
                AuthServices.login(MainActivity.this, nik, password, new AuthServices.LoginResponseListener() {
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
                        editor.putBoolean("isLogin", true);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, homeAdapter.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}