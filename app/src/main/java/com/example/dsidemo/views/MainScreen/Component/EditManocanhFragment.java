package com.example.dsidemo.views.MainScreen.Component;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dsidemo.R;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.MainScreen.profiles.UserPorfolio;

public class EditManocanhFragment extends Fragment {

    private ImageView porfolio, chon_vay_ngan, chon_dang,chon_toc,chon_vay_dai,chon_ao,chon_quan,chon_giay,chon_kinh,chon_tui,chon_khuyen_tai;
    private Button navModel;
    MainScreen mainScreen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editmanocanh,container,false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        porfolio = view.findViewById(R.id.porfolio_icon);
        chon_vay_ngan = view.findViewById(R.id.chon_vay_ngan);
        chon_dang = view.findViewById(R.id.chon_dang);
        chon_toc = view.findViewById(R.id.chon_toc);
        chon_vay_dai = view.findViewById(R.id.chon_vay_dai);
        chon_ao = view.findViewById(R.id.chon_ao);
        chon_quan = view.findViewById(R.id.chon_quan);
        chon_giay = view.findViewById(R.id.chon_giay);
        chon_kinh = view.findViewById(R.id.chon_kinh);
        chon_tui = view.findViewById(R.id.chon_tui);
        chon_khuyen_tai = view.findViewById(R.id.chon_khuyen_tai);

        navModel = view.findViewById(R.id.nav_model);

        helper.setTouchEffect(porfolio);
        helper.setTouchEffect(chon_vay_ngan);
        helper.setTouchEffect(chon_dang);
        helper.setTouchEffect(chon_toc);
        helper.setTouchEffect(chon_vay_dai);
        helper.setTouchEffect(chon_ao);
        helper.setTouchEffect(chon_quan);
        helper.setTouchEffect(chon_giay);
        helper.setTouchEffect(chon_kinh);
        helper.setTouchEffect(chon_tui);
        helper.setTouchEffect(chon_khuyen_tai);
        helper.setTouchEffect(navModel);

        porfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , UserPorfolio.class);
                startActivity(intent);
            }
        });
    }

}
