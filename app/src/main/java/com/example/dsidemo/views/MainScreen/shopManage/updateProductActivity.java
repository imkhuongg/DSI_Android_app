package com.example.dsidemo.views.MainScreen.shopManage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.RealPathUtil;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.utils.MySingleton;

public class updateProductActivity extends AppCompatActivity {
    private ImageView btnback,imgProduct;
    private Button btn_imgUpload,btn_addProduct;
    private ActivityResultLauncher<Intent> resultLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private String nameImg;
    private EditText txt_name,txt_price,txt_description,txt_nameBrand;
    private Bitmap bitmapImg;
    ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;

    private String path;
    private product getProduct;
    private int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_product);

        //Button
        btnback = findViewById(R.id.btn_back);
        btn_imgUpload = findViewById(R.id.btn_imgUpload);
        imgProduct = findViewById(R.id.imgProduct);
        btn_addProduct = findViewById(R.id.btn_addProduct);

        //EditText
        txt_description = findViewById(R.id.txt_description);
        txt_name = findViewById(R.id.txt_name);
        txt_price = findViewById(R.id.txt_price);
        txt_nameBrand = findViewById(R.id.txt_nameBrand);

        progressBar = findViewById(R.id.progressBar);

        //Effect
        helper.setTouchEffect(btnback);
        helper.hideSystemUI(getWindow().getDecorView());

   //     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        //setValue
        Intent intent = getIntent();
        getProduct = (product) intent.getSerializableExtra("productModel");

        txt_name.setText(getProduct.getName_product());
        txt_price.setText(String.valueOf(getProduct.getPrice()));
        txt_nameBrand.setText(getProduct.getName_brand());
        txt_description.setText(getProduct.getDescription());
        imgProduct.setVisibility(View.VISIBLE);

        Glide.with(updateProductActivity.this)
                .load(APILinkHelper.getIMG()+getProduct.getThumb())
                .into(imgProduct);

        registerResult();
        //OnClịckEvent
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_imgUpload.setOnClickListener(v -> {
            openFileChooser();
        });

        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);
        requestQueue = MySingleton.getInstance(getBaseContext()).getRequestQueue();



    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }
    private void registerResult(){

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o != null && o.getData() != null) {
                            imageUri = o.getData().getData();

                            nameImg = getImageName(imageUri);

                            imgProduct.setVisibility(View.VISIBLE);
                            path = RealPathUtil.getRealPath(updateProductActivity.this , imageUri);

                            bitmapImg = BitmapFactory.decodeFile(path);
                            imgProduct.setImageBitmap(bitmapImg);

                            Toast.makeText(getBaseContext(), "name IMG: " + nameImg, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private String getImageName(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String imageName = cursor.getString(columnIndex);
            cursor.close();
            return imageName;
        } else {
            return null;
        }
    }
}
