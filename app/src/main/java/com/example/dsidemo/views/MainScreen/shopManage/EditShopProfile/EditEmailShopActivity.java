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

public class EditEmailShopActivity extends AppCompatActivity {
    private EditText txt_nameEmail;
    private ImageView btn_back;
    private TextView btn_save;
    private UpdatedShopperTextWatcher updatedShopperTextWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_email_shopper_profile);

        txt_nameEmail = findViewById(R.id.txt_nameEmail);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);

        helper.setTouchEffect(btn_back);
        helper.hideSystemUI(getWindow().getDecorView());
        updatedShopperTextWatcher = new UpdatedShopperTextWatcher(txt_nameEmail,btn_save);
        txt_nameEmail.addTextChangedListener(updatedShopperTextWatcher);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditEmailShopActivity.this, ShopProfile.class);
                intent.putExtra("email" , txt_nameEmail.getText().toString());
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
