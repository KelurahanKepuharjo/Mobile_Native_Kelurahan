package com.example.mobile_native_kelurahan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Surat;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuratFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuratFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuratFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LikesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuratFragment newInstance(String param1, String param2) {
        SuratFragment fragment = new SuratFragment();
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

    GridView gridview;
    List<Surat> suratList1 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surat, container, false);
        gridview = view.findViewById(R.id.gridViewSurat);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (suratList1.size() > position) {
                    startActivity(new Intent(getActivity(), form_pengajuan.class).putExtra("data", suratList1.get(position)));
                } else {
                    Log.e("Errorbang", "List kosong");
                    Toast.makeText(getActivity(), "List surat kosong atau tidak ada item pada posisi ini", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AuthServices.surat(getContext(), new AuthServices.SuratResponseListener() {
            @Override
            public void onSuccess(List<Surat> suratList) {
                CustomAdapter customAdapter = new CustomAdapter(suratList, getContext());
                gridview.setAdapter(customAdapter);
                suratList1 = suratList;
            }

            @Override
            public void onError(String message) {
                Log.e("Failed load" , message);
            }
        });
        return view;
    }
    public static class CustomAdapter extends BaseAdapter{

        private List<Surat> suratList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<Surat> suratList, Context context) {
            this.suratList = suratList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        }

        @Override
        public int getCount() {
            return suratList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.row_grid_items,parent,false);
            }
            ImageView image = convertView.findViewById(R.id.logoSurat);
            TextView textView = convertView.findViewById(R.id.namaSurat);
            textView.setText(suratList.get(position).getNamaSurat());
            Glide.with(context).load("http://192.168.0.117:8000/images/"+suratList.get(position).getImage()).into(image);
            return convertView;
        }
    }
}