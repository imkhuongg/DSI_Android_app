package com.example.dsidemo.views.profiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.authenticate.LoginActivity;

public class settingActivity extends Fragment {
    private SharedPreferences preferences;
    private Button btn_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_layout , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

    }

    public void logOut(){
        clearPreference();
        gotoLogin();


    }

    public void clearPreference(){
        preferences= getActivity().getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), Context.MODE_PRIVATE);
        if(preferences.getBoolean("authenticated" , false)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }

    }
    public void gotoLogin(){
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
