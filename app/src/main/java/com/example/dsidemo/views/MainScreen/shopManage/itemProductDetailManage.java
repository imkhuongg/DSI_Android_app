package com.example.dsidemo.views.MainScreen.shopManage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.dsidemo.R;
import com.example.dsidemo.ViewModel.ShopManageViewModel;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.repository.productRepository;
import com.example.dsidemo.utils.MySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class itemProductDetailManage extends AppCompatActivity {
    private TextView txt_nameProduct,txt_nameBrand,txt_idProduct,txt_price,txt_rate,txt_createdAt,txt_updatedAt;
    private EditText txt_description;
    private ImageView img_product,btn_back;
    private FloatingActionButton btn_edit , btn_delete;
    private ScrollView ctn_product;

    private ShopManageViewModel shopManageViewModel;

    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private productRepository repository;
    private product prod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_shopmanage_recyleview);
        //TEXT
        txt_nameProduct = findViewById(R.id.txt_nameProduct);
        txt_nameBrand = findViewById(R.id.txt_nameBrand);
        txt_idProduct = findViewById(R.id.txt_idProduct);
        txt_price = findViewById(R.id.txt_price);
        txt_rate = findViewById(R.id.txt_rate);
        txt_createdAt = findViewById(R.id.txt_createdAt);
        txt_updatedAt = findViewById(R.id.txt_updatedAt);
        txt_description = findViewById(R.id.txt_description);

        img_product = findViewById(R.id.img_product);


        btn_back = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        ctn_product = findViewById(R.id.ctn_product);

        helper.setTouchEffect(btn_back);
        helper.hideSystemUI(getWindow().getDecorView());

        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);

        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        Intent intent = getIntent();

        Glide
                .with(this)
                .load(APILinkHelper.getIMG() + intent.getStringExtra("thumb"))
                .into(img_product);
        txt_nameProduct.setText(intent.getStringExtra("name_product"));
        txt_nameBrand.setText("Thương hiệu: " + intent.getStringExtra("name_brand"));
        txt_idProduct.setText("Mã sản phẩm: "+ intent.getIntExtra("id_product", 0));
        txt_price.setText(String.valueOf(intent.getDoubleExtra("price" , 0)) + "đ");
        txt_rate.setText(intent.getDoubleExtra("rate" , 0) + "/5");
        txt_description.setText(intent.getStringExtra("description"));
        txt_createdAt.setText("Created at: "+intent.getStringExtra("createdAt"));
        txt_updatedAt.setText("Updated at: " + intent.getStringExtra("updatedAt"));

        String product_id = String.valueOf(intent.getIntExtra("id_product",0));

        btn_back.setOnClickListener(v -> {
            finish();
        });
        btn_delete.setOnClickListener(v -> {
            deleteProduct(Integer.parseInt(product_id));
        });
        int user_id = sharedPreferences.getInt("user_id", 0);
        prod = new product(intent.getIntExtra("id_product", 0),intent.getStringExtra("name_product") ,intent.getDoubleExtra("price" , 0), user_id,intent.getStringExtra("description"), intent.getDoubleExtra("rate" , 0), intent.getStringExtra("name_brand"), intent.getStringExtra("thumb"),0,intent.getIntExtra("shopper_id", 0),intent.getStringExtra("createdAt") ,intent.getStringExtra("updatedAt"));
        btn_edit.setOnClickListener(v -> {
            Intent intent1 = new Intent(itemProductDetailManage.this , updateProductActivity.class);
            intent1.putExtra("productModel" , prod);
            startActivity(intent1);
            finish();
        });
        ctn_product.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_delete.setAlpha(0.5f);
                        btn_edit.setAlpha(0.5f);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        btn_delete.setAlpha(1.0f);
                        btn_edit.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });
        
    }

    public void deleteProduct(int id){
        String token = sharedPreferences.getString("token" , "");
        repository = new productRepository();
        repository.deleteProduct(id, token, requestQueue, new productRepository.StringCallback() {
            @Override
            public void onResponse(String response) {
                Intent intent =  new Intent(itemProductDetailManage.this , ShopManage.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(itemProductDetailManage.this, "Có lỗi xảy ra khi xoá sản phẩm", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

    }

}
