package com.example.dsidemo.helpers;

import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class helper extends AppCompatActivity {
    public helper(){}
    public static void setTouchEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Giảm opacity khi bấm vào
                        view.animate().alpha(0.5f).setDuration(10).start();
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Đặt lại opacity về ban đầu khi nhả ra
                        view.animate().alpha(1.0f).setDuration(500).start();
                        break;
                }
                return false;
            }
        });
    }
    public static void hideSystemUI(View decorView) {
        // Ẩn thanh trạng thái và thanh điều hướng
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI(getWindow().getDecorView());
        }
    }
}
