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
    static String idSurat = "";
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
            idSurat = surat.getIdSurat();
            Log.e("gatau", idSurat);
        }
        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", "");
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
            holder.nik.setText(masyarakatList.get(position).getNik());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, form_pengajuan.class);
                        intent.putExtra("id_surat", idSurat);
                        intent.putExtra("id_masyarakat", masyarakatList.get(pos).getIdMasyarakat());
                        intent.putExtra("nik", masyarakatList.get(pos).getNik());
                        intent.putExtra("namaLengkap", masyarakatList.get(pos).getNamaLengkap());
                        intent.putExtra("jenisKelamin", masyarakatList.get(pos).getJenisKelamin());
                        intent.putExtra("tempatLahir", masyarakatList.get(pos).getTempatLahir());
                        intent.putExtra("tanggalLahir", masyarakatList.get(pos).getTglLahir());
                        intent.putExtra("agama", masyarakatList.get(pos).getAgama());
                        intent.putExtra("pendidikan", masyarakatList.get(pos).getPendidikan());
                        intent.putExtra("pekerjaan", masyarakatList.get(pos).getPekerjaan());
                        intent.putExtra("golonganDarah", masyarakatList.get(pos).getGolonganDarah());
                        intent.putExtra("statusPerkawinan", masyarakatList.get(pos).getStatusPerkawinan());
                        intent.putExtra("tglPerkawinan", masyarakatList.get(pos).getTglPerkawinan());
                        intent.putExtra("statusKeluarga", masyarakatList.get(pos).getStatusKeluarga());
                        intent.putExtra("kewarganegaraan", masyarakatList.get(pos).getKewarganegaraan());
                        intent.putExtra("noPaspor", masyarakatList.get(pos).getNoPaspor());
                        intent.putExtra("noKitap", masyarakatList.get(pos).getNoKitap());
                        intent.putExtra("namaAyah", masyarakatList.get(pos).getNamaAyah());
                        intent.putExtra("namaIbu", masyarakatList.get(pos).getNamaIbu());
                        intent.putExtra("id", masyarakatList.get(pos).getId());
                        context.startActivity(intent);
                    }
                }
            });
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