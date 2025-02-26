package com.example.dsidemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.views.authenticate.LoginActivity;

public class termActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_layout);

        Button btnOK, btnCancel;
        CheckBox checkBox;

        checkBox = findViewById(R.id.checkBoxbtn);
        btnOK = findViewById(R.id.btnterm_Oke);
        btnCancel = findViewById(R.id.btnterm_cancel);


        helper.setTouchEffect(btnOK);
        helper.setTouchEffect(btnCancel);

        helper.hideSystemUI(getWindow().getDecorView());

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    Intent intent = new Intent(termActivity.this , MainScreen.class);
                    startActivity(intent);
                }else Toast.makeText(termActivity.this, "Vui lòng chọn đồng ý điều khoản", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(termActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
