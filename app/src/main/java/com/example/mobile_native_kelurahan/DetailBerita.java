package com.example.mobile_native_kelurahan;

import static com.example.mobile_native_kelurahan.R.id.dSubtitle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailBerita extends AppCompatActivity {
    private TextView txtJudul,txtSubtitle,txtDeskripsi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        txtJudul = findViewById(R.id.dJudul);
        txtSubtitle = findViewById(R.id.dSubtitle);
        txtDeskripsi = findViewById(R.id.dDeskripsi);

        Intent intent = getIntent();
        String judul = intent.getStringExtra("judulBerita");
        String subtitle = intent.getStringExtra("subtitle");
        String deskripsi = intent.getStringExtra("deskripsi");

        txtJudul.setText(judul);
        txtSubtitle.setText(subtitle);
        txtDeskripsi.setText(deskripsi);
    }
}