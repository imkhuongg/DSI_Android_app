package com.example.dsidemo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.models.product;
import com.android.volley.Request;

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
                                        APILinkHelper.getIMG() + respone.getString("thumb"),
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

}
