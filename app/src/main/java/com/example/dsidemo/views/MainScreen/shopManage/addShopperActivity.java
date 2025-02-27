package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.dsidemo.R;
import com.example.dsidemo.events.CloseSellerRegisterEvent;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.helpers.validation.AddShopperValidation;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.utils.MySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

public class addShopperActivity extends AppCompatActivity {

    private TextInputEditText txt_nameshop,txt_email,txt_phone,txt_shopAddress;
    private TextInputLayout layout_nameshop,layout_email,layout_phone,layout_shopAdress;
    private Button btn_confirm;
    private AddShopperValidation addShopperValidation;
    private ShopperRepository shopperRepository;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private ImageView btn_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_shopper);

        //TEXT INPUT
        txt_email = findViewById(R.id.txt_email);
        txt_nameshop = findViewById(R.id.txt_nameshop);
        txt_phone = findViewById(R.id.txt_phone);
        txt_shopAddress = findViewById(R.id.txt_shopAddress);
        //LAYOUT TEXT INPUT
        layout_email = findViewById(R.id.layout_email);
        layout_nameshop = findViewById(R.id.layout_nameshop);
        layout_phone = findViewById(R.id.layout_phone);
        layout_shopAdress = findViewById(R.id.layout_shopAdress);
        //BUTTON
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_back = findViewById(R.id.btn_back);

        progressBar = findViewById(R.id.progress);

        helper.hideSystemUI(getWindow().getDecorView());
        helper.setTouchEffect(btn_confirm);

        addShopperValidation = new AddShopperValidation(txt_email,txt_phone,txt_nameshop,txt_shopAddress,layout_email,layout_nameshop,layout_phone,layout_shopAdress,btn_confirm);
        txt_nameshop.addTextChangedListener(addShopperValidation);
        txt_email.addTextChangedListener(addShopperValidation);
        txt_shopAddress.addTextChangedListener(addShopperValidation);
        txt_phone.addTextChangedListener(addShopperValidation);

        requestQueue = MySingleton.getInstance(getBaseContext()).getRequestQueue();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                ShopperRegistration();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EventBus.getDefault().register(this);
    }

    public void ShopperRegistration(){
        shopperRepository = new ShopperRepository();
        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
        String token = sharedPreferences.getString("token" , "");
        String name_shop = txt_nameshop.getText().toString();
        String email = txt_email.getText().toString();
        String phone = txt_phone.getText().toString();
        String shop_address = txt_shopAddress.getText().toString();

        shopperRepository.ShopperRegistration(token, name_shop, email, phone, shop_address, requestQueue, new ShopperRepository.StringCallback() {
            @Override
            public void onResponse(String response) {
                SharedPreferences sharedPreferences1 = getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);
                shopperRepository.getShopper(token, requestQueue, new ShopperRepository.ObjectCallback() {
                    @Override
                    public void onSuccess(JSONObject shoppers) {
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        try {
                            editor.putBoolean("ishopper", true);
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
                Toast.makeText(addShopperActivity.this, "Tạo mới người bán hàng thành công", Toast.LENGTH_SHORT).show();
                gotoAddAvatarShopper();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(addShopperActivity.this, "Có lỗi xảy ra khi thêm mới", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void gotoAddAvatarShopper(){
        Intent intent = new Intent(addShopperActivity.this, AddShopperAvatarActivity.class);
        startActivity(intent);
    }
    @Subscribe
    public void onCloseEvent(CloseSellerRegisterEvent event) {
        finish(); // Đóng ActivityA
    }

    @Override
    protected void onDestroy() {
        // Hủy đăng ký để tránh memory leak
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
