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
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.RealPathUtil;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.api.ApiService;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.models.product;
import com.example.dsidemo.repository.productRepository;
import com.example.dsidemo.utils.MySingleton;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateProductActivity extends AppCompatActivity {
    private ImageView btnback,imgProduct;
    private Button btn_imgUpload,btn_updateProduct;
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
    String postThumb;
    private productRepository productRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_product);

        //Button
        btnback = findViewById(R.id.btn_back);
        btn_imgUpload = findViewById(R.id.btn_imgUpload);
        imgProduct = findViewById(R.id.imgProduct);
        btn_updateProduct = findViewById(R.id.btn_addProduct);

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
        postThumb = getProduct.getProduct_id() + "/" + getProduct.getThumb();

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
        btn_updateProduct.setOnClickListener(v -> {updateProduct();});

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
                            postThumb = getProduct.getUser_id() + "/" + nameImg;
                            bitmapImg = BitmapFactory.decodeFile(path);
                            imgProduct.setImageBitmap(bitmapImg);
                            uplaodIMG();
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
    public void updateProduct(){
        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        productRepository = new productRepository();
        productRepository.updateProduct(String.valueOf(getProduct.getProduct_id()), txt_name.getText().toString(), txt_price.getText().toString(), txt_description.getText().toString(), txt_nameBrand.getText().toString(), postThumb, token, requestQueue, new productRepository.ObjectCallback() {
            @Override
            public void onSuccess(JSONObject products) {
                Toast.makeText(updateProductActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(updateProductActivity.this, ShopManage.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(updateProductActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }
    public void uplaodIMG() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APILinkHelper.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create()).build();
        File file = new File(path);
        RequestBody requestfile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestfile);
        ApiService apiService = retrofit.create(ApiService.class);
        String token = "Bearer " + sharedPreferences.getString("token", "");
        Call<String> call = apiService.uploadImage(token, body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
