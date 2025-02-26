package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dsidemo.R;
import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.RealPathUtil;
import com.example.dsidemo.helpers.StringResourceHelper;
import com.example.dsidemo.helpers.api.ApiService;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.utils.MySingleton;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class addProduct extends AppCompatActivity {
    private ImageView btnback, imgProduct;
    private Button btn_imgUpload, btn_addProduct;
    private ActivityResultLauncher<Intent> resultLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private String nameImg;
    private EditText txt_name, txt_price, txt_description, txt_nameBrand;
    private Bitmap bitmapImg;
    ProgressBar progressBar;

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;

    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_product);

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

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        //OnClịckEvent
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerResult();
        btn_imgUpload.setOnClickListener(v -> openFileChooser());

        sharedPreferences = getSharedPreferences(StringResourceHelper.getUserDetailPrefName(), Context.MODE_PRIVATE);

        requestQueue = MySingleton.getInstance(getBaseContext()).getRequestQueue();

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                btn_addProduct.setEnabled(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uplaodIMG();
                        createProduct();
                        progressBar.setVisibility(View.GONE);
                        btn_addProduct.setEnabled(true);
                    }
                }, 2000);

            }
        });

    }


    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }


    private void registerResult() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o != null && o.getData() != null) {

                            imageUri = o.getData().getData();

                            nameImg = getImageName(imageUri);

                            imgProduct.setVisibility(View.VISIBLE);
                            path = RealPathUtil.getRealPath(addProduct.this, imageUri);

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

    public void createProduct() {
        String nameProduct = txt_name.getText().toString();
        String price = txt_price.getText().toString();
        String productDescription = txt_description.getText().toString();
        String nameBrand = txt_nameBrand.getText().toString();
        String thumb = nameImg;
        SharedPreferences sharedPreferences1 =  getSharedPreferences(StringResourceHelper.getShopperInfo(), MODE_PRIVATE);
        int shopper_id = sharedPreferences1.getInt("shopper_id",0);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name_product", nameProduct);
            jsonBody.put("price", price);
            jsonBody.put("description", productDescription);
            jsonBody.put("name_brand", nameBrand);
            jsonBody.put("thumb", thumb);
            jsonBody.put("shopper_id", shopper_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APILinkHelper.Product(), jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(addProduct.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(addProduct.this, ShopManage.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addProduct.this, "Có lỗi xảy ra khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + token);
                return header;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void gotoMangeProduct() {
        Intent intent = new Intent(this, ShopManage.class);
        startActivity(intent);
        finish();
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
                if (response.isSuccessful()) {
                    Toast.makeText(addProduct.this, "OK", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(addProduct.this, "!OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}

