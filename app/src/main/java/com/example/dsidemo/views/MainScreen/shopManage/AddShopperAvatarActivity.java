package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.RealPathUtil;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.repository.ShopperRepository;
import com.example.dsidemo.repository.UploadIMGRepository;
import com.example.dsidemo.utils.MySingleton;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddShopperAvatarActivity extends AppCompatActivity {
    private Button btn_save,btn_chooseFile;
    private CircleImageView img_avatar;
    private ImageView btn_back;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Bitmap bitmapImg;
    ProgressBar progressBar;
    private Uri imageUri;
    private String nameImg;
    private helper Helper;
    private String path;
    private UploadIMGRepository uploadIMGRepository;
    private ShopperRepository shopperRepository;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_avatar_shop);

        //BUTTON
        btn_save = findViewById(R.id.btn_save);
        btn_chooseFile = findViewById(R.id.btn_chooseFile);
        //IMAGE
        img_avatar = findViewById(R.id.img_avatar);
        btn_back = findViewById(R.id.btn_back);
        registerResult();

        helper.hideSystemUI(getWindow().getDecorView());
        helper.setTouchEffect(btn_back);


        sharedPreferences = getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);
        String imgPath = sharedPreferences.getString("avatar", "");
        if(imgPath.isEmpty() || imgPath == null){
            img_avatar.setImageResource(R.drawable.icon_avatar_default);
        } else{
            Glide
                    .with(this)
                    .load(APILinkHelper.getIMG() + imgPath)
                    .transform(new CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img_avatar);
            img_avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.openFileChooser(resultLauncher);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(path!= null && !path.isEmpty()){
                uploadIMGRepository = new UploadIMGRepository();
                    sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , MODE_PRIVATE);
                uploadIMGRepository.uploadIMG(AddShopperAvatarActivity.this, path,sharedPreferences);
                    Toast.makeText(AddShopperAvatarActivity.this, "Upload successfull", Toast.LENGTH_SHORT).show();
                }
                uploadIMGShopper();
            }
        });

    }
    private void registerResult() {

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o != null && o.getData() != null) {

                            imageUri = o.getData().getData();

                            nameImg = helper.getImageName(imageUri, AddShopperAvatarActivity.this);

                            path = RealPathUtil.getRealPath(AddShopperAvatarActivity.this, imageUri);

                            bitmapImg = BitmapFactory.decodeFile(path);
                            img_avatar.setImageBitmap(bitmapImg);



                        }
                    }
                });
    }
    public void uploadIMGShopper(){
        if(path != null || !path.isEmpty()){
        shopperRepository  = new ShopperRepository();
        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , MODE_PRIVATE);
        int id = sharedPreferences.getInt("user_id", 0);
        String imgPath = id + "/" + nameImg;
        String token = sharedPreferences.getString("token", "");
        requestQueue =  MySingleton.getInstance(getBaseContext()).getRequestQueue();
        shopperRepository.UpdateAvatar(token, imgPath, requestQueue, new ShopperRepository.StringCallback() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddShopperAvatarActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences1 = getSharedPreferences(StringResourceHelper.getShopperInfo(),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("avatar", imgPath);
                editor.apply();
                Intent intent = new Intent(AddShopperAvatarActivity.this, ShopProfile.class);
                finish();
                startActivity(intent);

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddShopperAvatarActivity.this, "fail", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        }
    }
}
