package com.example.dsidemo.views.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;

public class SignUpAvtivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        TextView signIn =  findViewById(R.id.SignInTxt);
        Button SignUp = findViewById(R.id.button);

        helper.setTouchEffect(SignUp);
        helper.setTouchEffect(signIn);

        helper.hideSystemUI(getWindow().getDecorView());


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUpAvtivity.this , LoginActivity.class);
                startActivity(signIn);
            }
        });


    }
}
