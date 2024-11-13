package com.example.dsidemo.views.MainScreen.profiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.MainScreen.shopManage.ShopManage;
import com.google.android.material.button.MaterialButton;

public class UserPorfolio extends Fragment {

    private MaterialButton btn_userInfo,btn_security,btn_payment,btn_shop_manage,btn_help;
    private ImageView settingBtn,btn_extract,btn_delivery,btn_shipping,btn_rating,btn_back;
    private TextView nameUser;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Button
        btn_userInfo = view.findViewById(R.id.btn_userInfo);
        btn_security = view.findViewById(R.id.btn_security);
        btn_payment = view.findViewById(R.id.btn_payment);
        btn_shop_manage = view.findViewById(R.id.btn_shop_manage);
        btn_help = view.findViewById(R.id.btn_help);
        //ImageView
        settingBtn = view.findViewById(R.id.settingBtn);
        btn_extract = view.findViewById(R.id.btn_extract);
        btn_delivery = view.findViewById(R.id.btn_delivery);
        btn_shipping = view.findViewById(R.id.btn_shipping);
        btn_rating = view.findViewById(R.id.btn_rating);
        btn_back = view.findViewById(R.id.btn_back);
        //TextView
        nameUser = view.findViewById(R.id.txt_nameUser);


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


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingActivity settingActivity = new settingActivity();
                if (getActivity() instanceof MainScreen) {
                    ((MainScreen) getActivity()).replaceFragment(settingActivity);
                }
            }
        });

        preferences= getActivity().getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), Context.MODE_PRIVATE);
        if(preferences.getBoolean("authenticated" , false)){
            String fullNameUser = preferences.getString("last_name" , "last_name")+ " " + preferences.getString("first_name" , "first_name");
            nameUser.setText(fullNameUser);
        }
        btn_shop_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopManage shopManage = new ShopManage();
                if (getActivity() instanceof MainScreen) {
                    ((MainScreen) getActivity()).replaceFragment(shopManage);
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
}
