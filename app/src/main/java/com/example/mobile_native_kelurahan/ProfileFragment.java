package com.example.mobile_native_kelurahan;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Masyarakat;
import com.example.mobile_native_kelurahan.Model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Button btn_logout;
    TextView btnprofileTertaut;
    RelativeLayout btnprof, editTelp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn_logout = view.findViewById(R.id.btn_logout);
        btnprofileTertaut = view.findViewById(R.id.profileTertaut);
        btnprof = view.findViewById(R.id.rlProfile);
        editTelp = view.findViewById(R.id.editTelp);

        // Memberikan listener pada button untuk menangani event klik
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE).getString("token", "");
                AuthServices.logOut(getContext(), token, new AuthServices.LogoutResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("isLogin");
                        editor.remove("token");
                        editor.apply();
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("LogOut Error", message);
                    }
                });

            }
        });
            SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
            String token = preferences.getString("token", "");
        AuthServices.getUserData(getContext(), token, new AuthServices.UserDataResponseListener() {
            @Override
            public void onSuccess(User user) {
                String nama = user.getMasyarakat().getNamaLengkap();
                String nik = user.getMasyarakat().getNik();
                String nohp = user.getPhoneNumber();
                String nokk = user.getNoKK();
                Log.e("nokk", nokk);
                TextView txt_namat = view.findViewById(R.id.tx_namaTop);
                TextView txt_nama = view.findViewById(R.id.tx_nama_user);
                TextView tct_nokk = view.findViewById(R.id.tx_kk_user);
                TextView txt_nik = view.findViewById(R.id.tx_nik_user);
                TextView txt_telp = view.findViewById(R.id.tx_nohp_user);
                txt_nama.setText(nama);
                tct_nokk.setText(nokk);
                txt_namat.setText(nama);
                txt_nik.setText(nik);
                txt_telp.setText(nohp);
            }

            @Override
            public void onError(String message) {
                Log.e("getUserData Error", message);
            }
        });
        btnprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileTertaut.class);
                startActivity(intent);
            }
        });
        editTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                View alertDialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_telp,
                        (RelativeLayout) v.findViewById(R.id.dialogTelp));
                alertDialog.setView(alertDialogView);
                final AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button btnBatal = alertDialogView.findViewById(R.id.btnBatal);
                Button btnSimpan = alertDialogView.findViewById(R.id.btnSimpan);
                TextView txnohp = alertDialogView.findViewById(R.id.telpBox);


                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noop = txnohp.getText().toString().trim();
                        Log.e("noho", noop);
                        if (noop.isEmpty()){
                            txnohp.setError("Nomor Telepon harus diisi");
                        } else if(noop.length() < 11){
                            txnohp.setError("Nomer Telepon tidak boleh kurang dari 10 digit");
                        } else {
                            AuthServices.updatenohp(getContext(), token, noop, new AuthServices.UpdateResponseListener() {
                                @Override
                                public void onSuccess(String response) {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                    AuthServices.getUserData(getContext(), token, new AuthServices.UserDataResponseListener() {
                                        @Override
                                        public void onSuccess(User user) {
                                            String nama = user.getMasyarakat().getNamaLengkap();
                                            String nik = user.getMasyarakat().getNik();
                                            String nohp = user.getPhoneNumber();
                                            String nokk = user.getNoKK();
                                            Log.e("nokk", nokk);
                                            TextView txt_namat = view.findViewById(R.id.tx_namaTop);
                                            TextView txt_nama = view.findViewById(R.id.tx_nama_user);
                                            TextView tct_nokk = view.findViewById(R.id.tx_kk_user);
                                            TextView txt_nik = view.findViewById(R.id.tx_nik_user);
                                            TextView txt_telp = view.findViewById(R.id.tx_nohp_user);
                                            txt_nama.setText(nama);
                                            tct_nokk.setText(nokk);
                                            txt_namat.setText(nama);
                                            txt_nik.setText(nik);
                                            txt_telp.setText(nohp);
                                        }

                                        @Override
                                        public void onError(String message) {
                                            Log.e("getUserData Error", message);
                                        }
                                    });
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }
}