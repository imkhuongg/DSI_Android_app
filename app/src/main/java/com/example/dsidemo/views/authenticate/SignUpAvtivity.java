package com.example.dsidemo.views.authenticate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.signUpValidation;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.termActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SignUpAvtivity extends AppCompatActivity {

    private TextInputLayout ctn_usernameTxt , ctn_passtxt,ctn_extractPass,ctn_fname,ctn_name;
    private TextInputEditText UsernameTxt,passtxt,extractPass_input,fname_input,lname_input;

    private RequestQueue requestQueue;
    private Button SignUp;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        TextView signIn =  findViewById(R.id.SignInTxt);
        //Button
        SignUp = findViewById(R.id.button);
        //EditText
        UsernameTxt = findViewById(R.id.UsernameTxt);
        passtxt = findViewById(R.id.passtxt);
        extractPass_input = findViewById(R.id.extractPass_input);
        fname_input = findViewById(R.id.fname_input);
        lname_input = findViewById(R.id.lname_input);
        //TextInputLayout
        ctn_usernameTxt = findViewById(R.id.ctn_usernameTxt);
        ctn_passtxt = findViewById(R.id.ctn_passtxt);
        ctn_extractPass = findViewById(R.id.ctn_extractPass);
        ctn_fname = findViewById(R.id.ctn_fname);
        ctn_name = findViewById(R.id.ctn_name);


        helper.setTouchEffect(SignUp);
        helper.setTouchEffect(signIn);

        helper.hideSystemUI(getWindow().getDecorView());

        requestQueue = MySingleton.getInstance(SignUpAvtivity.this).getRequestQueue();

        signUpValidation signUpValidation = new signUpValidation(ctn_usernameTxt,ctn_passtxt, ctn_extractPass,  ctn_fname, ctn_name,  UsernameTxt, passtxt,extractPass_input,  fname_input,lname_input, SignUp);
        UsernameTxt.addTextChangedListener(signUpValidation);
        passtxt.addTextChangedListener(signUpValidation);
        extractPass_input.addTextChangedListener(signUpValidation);
        fname_input.addTextChangedListener(signUpValidation);
        lname_input.addTextChangedListener(signUpValidation);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUpAvtivity.this , LoginActivity.class);
                startActivity(signIn);
                finish();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpAvtivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                SignUp();
            }
        });
    }
    public void gotoLogin(){
        Intent intent = new Intent(SignUpAvtivity.this , LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void SignUp(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.signUp(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUpAvtivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpAvtivity.this, "Có lỗi xảy ra khi đăng ký", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("first_name", fname_input.getText().toString());
                params.put("last_name", lname_input.getText().toString());
                params.put("email", UsernameTxt.getText().toString());
                params.put("password", passtxt.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
