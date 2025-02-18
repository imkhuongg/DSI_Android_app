package com.example.dsidemo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.api.ApiService;
import com.example.dsidemo.views.MainScreen.shopManage.addProduct;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadIMGRepository {


    public void uploadIMG(Context context,String path, SharedPreferences sharedPreferences) {
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
                if (response.isSuccessful()) Toast.makeText(context, "OK IMG", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "!OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "cook", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }




}
