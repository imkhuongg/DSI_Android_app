package com.example.dsidemo.helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dsidemo.views.MainScreen.shopManage.addProduct;

public class helper extends AppCompatActivity {
    public helper(){}
    public static void setTouchEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Giảm opacity khi bấm vào
                        view.animate().alpha(0.5f).setDuration(500).start();
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
    public static void setAlphaScrollView(View view , ImageView btn_delete , ImageView btn_edit){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_delete.setAlpha(0.5f);
                        btn_edit.setAlpha(0.5f);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        btn_delete.setAlpha(1.0f);
                        btn_edit.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });
    }
    public static void setAlphaScrollView(View view , ImageView btn_1){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_1.setAlpha(0.5f);

                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        btn_1.setAlpha(1.0f);

                        break;
                }
                return false;
            }
        });
    }
    public static void setAlphaScrollView(View view , View btn_1){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_1.setAlpha(0.5f);

                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        btn_1.setAlpha(1.0f);

                        break;
                }
                return false;
            }
        });
    }
    public static void  openFileChooser(ActivityResultLauncher<Intent> resultLauncher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }
    public static String getImageName(Uri uri , Context context) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String imageName = cursor.getString(columnIndex);
            cursor.close();
            return imageName;
        } else {
            return null;
        }
    }
}
