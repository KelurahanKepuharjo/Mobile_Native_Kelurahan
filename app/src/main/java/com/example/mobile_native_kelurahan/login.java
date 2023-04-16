package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class login extends AppCompatActivity {

    EditText NIK;
    EditText NoKK;
    EditText password;
    EditText KonfirmasiKataSandi;
    Button btnDaftar;
//    TextView signinText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NIK = findViewById(R.id.NIK);
        NoKK = findViewById(R.id.NoKK);
        password = findViewById(R.id.password);
        KonfirmasiKataSandi = findViewById(R.id.KonfirmasiKataSandi);
        btnDaftar = findViewById(R.id.btnDaftar);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (usernamek.getText().toString().equals("111") && password.getText().toString().equals("111")) {
//                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, login.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}