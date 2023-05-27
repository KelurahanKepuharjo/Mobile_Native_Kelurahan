package com.example.mobile_native_kelurahan;

import static com.example.mobile_native_kelurahan.R.id.dSubtitle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_native_kelurahan.Auth.AuthServices;

public class DetailBerita extends AppCompatActivity {
    private TextView txtJudul,txtSubtitle,txtDeskripsi;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        txtJudul = findViewById(R.id.dJudul);
        txtSubtitle = findViewById(R.id.dSubtitle);
        txtDeskripsi = findViewById(R.id.dDeskripsi);
        imageView = findViewById(R.id.dImage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }

        Intent intent = getIntent();
        String judul = intent.getStringExtra("judulBerita");
        String subtitle = intent.getStringExtra("subtitle");
        String deskripsi = intent.getStringExtra("deskripsi");
        String image = intent.getStringExtra("image");

        txtJudul.setText(judul);
        txtSubtitle.setText(subtitle);
        txtDeskripsi.setText(deskripsi);
        Glide.with(getApplicationContext()).load(AuthServices.getIMAGE()+image).into(imageView);
    }
}