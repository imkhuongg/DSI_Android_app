package com.example.dsidemo.views.MainScreen.shopManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.dsidemo.utils.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class addProduct extends Fragment {
    private ImageView btnback,imgProduct;
    private Button btn_imgUpload,btn_addProduct;
    private ActivityResultLauncher<Intent> resultLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private String nameImg;
    private EditText txt_name,txt_price,txt_description,txt_nameBrand;
    private Bitmap bitmapImg;

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_add_product , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Button
        btnback = view.findViewById(R.id.btn_back);
        btn_imgUpload = view.findViewById(R.id.btn_imgUpload);
        imgProduct = view.findViewById(R.id.imgProduct);
        btn_addProduct = view.findViewById(R.id.btn_addProduct);

        //EditText
        txt_description = view.findViewById(R.id.txt_description);
        txt_name = view.findViewById(R.id.txt_name);
        txt_price = view.findViewById(R.id.txt_price);
        txt_nameBrand = view.findViewById(R.id.txt_nameBrand);

        //Effect
        helper.setTouchEffect(btnback);


        //OnClịckEvent
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        registerResult();
        btn_imgUpload.setOnClickListener(v -> openFileChooser());


        sharedPreferences = getActivity().getSharedPreferences(StringResourceHelper.getUserDetailPrefName() , Context.MODE_PRIVATE);

        requestQueue = MySingleton.getInstance(getActivity().getBaseContext()).getRequestQueue();

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createProduct();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        resultLauncher.launch(intent);

    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            imageUri = o.getData().getData();

                            nameImg = getImageName(imageUri);

                            imgProduct.setVisibility(View.VISIBLE);
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                            bitmapImg = BitmapFactory.decodeStream(inputStream);
                            imgProduct.setImageBitmap(bitmapImg);
                            Toast.makeText(getActivity().getBaseContext(), "name IMG: " + nameImg, Toast.LENGTH_SHORT).show();
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity().getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getImageName(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String imageName = cursor.getString(columnIndex);
            cursor.close();
            return imageName;
        } else {
            return null;
        }
    }

    public void createProduct(){
        String nameProduct = txt_name.getText().toString();
        String price = txt_price.getText().toString();
        String productDescription = txt_description.getText().toString();
        String nameBrand = txt_nameBrand.getText().toString();
        String thumb = nameImg;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APILinkHelper.createProduct(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gotoMangeProduct();
                Log.i("CreateProductActivity" , response.toString());
                Toast.makeText(getActivity().getBaseContext(), "Thêm sản phẩm mới thành công!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getBaseContext(), "Có lỗi xảy ra! Không thể tạo sản phẩm mới", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("name_product" , nameProduct);
                params.put("price" , price);
                params.put("description",productDescription);
                params.put("name_brand" , nameBrand);
                params.put("thumb", thumb);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                Map<String,String> header = new HashMap<>();
                header.put("Authorization" , "Bearer " + token);
                return header;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void gotoMangeProduct(){
        requireActivity().getSupportFragmentManager().popBackStack();
    }



}

