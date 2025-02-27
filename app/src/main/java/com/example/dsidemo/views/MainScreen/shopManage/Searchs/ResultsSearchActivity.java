package com.example.dsidemo.views.MainScreen.shopManage.Searchs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.dsidemo.R;
import com.example.dsidemo.ViewModel.ShopManageViewModel;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.recycleviews.ProductListRecycleAdapter;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.MainScreen.shopManage.addProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ResultsSearchActivity extends AppCompatActivity {
    private Button btn_search;
    private ImageView btn_back;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private RecyclerView list_product;
    private ShopManageViewModel shopManageViewModel;
    private FloatingActionButton btn_add;
    private ProgressBar progressBar;
    private ProductListRecycleAdapter productListRecycleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shop_manage_result_search_product);


        btn_search = findViewById(R.id.btn_search);
        btn_back = findViewById(R.id.btn_back);
        list_product = findViewById(R.id.list_product);
        btn_add = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progressBar);

        list_product.setHasFixedSize(true);
        list_product.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        helper.hideSystemUI(getWindow().getDecorView());

        btn_add.setOnClickListener(v-> GotoAdd());
        btn_back.setOnClickListener(v ->finish());
        btn_search.setOnClickListener(v->GotoSearch());
        getShopperProduct();


    }


    public void GotoSearch(){
        Intent intent = new Intent(ResultsSearchActivity.this, SearchProductActivity.class);
        startActivity(intent);
    }
    public void GotoAdd(){
        Intent intent = new Intent(ResultsSearchActivity.this, addProduct.class);
        startActivity(intent);
    }
    public void getShopperProduct(){

        Intent intent = getIntent();
        String search = intent.getStringExtra("search");

        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        list_product.setLayoutManager(new LinearLayoutManager(this));
        String token = sharedPreferences.getString("token" , "");
        shopManageViewModel = new ViewModelProvider(this).get(ShopManageViewModel.class);
        shopManageViewModel.getProductShopManage(requestQueue , token, APILinkHelper.SearchResult() + search);
        productListRecycleAdapter = new ProductListRecycleAdapter(new ArrayList<>() ,this,shopManageViewModel);

        shopManageViewModel.getProducts().observe(this,products -> {
            productListRecycleAdapter.setProductList(products);
        } );
        progressBar.setVisibility(View.GONE);
        list_product.setAdapter(productListRecycleAdapter);
        list_product.setVisibility(View.VISIBLE);
    }

}


