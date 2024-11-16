package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.utils.MySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class itemProductDetailManage extends Fragment {
    private TextView txt_nameProduct,txt_nameBrand,txt_idProduct,txt_price,txt_rate,txt_createdAt,txt_updatedAt;
    private EditText txt_description;
    private ImageView img_product,btn_back;
    private FloatingActionButton btn_edit;

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_shopmanage_recyleview , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TEXT
        txt_nameProduct = view.findViewById(R.id.txt_nameProduct);
        txt_nameBrand = view.findViewById(R.id.txt_nameBrand);
        txt_idProduct = view.findViewById(R.id.txt_idProduct);
        txt_price = view.findViewById(R.id.txt_price);
        txt_rate = view.findViewById(R.id.txt_rate);
        txt_createdAt = view.findViewById(R.id.txt_createdAt);
        txt_updatedAt = view.findViewById(R.id.txt_updatedAt);
        txt_description = view.findViewById(R.id.txt_description);

        //IMG
        img_product = view.findViewById(R.id.img_product);

        //Button
        btn_back = view.findViewById(R.id.btn_back);
        btn_edit = view.findViewById(R.id.btn_edit);

        sharedPreferences = view.getContext().getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);

        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();

    }
}
