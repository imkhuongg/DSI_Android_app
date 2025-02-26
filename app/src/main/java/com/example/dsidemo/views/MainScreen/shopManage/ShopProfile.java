package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.utils.MySingleton;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditAdressShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditEmailShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditNameShopActivity;
import com.example.dsidemo.views.MainScreen.shopManage.EditShopProfile.EditPhoneShopActivity;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopProfile extends AppCompatActivity {

    private ImageView btn_back,img_avatar;
    private MaterialCardView ctn_fullnameShop,ctn_email,ctn_phone,ctn_address;
    private SharedPreferences sharedPreferences;
    private TextView txt_nameShop,txt_email,txt_phone;
    private Button btn_updateShop;
    private ShopperRepository shopperRepository;
    private RequestQueue requestQueue;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_shop);
        this.recreate();

        //BTN
        btn_back = findViewById(R.id.btn_back);
        btn_updateShop = findViewById(R.id.btn_updateShop);
        //CONTAINER
        ctn_fullnameShop = findViewById(R.id.ctn_fullnameShop);
        ctn_email = findViewById(R.id.ctn_email);
        ctn_phone = findViewById(R.id.ctn_phone);
        ctn_address = findViewById(R.id.ctn_address);
        //IMG
        img_avatar = findViewById(R.id.img_avatar);

        //TEXTVIEW
        txt_email = findViewById(R.id.txt_email);
        txt_nameShop = findViewById(R.id.txt_nameShop);
        txt_phone = findViewById(R.id.txt_phone);

        helper.hideSystemUI(getWindow().getDecorView());

        sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(),MODE_PRIVATE);
        String imgPath =  sharedPreferences.getString("avatar","");

        if(imgPath.isEmpty() || imgPath == null) {
            img_avatar.setImageResource(R.drawable.icon_avatar_default);
        }else{
            Glide
                    .with(this)
                    .load(APILinkHelper.getIMG() + imgPath)
                    .transform(new CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img_avatar);
            img_avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        txt_phone.setText(String.valueOf("0" +sharedPreferences.getInt("phone", 0)));
        txt_nameShop.setText(sharedPreferences.getString("name_shop", ""));
        txt_email.setText(sharedPreferences.getString("email", ""));

        Intent intent1 = getIntent();
        String name_shop = intent1.getStringExtra("name_shop");
        String phone  = intent1.getStringExtra("phone");
        String email = intent1.getStringExtra("email");



        if(name_shop != null &&  !name_shop.isEmpty()){
            txt_nameShop.setText(name_shop);
        }
        if(phone != null &&  !phone.isEmpty()){
            txt_nameShop.setText(phone);
        }
        if(email != null &&  !email.isEmpty()){
            txt_nameShop.setText(email);
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ctn_address.setOnClickListener(v -> {
            Intent intent = new Intent(ShopProfile.this , EditAdressShopActivity.class);
            sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);
            intent.putExtra("shop_address" , sharedPreferences.getString("shop_address",""));
            startActivity(intent);

        });
        ctn_email.setOnClickListener(v -> {
            Intent intent = new Intent(ShopProfile.this , EditEmailShopActivity.class);
            startActivity(intent);

        });
        ctn_phone.setOnClickListener(v->{
            Intent intent = new Intent(ShopProfile.this , EditPhoneShopActivity.class);
            startActivity(intent);

        });
        ctn_fullnameShop.setOnClickListener(v->{
            Intent intent = new Intent(ShopProfile.this , EditNameShopActivity.class);
            startActivity(intent);

        });
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShopProfile.this, AddShopperAvatarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_updateShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                String name_shop = txt_nameShop.getText().toString();
                String email = txt_email.getText().toString();
                int phone = Integer.parseInt(txt_phone.getText().toString());

                String shop_address = sharedPreferences.getString("shop_address","");
                String shop_addressIntent = intent1.getStringExtra("shopAddress");

                if(shop_addressIntent != null &&  !shop_addressIntent.isEmpty() && !shop_addressIntent.equals("")){
                    shop_address = shop_addressIntent;
                } else shop_address = sharedPreferences.getString("shop_address","");

                requestQueue = MySingleton.getInstance(ShopProfile.this).getRequestQueue();
                shopperRepository = new ShopperRepository();
                shopperRepository.updateShopper(token, name_shop, email, phone, shop_address, requestQueue, new ShopperRepository.ObjectCallback() {
                    @Override
                    public void onSuccess(JSONObject shoppers) {
                        Toast.makeText(ShopProfile.this,"Cập nhật thành công", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(ShopProfile.this,"Có lỗi xảy ra khi cập nhật", Toast.LENGTH_SHORT);
                        error.printStackTrace();
                    }
                });
            }
        });
    }
}
