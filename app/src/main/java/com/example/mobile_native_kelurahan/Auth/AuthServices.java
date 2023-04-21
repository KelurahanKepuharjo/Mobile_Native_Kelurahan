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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthServices {
    private static RequestQueue requestQueue;

    public static void getUserData(String token, Context context, final AuthResponseHandler authResponseHandler) {
        String BASEURL = "http://192.168.137.1:8000/api/auth/me";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Authorization", "Bearer " + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASEURL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                authResponseHandler.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                authResponseHandler.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        requestQueue.add(jsonObjectRequest);
    }

    public interface AuthResponseHandler {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }


    private static String URL = "http://192.168.43.199:8000/api/auth/";


    public interface RegisterResponseListener {
        void onSuccess(JSONObject response);
        void onError(String message);
    }
    public interface LoginResponseListener {
        void onSuccess(JSONObject response);
        void onError(String message);
        void onTokenReceived(String token);
    }

    public static void register(Context context, String nik, String pass, String no_tlp, RegisterResponseListener listener) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"register", new Response.Listener<String>() {
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("Berhasil Login")){
                        JSONObject userObj = jsonObject.getJSONObject("user");
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
}
