package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class tentang extends AppCompatActivity {

    ImageView gbrTentang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        gbrTentang = findViewById(R.id.gbrTentang);

        gbrTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tentang.this, ProfileFragment.class);
                startActivity(intent);
                finish();
            }
        });
    }
//        @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }
}