package com.example.dsidemo.views.MainScreen.Component;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.recycleviews.ProductAllRecycleAdapter;
import com.example.dsidemo.models.product;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.MainScreen.profiles.UserPorfolio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView productList;

    private RequestQueue requestQueue;

    private List<product> products = new ArrayList<>();
    private ProductAllRecycleAdapter productAllRecycleAdapter;
    private TextView txt_noProduct;
    private ProgressBar loading;
    private ImageView btn_porfolio;
    private ScrollView scroll_products;
    private FloatingActionButton btn_cart;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = MySingleton.getInstance(view.getContext()).getRequestQueue();
        getAllProduct();

        txt_noProduct = view.findViewById(R.id.txt_noMoreProducts);
        productList = view.findViewById(R.id.ctn_products);
        loading = view.findViewById(R.id.loading);
        btn_porfolio = view.findViewById(R.id.btn_porfolio);
        scroll_products = view.findViewById(R.id.scroll_products);
        btn_cart = view.findViewById(R.id.btn_cart);

        helper.setAlphaScrollView(scroll_products, btn_cart);

        productList.setNestedScrollingEnabled(false);
        productList.setHasFixedSize(true);



        productAllRecycleAdapter = new ProductAllRecycleAdapter(view.getContext() , products);
        productAllRecycleAdapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        productList.setLayoutManager(gridLayoutManager);
        productList.setAdapter(productAllRecycleAdapter);
        productList.requestLayout();

        loading.setVisibility(View.GONE);




        btn_porfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserPorfolio.class);
                startActivity(intent);
            }
        });


    }

    public void getAllProduct() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, APILinkHelper.getAllProduct(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

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
                                        respone.getInt("shopper_id"),
                                        respone.getString("created_at"),
                                        respone.getString("updated_at")
                                );
                        products.add(product);
                        productAllRecycleAdapter.notifyDataSetChanged();
                        productList.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt_noProduct.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
