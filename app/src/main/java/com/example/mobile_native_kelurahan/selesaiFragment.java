package com.example.mobile_native_kelurahan;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link selesaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class selesaiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public selesaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment selesaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static selesaiFragment newInstance(String param1, String param2) {
        selesaiFragment fragment = new selesaiFragment();
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
    RecyclerView recyclerView;
    List<Status> statusList1 = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        recyclerView = view.findViewById(R.id.recyclerSelesai);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        AuthServices.selesai(getContext(), token, new AuthServices.StatusResponseListener() {
            @Override
            public void onSuccess(List<Status> statusList) {
                selesaiFragment.CustomAdapterStatus customAdapterStatus = new selesaiFragment.CustomAdapterStatus(statusList, getContext());
                recyclerView.setAdapter(customAdapterStatus);
                statusList1 = statusList;
            }

            @Override
            public void onError(String message) {
                Log.e("load error", message);
            }
        });
        return  view;
    }
    public static class CustomAdapterStatus extends RecyclerView.Adapter<selesaiFragment.CustomAdapterStatus.ViewHolder> {
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
        public selesaiFragment.CustomAdapterStatus.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_status_selesai, parent, false);
            return new selesaiFragment.CustomAdapterStatus.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull selesaiFragment.CustomAdapterStatus.ViewHolder holder, int position) {
            holder.namaLengkap.setText(statusList.get(position).getNamaLengkap());
            holder.nik.setText(statusList.get(position).getNik());
            holder.status.setText(statusList.get(position).getStatus());
            Glide.with(context).load(AuthServices.getIMAGE() + statusList.get(position).getImage()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, UnduhPdf.class);
                        intent.putExtra("file_pdf", statusList.get(pos).getFilePdf());
                        context.startActivity(intent);
                    }
                }
            });
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