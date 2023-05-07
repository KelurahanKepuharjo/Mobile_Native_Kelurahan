package com.example.mobile_native_kelurahan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Surat;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class form_pengajuan extends AppCompatActivity {
    Button btnKirim;
    ImageView uploadkk;
    Uri uri;
    Surat surat;
    TextInputLayout til_nama, til_nik, til_ket;
    TextInputEditText tiet_nama, tiet_nik, tiet_ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengajuan);

        btnKirim = findViewById(R.id.btnKirim);
        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(form_pengajuan.this, DaftarKeluarga.class);
                startActivity(intent);
                finish();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadkk.setImageURI(uri);
                        } else {
                            Toast.makeText(form_pengajuan.this, "Tidak Ada Foto Yang Dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadkk = findViewById(R.id.uploadkk);
        uploadkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        til_nik = findViewById(R.id.tx_NIK);
        til_nama = findViewById(R.id.tx_nama);
        til_ket = findViewById(R.id.tx_keperluan);
        tiet_ket = findViewById(R.id.et_keperluan);

        Intent intent = getIntent();
        String id_surat = intent.getStringExtra("id_surat");
        Log.e("gatau", id_surat);
        String nama = intent.getStringExtra("namaLengkap");
        String nik = intent.getStringExtra("nik");
        Log.e("nik", nik);
        Editable keterangan = til_ket.getEditText().getText();
        String keperluan = keterangan.toString().trim();
        til_nik.getEditText().setText(nik);
        til_nama.getEditText().setText(nama);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keperluan.isEmpty()){
                    til_ket.setError("Keperluan Harus diisi");
                } else {
                    AuthServices.pengajuan(form_pengajuan.this, nik, keperluan, id_surat, new AuthServices.PengajuanResponseListener() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Toast.makeText(form_pengajuan.this, response.toString(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(form_pengajuan.this, homeAdapter.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(form_pengajuan.this, message, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }
}