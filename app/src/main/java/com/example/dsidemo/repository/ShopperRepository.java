package com.example.dsidemo.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.models.Shopper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class ShopperRepository {
    private SharedPreferences sharedPreferences;
    public interface ObjectListCallback {
        void onSuccess(List<Shopper> shoppers);
        void onError(VolleyError error);
    } public interface ObjectCallback {
        void onSuccess(JSONObject shoppers);
        void onError(VolleyError error);
    }

    public interface StringCallback{
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }
    public interface IntCallback{
        void onResponse(int response);
        void onErrorResponse(VolleyError error);
    }
    public interface ArrayListCallback{
        void onResponse(JSONArray response);
        void onErrorResponse(VolleyError error);
    }

    public ShopperRepository(){}
    public void CheckShopper(String token , RequestQueue requestQueue, final IntCallback callback){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APILinkHelper.CheckShopper(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int check = response.getInt("isShopper");

                       callback.onResponse(check);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onErrorResponse(error);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    headers.put("content-type", "application/json");
                    return headers;
                }
            };
        requestQueue.add(jsonObjectRequest);

    }

    public void getShopper(String token , RequestQueue requestQueue , final ObjectCallback callback){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APILinkHelper.Shopper(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    public void ShopperRegistration(String token,String name_shop,String email,String phone,String shop_address, RequestQueue requestQueue, final StringCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.ShopperRegistration(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name_shop",name_shop);
                params.put("email", email);
                params.put("phone",phone);
                params.put("shop_address",shop_address);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void UpdateAvatar(String token, String avatar, RequestQueue requestQueue, StringCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.ShopperUpdateAvatar(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("avatar", avatar);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void updateShopper(String token,String name_shop, String email, int phone, String shop_Address, RequestQueue requestQueue, ObjectCallback callback){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name_shop", name_shop);
            jsonBody.put("email", email);
            jsonBody.put("phone", phone);
            jsonBody.put("shop_address", shop_Address);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, APILinkHelper.Shopper(), jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void getHintList(String token,String name_product, RequestQueue requestQueue, ArrayListCallback callback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, APILinkHelper.ShopperSearchHints() + name_product, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

}
