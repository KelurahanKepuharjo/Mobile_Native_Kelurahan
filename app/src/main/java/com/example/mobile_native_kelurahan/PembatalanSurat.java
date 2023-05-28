package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_native_kelurahan.Auth.AuthServices;

public class PembatalanSurat extends AppCompatActivity {

    TextView nik, nama;
    Button btn_batal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembatalan_surat);
        nik = findViewById(R.id.tx_nikUser);
        nama = findViewById(R.id.tx_namaUser);
        btn_batal = findViewById(R.id.btnbatal);

        Intent intent = getIntent();
        String anik = intent.getStringExtra("nik");
        String anama = intent.getStringExtra("nama");
        String idSurat = intent.getStringExtra("idsurat");

        nik.setText(anik);
        nama.setText(anama);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthServices.pembatalan(PembatalanSurat.this, anik, idSurat, new AuthServices.PembatalanResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(PembatalanSurat.this, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PembatalanSurat.this, homeAdapter.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(PembatalanSurat.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}