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
    private final Context context;

    private final String token;


    public productRepository(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public LiveData<List<product>> getProductShopper() {
        MutableLiveData<List<product>> liveData = new MutableLiveData<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, APILinkHelper.getProduts(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<product> products = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject respone = response.getJSONObject(i);
                        product product =
                                new product(respone.getInt("product_id"), respone.getString("name_product"), respone.getDouble("price"), respone.getInt("user_id"), respone.getString("description"), respone.getDouble("rate"), respone.getString("name_brand"), APILinkHelper.getIMG() + respone.getString("thumb"), respone.getInt("quantity_sold"));

                        products.add(product);

                    } catch (JSONException e) {
                        Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                liveData.setValue(products);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "failed to load products", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(jsonArrayRequest);
        return liveData;
    }

}
