package com.example.mobile_native_kelurahan.Auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_native_kelurahan.Model.Berita;
import com.example.mobile_native_kelurahan.Model.Masyarakat;
import com.example.mobile_native_kelurahan.Model.Surat;
import com.example.mobile_native_kelurahan.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthServices {
    private static String URL = "http://192.168.0.117:8000/api/auth";

    public interface RegisterResponseListener {
        void onSuccess(JSONObject response);
        void onError(String message);
    }
    public interface LoginResponseListener {
        void onSuccess(JSONObject response);
        void onError(String message);
        void onTokenReceived(String token);
    }
    public interface UserDataResponseListener {
        void onSuccess(User user);
        void onError(String message);
    }
    public interface LogoutResponseListener {
        void onSuccess(String message);
        void onError(String message);
    }
    public interface BeritaResponseListener {
        void onSuccess(List<Berita> beritaList);
        void onError(String message);
    }
    public interface SuratResponseListener {
        void onSuccess(List<Surat> suratList);
        void onError(String message);
    }


    public static void register(Context context, String nik, String pass, String no_tlp, RegisterResponseListener listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("Berhasil Register")){
                        JSONObject userObj = jsonObject.getJSONObject("user");
                        listener.onSuccess(userObj);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                if (message.equals("Akun sudah terdaftar")) {
                                    listener.onError("Akun sudah terdaftar");
                                } else if (message.equals("Nik anda belum terdaftar")) {
                                    listener.onError("Nik anda belum terdaftar");
                                }
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                listener.onError("Gagal register: " + e.getMessage());
                            }
                        }else{
                            listener.onError("Gagal register: network response is null");
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("password", pass);
                params.put("no_hp", no_tlp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void login(Context context, String nik, String pass, LoginResponseListener listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("Berhasil login")){
                        JSONObject userObj = jsonObject.getJSONObject("data");
                        String token = jsonObject.getString("token");
                        listener.onSuccess(userObj);
                        listener.onTokenReceived(token);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                if (message.equals("Nik Anda Belum Terdaftar")) {
                                    listener.onError("Nik Anda Belum Terdaftar");
                                } else if (message.equals("Password Anda Salah")) {
                                    listener.onError("Password Anda Salah");
                                }
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                listener.onError("Gagal Login: " + e.getMessage());
                            }
                        }else{
                            listener.onError("Gagal Login: network response is null");
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void getUserData(Context context, String token, final UserDataResponseListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/me",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if (message.equals("success")){
                                JSONObject userObj = jsonObject.getJSONObject("data");
                                String id = userObj.getString("id");
                                String password = userObj.getString("password");
                                String phoneNumber = userObj.getString("no_hp");
                                String role = userObj.getString("role");
                                JSONObject masyarakatObj = userObj.getJSONObject("masyarakat");
                                Masyarakat masyarakat = new Masyarakat(
                                        masyarakatObj.getString("id_masyarakat"),
                                        masyarakatObj.getString("nik"),
                                        masyarakatObj.getString("nama_lengkap"),
                                        masyarakatObj.getString("jenis_kelamin"),
                                        masyarakatObj.getString("tempat_lahir"),
                                        masyarakatObj.getString("tgl_lahir"),
                                        masyarakatObj.getString("agama"),
                                        masyarakatObj.getString("pendidikan"),
                                        masyarakatObj.getString("pekerjaan"),
                                        masyarakatObj.getString("golongan_darah"),
                                        masyarakatObj.getString("status_perkawinan"),
                                        masyarakatObj.getString("tgl_perkawinan"),
                                        masyarakatObj.getString("status_keluarga"),
                                        masyarakatObj.getString("kewarganegaraan"),
                                        masyarakatObj.getString("no_paspor"),
                                        masyarakatObj.getString("no_kitap"),
                                        masyarakatObj.getString("nama_ayah"),
                                        masyarakatObj.getString("nama_ibu"),
                                        masyarakatObj.getString("id")
                                );
                                User user = new User(id, password, phoneNumber, role, masyarakat);
                                listener.onSuccess(user);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                listener.onError(message);
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                listener.onError("Gagal mendapatkan data user: " + e.getMessage());
                            }
                        } else {
                            listener.onError("Gagal mendapatkan data user: network response is null");
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void logOut(Context context, String token, LogoutResponseListener listener) {
        StringRequest request = new StringRequest(Request.Method.POST, URL + "/logout", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public static void berita(Context context, final BeritaResponseListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/berita",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if (message.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                List<Berita> beritaList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject beritaObj = jsonArray.getJSONObject(i);
                                    String id = beritaObj.getString("id");
                                    String judul = beritaObj.getString("judul");
                                    String subTitle = beritaObj.getString("sub_title");
                                    String deskripsi = beritaObj.getString("deskripsi");
                                    String image = beritaObj.getString("image");
                                    Berita berita = new Berita(id, judul, subTitle, deskripsi,image);
                                    beritaList.add(berita);
                                }
                                listener.onSuccess(beritaList);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                listener.onError(message);
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                listener.onError("Gagal mendapatkan berita: " + e.getMessage());
                            }
                        } else {
                            listener.onError("Gagal mendapatkan berita: network response is null");
                        }
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void surat(Context context, final SuratResponseListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/surat",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if (message.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                List<Surat> suratList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject suratObj = jsonArray.getJSONObject(i);
                                    String idSurat = suratObj.getString("id_surat");
                                    String namaSurat = suratObj.getString("nama_surat");
                                    String image = suratObj.getString("image");
                                    Surat surat = new Surat(idSurat, namaSurat,image);
                                    suratList.add(surat);
                                }
                                listener.onSuccess(suratList);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String message = jsonObject.getString("message");
                                listener.onError(message);
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                listener.onError("Gagal mendapatkan berita: " + e.getMessage());
                            }
                        } else {
                            listener.onError("Gagal mendapatkan berita: network response is null");
                        }
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
