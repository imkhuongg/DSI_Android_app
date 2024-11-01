package com.example.dsidemo.views.authenticate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.LoginValidationHelper;
import com.example.dsidemo.views.termActivity;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {
    Intent term;

    private SharedPreferences preferences;
    private LoginValidationHelper loginValidationHelper;
    private TextInputLayout emailTxtlayout,passwordLayout;
    private EditText loginField,passwordField;
    private Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);

        TextView NextBtn = findViewById(R.id.btnNext);
        TextView help, SignUp, fogot ;
        ImageView facebook, gmail, zalo;

        loginBtn = findViewById(R.id.button);

        emailTxtlayout =findViewById(R.id.UsernameTxt);
        passwordLayout = findViewById(R.id.passtxt);

        loginField = findViewById(R.id.UsernameTxtContent);
        passwordField = findViewById(R.id.passtxtContent);

        help = findViewById(R.id.helpTxt);
        SignUp = findViewById(R.id.SignUpTxt);
        fogot = findViewById(R.id.textView3);
        facebook = findViewById(R.id.facebook_icon);
        gmail = findViewById(R.id.gmail_icon);
        zalo = findViewById(R.id.zalo_icon);

        helper.setTouchEffect(NextBtn);
        helper.setTouchEffect(loginBtn);
        helper.setTouchEffect(SignUp);
        helper.setTouchEffect(help);
        helper.setTouchEffect(fogot);
        helper.setTouchEffect(facebook);
        helper.setTouchEffect(gmail);
        helper.setTouchEffect(zalo);

        helper.hideSystemUI(getWindow().getDecorView());

        loginValidationHelper = new LoginValidationHelper(loginField , passwordField , emailTxtlayout ,passwordLayout , loginBtn);
        loginField.addTextChangedListener(loginValidationHelper);
        passwordField.addTextChangedListener(loginValidationHelper);


        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               term = new Intent(LoginActivity.this , termActivity.class);
               startActivity(term);
               NextBtn.setAlpha(0.5f);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(LoginActivity.this , SignUpAvtivity.class);
                startActivity(signUp);
            }
        });
    }

}
