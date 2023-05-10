package com.example.mobile_native_kelurahan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Berita;
import com.example.mobile_native_kelurahan.Model.Surat;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    CardView cardTidakMampu;
    CardView cardlainnya;
    TextView Txtjudul,Txtsub;
    RecyclerView ryView;
    List<Berita> beritaList1 = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ryView = view.findViewById(R.id.rycycleBerita);
        cardTidakMampu = view.findViewById(R.id.cardTidakMampu);
        cardlainnya = view.findViewById(R.id.cardLainnya);

        cardTidakMampu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), form_pengajuan.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        cardlainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment suratfrag = new SuratFragment();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.fragmentContainer,suratfrag).commit();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ryView.setLayoutManager(layoutManager);
        AuthServices.berita(getContext(), new AuthServices.BeritaResponseListener() {
            @Override
            public void onSuccess(List<Berita> beritaList) {
                CustomAdapterBerita customAdapter = new CustomAdapterBerita(beritaList, getContext());
                ryView.setAdapter(customAdapter);
                beritaList1 = beritaList;
            }

            @Override
            public void onError(String message) {
                Log.e("Berita Error",message);
            }
        });
                return view;
    }
    public static class CustomAdapterBerita extends RecyclerView.Adapter<CustomAdapterBerita.ViewHolder> {
        private List<Berita> beritaList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapterBerita(List<Berita> beritaList, Context context) {
            this.beritaList = beritaList;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.row_list_item_berita, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.judulTextView.setText(beritaList.get(position).getJudul());
            holder.subtitleTextView.setText(beritaList.get(position).getSubTitle());
            Glide.with(context).load(AuthServices.getIMAGE() + beritaList.get(position).getImage()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailBerita.class);
                        intent.putExtra("judulBerita", beritaList.get(pos).getJudul());
                        intent.putExtra("subtitle", beritaList.get(pos).getSubTitle());
                        intent.putExtra("deskripsi", beritaList.get(pos).getDeskripsi());
                        intent.putExtra("image", beritaList.get(pos).getImage());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return beritaList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView judulTextView,subtitleTextView;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageBerita);
                judulTextView = itemView.findViewById(R.id.judulBerita);
                subtitleTextView = itemView.findViewById(R.id.subtitleBerita);
            }
        }
    }

}