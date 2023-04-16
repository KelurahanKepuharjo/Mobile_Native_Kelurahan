package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class registrasi extends AppCompatActivity {

    EditText NIK;
    EditText NoKK;
    EditText password;
    EditText KonfirmasiKataSandi;
    Button btnDaftar;
//    TextView signinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        NIK = findViewById(R.id.NIK);
        NoKK = findViewById(R.id.NoKK);
        password = findViewById(R.id.password);
        KonfirmasiKataSandi = findViewById(R.id.KonfirmasiKataSandi);
        btnDaftar = findViewById(R.id.btnDaftar);
//        signinText = findViewById(R.id.signinText);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registrasi.this, MainActivity.class));
            }
        });

//        signinText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(registrasi.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}