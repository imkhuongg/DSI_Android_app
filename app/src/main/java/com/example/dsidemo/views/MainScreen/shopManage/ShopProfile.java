package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditAdressShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditEmailShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditNameShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditPhoneShopActivity;
import com.google.android.material.card.MaterialCardView;

public class ShopProfile extends AppCompatActivity {

    private ImageView btn_back,img_avatar;
    private MaterialCardView ctn_fullnameShop,ctn_email,ctn_phone,ctn_address;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_shop);

        //BTN
        btn_back = findViewById(R.id.btn_back);
        //CONTAINER
        ctn_fullnameShop = findViewById(R.id.ctn_fullnameShop);
        ctn_email = findViewById(R.id.ctn_email);
        ctn_phone = findViewById(R.id.ctn_phone);
        ctn_address = findViewById(R.id.ctn_address);
        //IMG
        img_avatar = findViewById(R.id.img_avatar);

        helper.hideSystemUI(getWindow().getDecorView());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        ctn_address.setOnClickListener(v -> {
            Intent intent = new Intent(ShopProfile.this , EditAdressShopActivity.class);
            startActivity(intent);
            finish();
        });
        ctn_email.setOnClickListener(v -> {
            Intent intent = new Intent(ShopProfile.this , EditEmailShopActivity.class);
            startActivity(intent);
            finish();
        });
        ctn_phone.setOnClickListener(v->{
            Intent intent = new Intent(ShopProfile.this , EditPhoneShopActivity.class);
            startActivity(intent);
            finish();
        });
        ctn_fullnameShop.setOnClickListener(v->{
            Intent intent = new Intent(ShopProfile.this , EditNameShopActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
