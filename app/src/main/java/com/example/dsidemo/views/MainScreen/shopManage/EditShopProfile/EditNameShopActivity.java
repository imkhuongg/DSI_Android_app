package com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;

public class EditNameShopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_name_shopper_profile);

        helper.hideSystemUI(getWindow().getDecorView());
    }
}
