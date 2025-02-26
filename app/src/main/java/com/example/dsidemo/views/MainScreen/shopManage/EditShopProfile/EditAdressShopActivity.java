package com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.UpdatedShopperTextWatcher;
import com.example.dsidemo.views.MainScreen.shopManage.ShopProfile;
import com.google.android.material.textfield.TextInputEditText;

public class EditAdressShopActivity extends AppCompatActivity {
    private ImageView btn_back;
    private UpdatedShopperTextWatcher updatedShopperTextWatcher;
    private TextView btn_save;
    private TextInputEditText txt_shopAddress;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_address_shopper_profile);

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        txt_shopAddress = findViewById(R.id.txt_shopAddress);

        helper.setTouchEffect(btn_back);
        helper.hideSystemUI(getWindow().getDecorView());
        updatedShopperTextWatcher = new UpdatedShopperTextWatcher(txt_shopAddress,btn_save);
        txt_shopAddress.addTextChangedListener(updatedShopperTextWatcher);


        sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);
        Intent intent = getIntent();
        txt_shopAddress.setText(intent.getStringExtra("shop_address"));

        String address = txt_shopAddress.getText().toString();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAdressShopActivity.this, ShopProfile.class);
                if(address != null && !address.isEmpty()) intent.putExtra("shopAddress" , txt_shopAddress.getText().toString());
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


    }
}
