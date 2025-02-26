package com.example.dsidemo.views.MainScreen.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.views.MainScreen.profiles.UserPorfolio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView img_product,btn_porfolio,btn_back;
    private TextView txt_nameProduct,txt_nameBrand,txt_idProduct,txt_price,txt_rate;
    private EditText txt_description;
    private product product;
    private ScrollView ctn_product;
    private FloatingActionButton btn_message, btn_cart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product);


        //IMAGE
        img_product = findViewById(R.id.img_product);
        //BUTTON
        btn_back =  findViewById(R.id.btn_back);
        btn_porfolio =  findViewById(R.id.btn_porfolio);
        btn_message = findViewById(R.id.btn_message);
        btn_cart = findViewById(R.id.btn_cart);
        //TEXT
        txt_nameProduct = findViewById(R.id.txt_nameProduct);
        txt_nameBrand = findViewById(R.id.txt_nameBrand);
        txt_idProduct = findViewById(R.id.txt_idProduct);
        txt_price = findViewById(R.id.txt_price);
        txt_rate = findViewById(R.id.txt_rate);
        txt_description = findViewById(R.id.txt_description);

        ctn_product = findViewById(R.id.ctn_product);

        helper.hideSystemUI(getWindow().getDecorView());
        helper.setAlphaScrollView(ctn_product , btn_cart,btn_message);

        Intent intent = getIntent();
        product = (product) intent.getSerializableExtra("product");

        String nameBrandString = "Thương hiệu: " + product.getName_brand();
        String idProductString = "Mã SP: " + product.getProduct_id();
        String rateString = product.getRate()+ "/5";
        String priceString = String.format("%,.2f", product.getPrice()) + "đ";

        Glide
                .with(this)
                .load(APILinkHelper.getIMG() + product.getThumb())
                .into(img_product);
        txt_nameProduct.setText(product.getName_product());
        txt_nameBrand.setText(nameBrandString);
        txt_idProduct.setText(idProductString);
        txt_price.setText(priceString);
        txt_rate.setText(rateString);
        txt_description.setText(product.getDescription());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_porfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProductDetailActivity.this , UserPorfolio.class);
                startActivity(intent1);
                finish();
            }
        });

    }
}
