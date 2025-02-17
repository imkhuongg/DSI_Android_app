package com.example.dsidemo.views.MainScreen.profiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.views.authenticate.LoginActivity;

public class settingActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Button btn_logout;
    private ImageView btn_back;
    private RequestQueue requestQueue;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);


        btn_logout = findViewById(R.id.btn_logout);
        btn_back = findViewById(R.id.btn_back);

        helper.setTouchEffect(btn_back);
        helper.hideSystemUI(getWindow().getDecorView());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

    }

    public void logOut(){
        clearPreference();
        gotoLogin();


    }

    public void clearPreference(){
        preferences= getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(),MODE_PRIVATE);
        Context context = getApplicationContext();
        String userDetailPrefName = StringResourceHelper.getUserDetailPrefName();
        String shopperInfo = StringResourceHelper.getShopperInfo();
        boolean deletedUserDetail = context.deleteSharedPreferences(userDetailPrefName);
        boolean deletedShopperInfo = context.deleteSharedPreferences(shopperInfo);
        if(preferences.getBoolean("authenticated" , false)){
            SharedPreferences.Editor editor = preferences.edit();
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            editor1.clear();
            editor1.apply();;
        }

    }
    public void gotoLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

            finish();
    }
}
