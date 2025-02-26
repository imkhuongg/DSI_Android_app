package com.example.dsidemo.helpers.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddShopperValidation implements TextWatcher {

    private TextInputEditText email,phone,name_shop,shop_address;
    private TextInputLayout  layout_email,layout_nameshop,layout_phone,layout_address;
    private Button btn_confirm;

    public AddShopperValidation(TextInputEditText email, TextInputEditText phone, TextInputEditText name_shop, TextInputEditText shop_address, TextInputLayout layout_email, TextInputLayout layout_nameshop, TextInputLayout layout_phone, TextInputLayout layout_address, Button btn_confirm) {
        this.email = email;
        this.phone = phone;
        this.name_shop = name_shop;
        this.shop_address = shop_address;
        this.layout_email = layout_email;
        this.layout_nameshop = layout_nameshop;
        this.layout_phone = layout_phone;
        this.layout_address = layout_address;
        this.btn_confirm = btn_confirm;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String txt_email = this.email.getText().toString();
        String txt_phone = this.phone.getText().toString();
        String txt_nameShop = this.name_shop.getText().toString();
        String txt_shopAdress = this.shop_address.getText().toString();

        layout_address.setError(null);
        layout_email.setError(null);
        layout_phone.setError(null);
        layout_nameshop.setError(null);

        if(txt_email == null || txt_email.isEmpty()) layout_email.setError("Email là trường bắt buộc");
        if(txt_phone == null || txt_phone.isEmpty()) layout_phone.setError("Số điện thoại là trường bắt buộc");
        if(txt_nameShop == null || txt_nameShop.isEmpty()) layout_nameshop.setError("Tên cửa hàng không được để trống");
        if(txt_shopAdress == null || txt_shopAdress.isEmpty()) layout_address.setError("Địa chỉ cửa hàng không được để trống");

        btn_confirm.setEnabled(!txt_phone.isEmpty() && !txt_email.isEmpty() && !txt_nameShop.isEmpty() && !txt_shopAdress.isEmpty());


    }
}
