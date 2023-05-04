package com.example.mobile_native_kelurahan;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobile_native_kelurahan.Auth.AuthServices;
import com.example.mobile_native_kelurahan.Model.Masyarakat;
import com.example.mobile_native_kelurahan.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn_logout = view.findViewById(R.id.btn_logout);

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
                String nik = user.getMasyarakat().getNik();
                String nohp = user.getPhoneNumber();
                TextView textView4 = view.findViewById(R.id.textView4);
                TextView txt_hohp = view.findViewById(R.id.txt_nohp);

                textView4.setText(nik);
                txt_hohp.setText(nohp);
            }

            @Override
            public void onError(String message) {
                Log.e("getUserData Error", message);
            }
        });
        return view;
    }
}