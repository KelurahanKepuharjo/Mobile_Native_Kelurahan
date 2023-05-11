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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Status;

import java.util.ArrayList;
import java.util.List;

public class suratDibatalkan extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Status> statusList1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat_dibatalkan);

        recyclerView = findViewById(R.id.recyclerDibatalkan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences preferences = suratDibatalkan.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        AuthServices.status(getApplicationContext(), token, "Dibatalkan", new AuthServices.StatusResponseListener() {
            @Override
            public void onSuccess(List<Status> statusList) {
                suratDibatalkan.CustomAdapterStatus customAdapterStatus = new suratDibatalkan.CustomAdapterStatus(statusList, getApplicationContext());
                recyclerView.setAdapter(customAdapterStatus);
                statusList1 = statusList;
            }

            @Override
            public void onError(String message) {
                Log.e("load error", message);
            }
        });
    }
    public static class CustomAdapterStatus extends RecyclerView.Adapter<suratDibatalkan.CustomAdapterStatus.ViewHolder> {
        private List<Status> statusList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapterStatus(List<Status> statusList, Context context) {
            this.statusList = statusList;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public suratDibatalkan.CustomAdapterStatus.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_status_dibatalkan, parent, false);
            return new suratDibatalkan.CustomAdapterStatus.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull suratDibatalkan.CustomAdapterStatus.ViewHolder holder, int position) {
            holder.namaLengkap.setText(statusList.get(position).getNamaLengkap());
            holder.nik.setText(statusList.get(position).getNik());
            holder.status.setText(statusList.get(position).getStatus());
            Glide.with(context).load(AuthServices.getIMAGE() + statusList.get(position).getImage()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return statusList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView namaLengkap,nik,status;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                namaLengkap = itemView.findViewById(R.id.tx_namaLengkap);
                nik = itemView.findViewById(R.id.tx_NIK);
                status = itemView.findViewById(R.id.statusSurat);
                imageView= itemView.findViewById(R.id.logoSurat);
            }
        }
    }
}
