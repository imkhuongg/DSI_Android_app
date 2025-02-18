package com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.UpdatedShopperTextWatcher;
import com.example.dsidemo.views.MainScreen.shopManage.ShopProfile;

public class EditPhoneShopActivity extends AppCompatActivity {

    private ImageView btn_back;
    private EditText txt_phoneShop;
    private TextView btn_save;
    private UpdatedShopperTextWatcher updatedShopperTextWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_phone_shopper_profile);

        btn_back = findViewById(R.id.btn_back);
        txt_phoneShop = findViewById(R.id.txt_phoneShop);
        btn_save = findViewById(R.id.btn_save);

        helper.hideSystemUI(getWindow().getDecorView());
        helper.setTouchEffect(btn_back);
        updatedShopperTextWatcher = new UpdatedShopperTextWatcher(txt_phoneShop, btn_save);
        txt_phoneShop.addTextChangedListener(updatedShopperTextWatcher);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPhoneShopActivity.this, ShopProfile.class);
                intent.putExtra("phone" , txt_phoneShop.getText().toString());
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
