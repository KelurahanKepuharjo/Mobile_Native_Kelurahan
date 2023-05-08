package com.example.mobile_native_kelurahan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.example.mobile_native_kelurahan.Model.Masyarakat;

import java.util.ArrayList;
import java.util.List;

public class ProfileTertaut extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Masyarakat> masyarakatList1 = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tertaut);
        recyclerView = findViewById(R.id.recyclerProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProfileTertaut.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        AuthServices.keluarga(ProfileTertaut.this,token, new AuthServices.KeluargaResponseListener() {
            @Override
            public void onSuccess(List<Masyarakat> masyarakatList) {
                ProfileTertaut.AdapterKeluarga adapterKeluarga = new ProfileTertaut.AdapterKeluarga(masyarakatList, ProfileTertaut.this);
                recyclerView.setAdapter(adapterKeluarga);
                masyarakatList1 = masyarakatList;
            }

            @Override
            public void onError(String message) {
                Log.e("Keluarga Error",message);
            }
        });
    }
    public static class AdapterKeluarga extends  RecyclerView.Adapter<ProfileTertaut.AdapterKeluarga.Viewholder>{
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
        public ProfileTertaut.AdapterKeluarga.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.rows_item_keluarga, parent, false);
            return new ProfileTertaut.AdapterKeluarga.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileTertaut.AdapterKeluarga.Viewholder holder, int position) {
            holder.nama.setText(masyarakatList.get(position).getNamaLengkap());
            holder.nik.setText(masyarakatList.get(position).getNik());
        }

        @Override
        public int getItemCount() {
            return masyarakatList.size();
        }

        public static class Viewholder extends RecyclerView.ViewHolder{
            TextView nama,nik;
            public Viewholder(@NonNull View itemView) {
                super(itemView);
                nama = itemView.findViewById(R.id.namaKeluarga);
                nik = itemView.findViewById(R.id.nikKeluarga);
            }
        }
    }
}