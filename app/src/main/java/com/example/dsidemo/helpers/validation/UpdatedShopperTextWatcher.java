package com.example.dsidemo.helpers.validation;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dsidemo.R;

public class UpdatedShopperTextWatcher implements TextWatcher {

    private EditText editText;
    private TextView textView;

    public UpdatedShopperTextWatcher(EditText editText, TextView textView) {
        this.editText = editText;
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = editText.getText().toString();

        if(!text.isEmpty() && text != null){
            textView.setEnabled(true);
            textView.setTextColor(R.color.MainColor);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
