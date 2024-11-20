package com.example.dsidemo.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.models.product;
import com.android.volley.Request;
import com.example.dsidemo.views.MainScreen.shopManage.ShopManage;
import com.example.dsidemo.views.MainScreen.shopManage.itemProductDetailManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class productRepository {




    public productRepository() {}

    public LiveData<List<product>> getProductShopper(RequestQueue queue,String token,final RepositoryCallback callback) {
        MutableLiveData<List<product>> liveData = new MutableLiveData<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, APILinkHelper.getProduts(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<product> products = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject respone = response.getJSONObject(i);
                        product product =
                                new product(
                                        respone.getInt("product_id"),
                                        respone.getString("name_product"),
                                        respone.getDouble("price"),
                                        respone.getInt("user_id"),
                                        respone.getString("description"),
                                        respone.getDouble("rate"),
                                        respone.getString("name_brand"),
                                        respone.getString("thumb"),
                                        respone.getInt("quantity_sold"),
                                        respone.getString("created_at"),
                                        respone.getString("updated_at")
                                );

                        products.add(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(products);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("content-type", "application/json");
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
        return liveData;
    }
    public interface RepositoryCallback {
        void onSuccess(List<product> products);
        void onError(VolleyError error);
    }
    public interface StringCallback{
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }

    public int deleteProduct(String product_id, String token , RequestQueue requestQueue , final StringCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.deleteProduct(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("productDetail" , "product deleted");
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
                Map<String, String> params = new HashMap<>();
                params.put("product_id" , product_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();
                header.put("Authorization" , "Bearer " + token);
                return header;


            }
        };
        requestQueue.add(stringRequest);
        return 1;
    }
    public void updateProduct(String product_id, String name_product , String price , String description , String name_brand , String thumb , String token , RequestQueue requestQueue , final StringCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.updateProduct(), new Response.Listener<String>() {
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
                Map<String , String> params = new HashMap<>();
                params.put("name_product", name_product);
                params.put("price", price);
                params.put("description", description);
                params.put("name_brand", name_brand);
                params.put("thumb", thumb);
                params.put("product_id", product_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + token);
                return header;
            }
        };
    }

}
