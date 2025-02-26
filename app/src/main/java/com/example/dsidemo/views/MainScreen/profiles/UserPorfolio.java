package com.example.dsidemo.views.MainScreen.profiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.MainScreen.shopManage.NewShopperActivity;
import com.example.dsidemo.views.MainScreen.shopManage.ShopManage;
import com.google.android.material.button.MaterialButton;

public class UserPorfolio extends AppCompatActivity {

    private MaterialButton btn_userInfo,btn_security,btn_payment,btn_shop_manage,btn_help;
    private ImageView settingBtn,btn_extract,btn_delivery,btn_shipping,btn_rating,btn_back;
    private TextView nameUser;
    private SharedPreferences preferences;
    private RequestQueue requestQueue;
    private ShopperRepository shopperRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

    //Button
        btn_userInfo = findViewById(R.id.btn_userInfo);
        btn_security = findViewById(R.id.btn_security);
        btn_payment = findViewById(R.id.btn_payment);
        btn_shop_manage = findViewById(R.id.btn_shop_manage);
        btn_help = findViewById(R.id.btn_help);
        //ImageView
        settingBtn = findViewById(R.id.settingBtn);
        btn_extract = findViewById(R.id.btn_extract);
        btn_delivery = findViewById(R.id.btn_delivery);
        btn_shipping = findViewById(R.id.btn_shipping);
        btn_rating = findViewById(R.id.btn_rating);
        btn_back = findViewById(R.id.btn_back);
        //TextView
        nameUser = findViewById(R.id.txt_nameUser);


        helper.setTouchEffect(btn_userInfo);
        helper.setTouchEffect(btn_security);
        helper.setTouchEffect(btn_payment);
        helper.setTouchEffect(btn_shop_manage);
        helper.setTouchEffect(btn_help);
        helper.setTouchEffect(settingBtn);
        helper.setTouchEffect(btn_extract);
        helper.setTouchEffect(btn_delivery);
        helper.setTouchEffect(btn_shipping);
        helper.setTouchEffect(btn_rating);
        helper.setTouchEffect(btn_back);

        helper.hideSystemUI(getWindow().getDecorView());


        preferences = getSharedPreferences(StringResourceHelper.getShopperInfo() , MODE_PRIVATE);
        boolean check = preferences.getBoolean("isShopper" , false);
        if(check == false){
            btn_shop_manage.setText("Đăng ký trở thành người bán hàng");
            btn_shop_manage.setIconPadding(-35);
        } else{
            btn_shop_manage.setText("Quản lý bán hàng");
        }


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPorfolio.this,settingActivity.class);
                startActivity(intent);
            }
        });

        preferences= getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), Context.MODE_PRIVATE);
        if(preferences.getBoolean("authenticated" , false)){
            String fullNameUser = preferences.getString("last_name" , "last_name")+ " " + preferences.getString("first_name" , "first_name");
            nameUser.setText(fullNameUser);
        }
        btn_shop_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check == false){
                    Intent intent = new Intent(UserPorfolio.this, NewShopperActivity.class);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(UserPorfolio.this , ShopManage.class);
                    startActivity(intent);
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
