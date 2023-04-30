package com.example.mobile_native_kelurahan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class form_pengajuan extends AppCompatActivity {

    String[] item = {"faisal","okta","brian","FOS","ical"};
    AutoCompleteTextView dropdown_menu1;
    ArrayAdapter<String> adapterItems;
    Button btnKirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengajuan);

        dropdown_menu1 = findViewById(R.id.dropdown_menu1);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_dropdown, item);
        dropdown_menu1.setAdapter(adapterItems);
        dropdown_menu1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
              String item = adapterView.getItemAtPosition(position).toString();
              Toast.makeText(form_pengajuan.this, "Data " + item, Toast.LENGTH_SHORT).show();
            }
        });

        btnKirim = findViewById(R.id.btnKirim);
        ImageView btnBack = findViewById(R.id.btnBack);
//        TextView toolbar_title = findViewById(R.id.toolbar_title);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form_pengajuan.this, homeAdapter.class);
                startActivity(intent);
                finish();
                Toast.makeText(form_pengajuan.this, "Pengajuan Anda Telah Dikirim", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form_pengajuan.this, homeAdapter.class);
                startActivity(intent);
                finish();
//                Toast.makeText(form_pengajuan.this, "anda kembali", Toast.LENGTH_SHORT).show();
            }
        });
    }
}