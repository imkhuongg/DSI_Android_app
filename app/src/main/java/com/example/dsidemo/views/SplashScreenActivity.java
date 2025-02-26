package com.example.dsidemo.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.authenticate.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        preferences= getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getBoolean("authenticated" , false))
                    gotoMainActivity();
                else
                    gotoLoginActivity();
            }
        }, 1000);
    }

    public void gotoLoginActivity(){
        Intent LoginActivity = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(LoginActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    public void gotoMainActivity(){
        Intent mainActivity = new Intent(SplashScreenActivity.this, MainScreen.class);
        startActivity(mainActivity);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}