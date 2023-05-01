package com.example.mobile_native_kelurahan;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Berita;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        AuthServices.berita(getContext(), new AuthServices.BeritaResponseListener() {
            @Override
            public void onSuccess(List<Berita> beritaList) {
                String judul =  beritaList.get(1).getJudul();
                String subtitle = beritaList.get(2).getSubTitle();
//                String deskripsi = beritaList.get(3).getDeskripsi();

                Txtjudul = view.findViewById(R.id.judulBerita);
                Txtjudul.setText(judul);

                Txtsub = view.findViewById(R.id.subtitle);
                Txtsub.setText(subtitle);
            }

            @Override
            public void onError(String message) {
                Log.e("Berita Error",message);
            }
        });
                return view;
    }
}