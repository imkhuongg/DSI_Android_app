package com.example.dsidemo.helpers.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class signUpValidation implements TextWatcher {
    private TextInputLayout ctn_usernameTxt , ctn_passtxt,ctn_extractPass,ctn_fname,ctn_name;
    private TextInputEditText UsernameTxt,passtxt,extractPass_input,fname_input,lname_input;
    private Button SignUp;

    public signUpValidation(TextInputLayout ctn_usernameTxt, TextInputLayout ctn_passtxt, TextInputLayout ctn_extractPass, TextInputLayout ctn_fname, TextInputLayout ctn_name, TextInputEditText usernameTxt, TextInputEditText passtxt, TextInputEditText extractPass_input, TextInputEditText fname_input, TextInputEditText lname_input, Button signUp) {
        this.ctn_usernameTxt = ctn_usernameTxt;
        this.ctn_passtxt = ctn_passtxt;
        this.ctn_extractPass = ctn_extractPass;
        this.ctn_fname = ctn_fname;
        this.ctn_name = ctn_name;
        this.UsernameTxt = usernameTxt;
        this.passtxt = passtxt;
        this.extractPass_input = extractPass_input;
        this.fname_input = fname_input;
        this.lname_input = lname_input;
        this.SignUp = signUp;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String firsname = fname_input.getText().toString().trim();
        String lname = lname_input.getText().toString().trim();
        String username = UsernameTxt.getText().toString().trim();
        String pass = passtxt.getText().toString().trim();
        String extract = extractPass_input.getText().toString().trim();


        ctn_usernameTxt.setError(null);
        ctn_passtxt.setError(null);
        ctn_extractPass.setError(null);
        ctn_fname.setError(null);
        ctn_name.setError(null);

        if (firsname.isEmpty() || firsname == null){
            ctn_fname.setError("Tên không được để trống");
        }
        if (lname.isEmpty() || lname == null){
            ctn_name.setError("Họ không được để trống");
        }
        if (username.isEmpty() || username == null){
            ctn_usernameTxt.setError("Tài khoản không được để trống");
        }
        if (pass.isEmpty() || pass == null){
            ctn_passtxt.setError("Mật khẩu không được để trống");
        }
        if (extract.isEmpty() || extract == null){
            ctn_extractPass.setError("Xác nhận mật không được để trống");
        }
        if (!extract.equals(pass)){
            ctn_extractPass.setError("Không khớp với mật khẩu");
        }

        SignUp.setEnabled(!firsname.isEmpty() && !lname.isEmpty() && !username.isEmpty() && !pass.isEmpty() && !extract.isEmpty() && extract.equals(pass));

    }
}
