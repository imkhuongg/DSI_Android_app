package com.example.dsidemo.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.models.Shopper;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ShopperRepository(){}
    public int CheckShopper(String token , RequestQueue requestQueue, final StringCallback callback){
        final int[] check = {0};

       StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.CheckShopper(), new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   check[0] = jsonObject.getInt("isShopper");
               } catch (JSONException e) {
                   throw new RuntimeException(e);
               }

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
        requestQueue.add(stringRequest);
        return check[0];
    }

    public Shopper getShopper(String token , RequestQueue requestQueue , final ObjectCallback callback){
        final Shopper[] shopper = new Shopper[1];

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APILinkHelper.getShopper(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    shopper[0] = new Shopper(response.getInt("shopper_id"),
                            response.getInt("user_id"),
                            response.getString("avatar"),
                            response.getString("name_shop"),
                            response.getInt("phone"),
                            response.getString("email"),
                            response.getString("shop_address"),
                            response.getInt("follower"),
                            response.getInt("total_sold"),
                            BigDecimal.valueOf(response.getLong("total_revenue")),
                            response.getString("created_at"),
                            response.getString("updated_at")
                            );
                }catch (JSONException e){
                    e.printStackTrace();
                }
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
        return shopper[0];
    }

    public void ShopperRegistration(String name_shop, String email, String avatar,int phone,String shop_address,String token, RequestQueue requestQueue , StringCallback stringCallback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.ShopperRegistration(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stringCallback.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stringCallback.onErrorResponse(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String ,String> params = new HashMap<>();


                return params;
            }
        };
    }

}
