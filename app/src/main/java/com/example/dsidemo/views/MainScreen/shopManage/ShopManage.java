package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.recycleviews.productListRecycleAdapter;
import com.example.dsidemo.models.product;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopManage extends Fragment {

    private SharedPreferences preferences;
    private ProgressBar progressBar;
    private  productListRecycleAdapter productListRecycleAdapter;
    private  RecyclerView recyclerView;
    private List<product> productList;
    private TextView NoneProduct_txt;
    private RequestQueue requestQueue;
    private FloatingActionButton addbtn;
    private ImageView btn_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_shop_manage , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productList = new ArrayList<>();
        requestQueue = MySingleton.getInstance(view.getContext()).getRequestQueue();
        preferences = getActivity().getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);

        //RecyclerView
        recyclerView = view.findViewById(R.id.listProduct);
        //ProgressBar
        progressBar = view.findViewById(R.id.progressBar);
        //txt
        NoneProduct_txt = view.findViewById(R.id.NoneProduct_txt);
        //btn
        addbtn = view.findViewById(R.id.add_btn);
        //IMGView
        btn_back = view.findViewById(R.id.btn_back);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        helper.setTouchEffect(btn_back);

        getShopperProduct();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct addProduct = new addProduct();
                if (getActivity() instanceof MainScreen) {
                    ((MainScreen) getActivity()).replaceFragment(addProduct);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


    }

    public void getShopperProduct(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, APILinkHelper.getProduts(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                for(int i = 0 ; i< response.length() ;i++){
                    try {
                        JSONObject respone = response.getJSONObject(i);
                        product product =
                                new product(respone.getInt("product_id") , respone.getString("name_product"), respone.getDouble("price"),respone.getInt("user_id"), respone.getString("description"), respone.getDouble("rate"), respone.getString("name_brand"), APILinkHelper.getIMG() + respone.getString("thumb"), respone.getInt("quantity_sold"));

                        productList.add(product);


                    }catch (JSONException e){
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                productListRecycleAdapter = new productListRecycleAdapter(productList, getContext());
                recyclerView.setAdapter(productListRecycleAdapter);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                NoneProduct_txt.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity().getBaseContext(), "failed to load products", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token" , "");
                Map<String , String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("content-type", "application/json");
                return headers;

            }
        };
        requestQueue.add(jsonArrayRequest);

    }
}
