package com.example.dsidemo.views.authenticate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.LoginValidationHelper;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.termActivity;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    Intent term;

    private RequestQueue requestQueue;

    private SharedPreferences preferences;
    private LoginValidationHelper loginValidationHelper;
    private TextInputLayout emailTxtlayout,passwordLayout;
    private EditText loginField,passwordField;
    private Button loginBtn;
    private ShopperRepository shopperRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        requestQueue = MySingleton.getInstance(LoginActivity.this).getRequestQueue();

        TextView NextBtn = findViewById(R.id.btnNext);
        TextView help, SignUp, fogot ;
        ImageView facebook, gmail, zalo;

        loginBtn = findViewById(R.id.button);

        emailTxtlayout =findViewById(R.id.UsernameTxt);
        passwordLayout = findViewById(R.id.passtxt);

        loginField = findViewById(R.id.UsernameTxtContent);
        passwordField = findViewById(R.id.passtxtContent);

        help = findViewById(R.id.helpTxt);
        SignUp = findViewById(R.id.SignUpTxt);
        fogot = findViewById(R.id.textView3);
        facebook = findViewById(R.id.facebook_icon);
        gmail = findViewById(R.id.gmail_icon);
        zalo = findViewById(R.id.zalo_icon);

        helper.setTouchEffect(NextBtn);
        helper.setTouchEffect(loginBtn);
        helper.setTouchEffect(SignUp);
        helper.setTouchEffect(help);
        helper.setTouchEffect(fogot);
        helper.setTouchEffect(facebook);
        helper.setTouchEffect(gmail);
        helper.setTouchEffect(zalo);

        helper.hideSystemUI(getWindow().getDecorView());

        loginValidationHelper = new LoginValidationHelper(loginField , passwordField , emailTxtlayout ,passwordLayout , loginBtn);
        loginField.addTextChangedListener(loginValidationHelper);
        passwordField.addTextChangedListener(loginValidationHelper);


        processLogin();


        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               term = new Intent(LoginActivity.this , termActivity.class);
               startActivity(term);
               NextBtn.setAlpha(0.5f);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gotoRegister();
            }
        });
    }

    public void processLogin(){
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(loginField.getText().toString(), passwordField.getText().toString());

            }
        });
    }

    public void CheckShopper(String token){
        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
        int check = 0;
        shopperRepository = new ShopperRepository();
        SharedPreferences sharedPreferences1 = getSharedPreferences(StringResourceHelper.getShopperInfo() , MODE_PRIVATE);
        shopperRepository.CheckShopper(token, requestQueue, new ShopperRepository.IntCallback() {
            @Override
            public void onResponse(int response) {
                Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                if(response == 0){
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putBoolean("isShopper", false);
                } else{
                    shopperRepository.getShopper(token, requestQueue, new ShopperRepository.ObjectCallback() {
                        @Override
                        public void onSuccess(JSONObject shoppers) {
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            try {
                                editor.putBoolean("isShopper", true);
                                editor.putInt("shopper_id", shoppers.getInt("shopper_id"));
                                editor.putInt("user_id" , shoppers.getInt("user_id"));
                                editor.putString("name_shop" , shoppers.getString("name_shop"));
                                editor.putString("email" , shoppers.getString("email"));
                                editor.putString("avatar" , shoppers.getString("avatar"));
                                editor.putInt("phone" , shoppers.getInt("phone"));
                                editor.putString("shop_address" , shoppers.getString("shop_address"));
                                editor.putInt("follower" , shoppers.getInt("follower"));
                                editor.putInt("total_sold" , shoppers.getInt("total_sold"));
                                editor.putLong("total_revenue" , shoppers.getLong("total_revenue"));
                                editor.putString("created_at" , shoppers.getString("created_at"));
                                editor.putString("updated_at" , shoppers.getString("updated_at"));
                                editor.apply();

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




    }

    public void gotoRegister(){
        Intent intent = new Intent(LoginActivity.this, SignUpAvtivity.class);
        startActivity(intent);
        finish();
    }


    public void gotoMainActivityAuthenticated(){
        Intent intent = new Intent(LoginActivity.this, MainScreen.class);
        startActivity(intent);
        Toast.makeText(this, "LoginSuccessfull", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void authenticateUser(String email , String password){
        HashMap<String , String> params = new HashMap<>();
        params.put("email" , email);
        params.put("password" , password);

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.POST, APILinkHelper.authUserApiUri(), new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        preferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        try{
                            editor.putInt("user_id" , response.getInt("id"));
                            editor.putString("first_name" , response.getString("firstname"));
                            editor.putString("last_name" , response.getString("lastname"));
                            editor.putString("email" , response.getString("username"));
                            editor.putString("token" , response.getString("token"));
                            editor.putBoolean("authenticated" , true);
                            editor.apply();
                            CheckShopper(response.getString("token"));

                            gotoMainActivityAuthenticated();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(LoginActivity.this, "failed to login", Toast.LENGTH_SHORT).show();

                    }
        });

        requestQueue.add(request);
    }

}
