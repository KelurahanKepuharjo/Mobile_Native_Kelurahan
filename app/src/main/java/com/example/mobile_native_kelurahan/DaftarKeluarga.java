package com.example.mobile_native_kelurahan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Berita;
import com.example.mobile_native_kelurahan.Model.Masyarakat;
import com.example.mobile_native_kelurahan.Model.Surat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DaftarKeluarga extends AppCompatActivity {

    Surat surat;
    RecyclerView recyclerView;
    List<Masyarakat> masyarakatList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_keluarga);
        recyclerView = findViewById(R.id.rKeluarga);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DaftarKeluarga.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();

        if (intent.getExtras() != null){
            surat = (Surat) intent.getSerializableExtra("data");
            String idSurat = surat.getIdSurat();
        }
        String tokkken = intent.getStringExtra("token");
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", "");
//        String token = DaftarKeluarga.this.getSharedPreferences("myPrefs", MODE_PRIVATE).getString("token", "");
        AuthServices.keluarga(DaftarKeluarga.this,token, new AuthServices.KeluargaResponseListener() {
            @Override
            public void onSuccess(List<Masyarakat> masyarakatList) {
                AdapterKeluarga adapterKeluarga = new AdapterKeluarga(masyarakatList, DaftarKeluarga.this);
                recyclerView.setAdapter(adapterKeluarga);
                masyarakatList1 = masyarakatList;
            }

            @Override
            public void onError(String message) {
                Log.e("Keluarga Error",message);
            }
        });
    }
    public static class AdapterKeluarga extends  RecyclerView.Adapter<DaftarKeluarga.AdapterKeluarga.Viewholder>{
        private List<Masyarakat> masyarakatList;
        private Context context;
        private LayoutInflater layoutInflater;

        public AdapterKeluarga(List<Masyarakat> masyarakatList, Context context) {
            this.masyarakatList = masyarakatList;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public DaftarKeluarga.AdapterKeluarga.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.rows_item_keluarga, parent, false);
            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DaftarKeluarga.AdapterKeluarga.Viewholder holder, int position) {
            holder.nama.setText(masyarakatList.get(position).getNamaLengkap());
        }

        @Override
        public int getItemCount() {
            return masyarakatList.size();
        }

        public static class Viewholder extends RecyclerView.ViewHolder{
            TextView nama;
            public Viewholder(@NonNull View itemView) {
                super(itemView);
                nama = itemView.findViewById(R.id.namaKeluarga);
            }
        }
    }

}