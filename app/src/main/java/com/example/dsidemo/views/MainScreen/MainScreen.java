package com.example.dsidemo.views.MainScreen;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dsidemo.views.MainScreen.Component.CartFragment;
import com.example.dsidemo.views.MainScreen.Component.EditManocanhFragment;
import com.example.dsidemo.views.MainScreen.Component.InstructionFragment;
import com.example.dsidemo.views.MainScreen.Component.NewsFragment;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        helper.hideSystemUI(getWindow().getDecorView());
        setContentView(R.layout.main_screen);

        bottomNavigationView = findViewById(R.id.BottomNav);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Hiển thị fragment đầu tiên
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditManocanhFragment()).commit();
    }

    private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.edit_manocanh:
                    selectedFragment = new EditManocanhFragment();
                    break;
                case R.id.news:
                    selectedFragment = new NewsFragment();
                    break;
                case R.id.instruction:
                    selectedFragment = new InstructionFragment();
                    break;
                case R.id.cart:
                    selectedFragment = new CartFragment();
                    break;
            }

            // Thay thế fragment đã chọn
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };
}
