package com.example.mobile_native_kelurahan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UnduhPdf extends AppCompatActivity {

    PDFView pdfView;
    ImageView pdfdownload;
    private static final int PERMISSION_STORAGE_CODE = 1000;
    String url = AuthServices.getPDF();
    String pdf = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unduh_pdf);
        pdfView = findViewById(R.id.pdfviewer);
        pdfdownload = findViewById(R.id.download);
        Intent intent = getIntent();
        pdf = intent.getStringExtra("file_pdf");
        new UnduhPdf.RetrivePdf().execute( url + pdf);

        pdfdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UnduhPdf.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(UnduhPdf.this, permissions, PERMISSION_STORAGE_CODE);
                } else {
                    startDownload();
                }
            }
        });
    }

    private void startDownload() {
        String pdfUrl = url + pdf;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Mengunduh");
        request.setDescription("Mengundul file pdf surat....... ");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdf);

        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);
        Log.d("UnduhPdf", "Downloaded file with ID: " + downloadId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startDownload();
                } else {
                    Toast.makeText(UnduhPdf.this, "Permissions denied..." , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class RetrivePdf extends AsyncTask<String,Void, InputStream>{
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}