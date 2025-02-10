package com.example.dsidemo.helpers.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.textfield.TextInputLayout;

public class LoginValidationHelper implements TextWatcher {

    private EditText emailTxt,passwordTxt ;
    private TextInputLayout emailTxtLayout,passwordTxtLayout;
    private Button loginBtn;
    private String emailField,passwordField;

    public LoginValidationHelper(EditText emailTxt, EditText passwordTxt, TextInputLayout emailTxtLayout, TextInputLayout passwordTxtLayout, Button loginBtn) {
        this.emailTxt = emailTxt;
        this.passwordTxt = passwordTxt;
        this.emailTxtLayout = emailTxtLayout;
        this.passwordTxtLayout = passwordTxtLayout;
        this.loginBtn = loginBtn;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
      emailField = emailTxt.getText().toString().trim();
      passwordField = passwordTxt.getText().toString().trim();

      emailTxtLayout.setError(null);
      passwordTxtLayout.setError(null);

      if(emailField.isEmpty() || emailField == null)
          emailTxtLayout.setError("Email không được bỏ trống");
      if(passwordField.isEmpty() || passwordField == null)
          passwordTxtLayout.setError("Mật khẩu không được bỏ trống");

      loginBtn.setEnabled(!emailField.isEmpty() && !passwordField.isEmpty());
    }
}
