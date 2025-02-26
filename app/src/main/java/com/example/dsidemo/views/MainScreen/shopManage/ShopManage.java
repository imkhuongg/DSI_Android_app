package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.dsidemo.R;
import com.example.dsidemo.ViewModel.ShopManageViewModel;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.recycleviews.ProductListRecycleAdapter;
import com.example.dsidemo.models.product;
import com.example.dsidemo.utils.MySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShopManage extends AppCompatActivity {

    private SharedPreferences preferences;
    private ProgressBar progressBar;
    private ProductListRecycleAdapter productListRecycleAdapter;
    private RecyclerView recyclerView;
    private List<product> productList;
    private TextView NoneProduct_txt;
    private RequestQueue requestQueue;
    private FloatingActionButton addbtn,btn_chat;
    private ImageView btn_back , btn_ShopPorfolio;
    private ShopManageViewModel  ShopManageViewModel;
    private TextView quantitySold, Txt_Profits,txt_follower,txt_quantityRate;
    private ScrollView scroll;
    private Button btn_coupon,btn_productConfirm,btn_statusProduct,btn_support;



    private ShopManageViewModel shopManageViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_shop_manage);

    productList = new ArrayList<>();
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);


        //RecyclerView
        recyclerView = findViewById(R.id.listProduct);
        scroll = findViewById(R.id.scroll);
        //ProgressBar
        progressBar = findViewById(R.id.progressBar);
        //txt
        NoneProduct_txt = findViewById(R.id.NoneProduct_txt);
        quantitySold = findViewById(R.id.quantitySold);
        Txt_Profits = findViewById(R.id.Txt_Profits);
        txt_follower = findViewById(R.id.txt_follower);
        txt_quantityRate = findViewById(R.id.txt_quantityRate);
        //btn
        addbtn = findViewById(R.id.add_btn);
        btn_chat = findViewById(R.id.btn_chat);
        btn_coupon = findViewById(R.id.btn_coupon);
        btn_productConfirm = findViewById(R.id.btn_productConfirm);
        btn_statusProduct = findViewById(R.id.btn_statusProduct);
        btn_support = findViewById(R.id.btn_support);

        //IMGView
        btn_back = findViewById(R.id.btn_back);
        btn_ShopPorfolio = findViewById(R.id.btn_ShopPorfolio);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        helper.setTouchEffect(btn_back);
        helper.hideSystemUI(getWindow().getDecorView());
        helper.setAlphaScrollView(scroll,btn_chat);
        helper.setAlphaScrollView(scroll,addbtn);

        quantitySold.setText(String.valueOf(sharedPreferences.getInt("total_sold",0)));
        Txt_Profits.setText(String.valueOf(sharedPreferences.getLong("total_revenue",0)));
        txt_follower.setText(String.valueOf(sharedPreferences.getInt("follower",0)));


        getShopperProduct();


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManage.this , addProduct.class);
                startActivity(intent);
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_ShopPorfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopManage.this , ShopProfile.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void getShopperProduct(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String token = preferences.getString("token" , "");
        shopManageViewModel = new ViewModelProvider(this).get(ShopManageViewModel.class);
        shopManageViewModel.getProductShopManage(requestQueue , token);
        productListRecycleAdapter = new ProductListRecycleAdapter(new ArrayList<>() ,this,shopManageViewModel);

        shopManageViewModel.getProducts().observe(this,products -> {
            productListRecycleAdapter.setProductList(products);
        } );
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(productListRecycleAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void clearCache() {
        try {
            File cacheDir = getCacheDir();
            if (deleteDir(cacheDir)) {

            } else {
                Toast.makeText(this, "Failed to clear cache", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Error clearing cache", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        }
        return false;
    }
}
