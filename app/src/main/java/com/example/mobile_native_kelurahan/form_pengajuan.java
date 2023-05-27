package com.example.mobile_native_kelurahan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class form_pengajuan extends AppCompatActivity {
    Button btnKirim;
    ImageView uploadkk,uploadBukti;
    Uri uri;
    Surat surat;
    TextInputLayout til_nama, til_nik, til_jenisKelamin,til_tempatTanggalLahir,til_kewarganegaraan,til_agama,til_pendidikan,til_rw,til_rt, til_statusPerkawinan,til_statusKeluarga,til_noKitap,til_golonganDarah,til_noPaspor,til_pekerjaan,til_alamat,til_namaAyah,til_namaIbu, til_ket;
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
                            uploadBukti.setImageURI(uri);
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
        uploadBukti = findViewById(R.id.uploadBukti);
        uploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        til_nik = findViewById(R.id.tx_NIK);
        til_nama = findViewById(R.id.tx_nama);
        til_jenisKelamin = findViewById(R.id.tx_jenisKelamin);
        til_agama = findViewById(R.id.tx_agama);
        til_kewarganegaraan = findViewById(R.id.tx_kewarganegaraan);
        til_statusPerkawinan = findViewById(R.id.tx_statusPerkawinan);
        til_statusKeluarga = findViewById(R.id.tx_statusKeluarga);
        til_pekerjaan = findViewById(R.id.tx_pekerjaan);
        til_alamat = findViewById(R.id.tx_alamat);
        til_tempatTanggalLahir = findViewById(R.id.tx_ttl);
        til_golonganDarah = findViewById(R.id.tx_golDarah);
        til_pendidikan = findViewById(R.id.tx_pendidikan);
        til_noKitap = findViewById(R.id.tx_noKitap);
        til_noPaspor = findViewById(R.id.tx_noPaspor);
        til_namaAyah = findViewById(R.id.tx_namaAyah);
        til_namaIbu = findViewById(R.id.tx_namaIbu);
        til_ket = findViewById(R.id.tx_keperluan);
        tiet_ket = findViewById(R.id.et_keperluan);

        Intent intent = getIntent();
        String id_surat = intent.getStringExtra("id_surat");
        Log.e("gatau", id_surat);
        String nama = intent.getStringExtra("namaLengkap");
        String nik = intent.getStringExtra("nik");
        Log.e("nik", nik);
        String jenisKelamin = intent.getStringExtra("jenisKelamin");
        String agama = intent.getStringExtra("agama");
        String kewarganegaraan = intent.getStringExtra("kewarganegaraan");
        String statusPerkawinan = intent.getStringExtra("statusPerkawinan");
        String statusKeluarga = intent.getStringExtra("statusKeluarga");
        String pekerjaan = intent.getStringExtra("pekerjaan");
        String alamat = intent.getStringExtra("alamat");
        String golonganDarah = intent.getStringExtra("golonganDarah");
        String pendidikan = intent.getStringExtra("pendidikan");
        String noKitap = intent.getStringExtra("noKitap");
        String noPaspor = intent.getStringExtra("noPaspor");
        String namaAyah = intent.getStringExtra("namaAyah");
        String namaIbu = intent.getStringExtra("namaIbu");
        String tempatLahir = intent.getStringExtra("tempatLahir");
        String tanggalLahir = intent.getStringExtra("tanggalLahir");
        String tempatTanggalLahir = tempatLahir + ", " + tanggalLahir;
        Editable keterangan = til_ket.getEditText().getText();
        til_nik.getEditText().setText(nik);
        til_nama.getEditText().setText(nama);
        til_jenisKelamin.getEditText().setText(jenisKelamin);
        til_agama.getEditText().setText(agama);
        til_kewarganegaraan.getEditText().setText(kewarganegaraan);
        til_statusPerkawinan.getEditText().setText(statusPerkawinan);
        til_statusKeluarga.getEditText().setText(statusKeluarga);
        til_pekerjaan.getEditText().setText(pekerjaan);
        til_alamat.getEditText().setText(alamat);
        til_golonganDarah.getEditText().setText(golonganDarah);
        til_pendidikan.getEditText().setText(pendidikan);
        til_noKitap.getEditText().setText(noKitap);
        til_noPaspor.getEditText().setText(noPaspor);
        til_namaAyah.getEditText().setText(namaAyah);
        til_namaIbu.getEditText().setText(namaIbu);
        til_tempatTanggalLahir.getEditText().setText(tempatTanggalLahir);


        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(keterangan).isEmpty()) {
                    til_ket.setError("Keperluan Harus diisi");
                } else if (uploadkk.getDrawable() == null) {
                    Toast.makeText(form_pengajuan.this, "Unggah KK harus dilampirkan", Toast.LENGTH_LONG).show();
                } else if (uploadBukti.getDrawable() == null) {
                    Toast.makeText(form_pengajuan.this, "Unggah Bukti harus dilampirkan", Toast.LENGTH_LONG).show();
                } else {
                    // Mengubah gambar menjadi file
                    File fileKK = new File(getCacheDir(), "uploadkk.jpg");
                    File fileBukti = new File(getCacheDir(), "uploadbukti.jpg");
                    try {
                        FileOutputStream outputStreamKK = new FileOutputStream(fileKK);
                        FileOutputStream outputStreamBukti = new FileOutputStream(fileBukti);
                        BitmapDrawable drawableKK = (BitmapDrawable) uploadkk.getDrawable();
                        BitmapDrawable drawableBukti = (BitmapDrawable) uploadBukti.getDrawable();
                        Bitmap bitmapKK = drawableKK.getBitmap();
                        Bitmap bitmapBukti = drawableBukti.getBitmap();
                        bitmapKK.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamKK);
                        bitmapBukti.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamBukti);
                        outputStreamKK.flush();
                        outputStreamBukti.flush();
                        outputStreamKK.close();
                        outputStreamBukti.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Membuat permintaan pengajuan menggunakan OkHttp
                    String url = AuthServices.getURL() + "uploadfoto"; // Sesuaikan dengan URL backend Anda

                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("keterangan", String.valueOf(keterangan))
                            .addFormDataPart("uploadkk", fileKK.getName(), RequestBody.create(MediaType.parse("image/jpeg"), fileKK))
                            .addFormDataPart("uploadbukti", fileBukti.getName(), RequestBody.create(MediaType.parse("image/jpeg"), fileBukti))
                            .build();

                    Request.Builder requestBuilder = new Request.Builder()
                            .url(url)
                            .post(requestBody);

                    SharedPreferences preferences = form_pengajuan.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                    String token = preferences.getString("token", "");

                    requestBuilder.addHeader("Authorization", "Bearer " + token);

                    Request request = requestBuilder.build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            // Gagal mengirim permintaan HTTP
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(form_pengajuan.this, "Gagal mengirim permintaan", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                // Berhasil mengajukan pengajuan
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(form_pengajuan.this, "Pengajuan Surat Anda Berhasil", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(form_pengajuan.this, homeAdapter.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                // Gagal mengajukan pengajuan
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(form_pengajuan.this, "Pengajuan Surat Gagal", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
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