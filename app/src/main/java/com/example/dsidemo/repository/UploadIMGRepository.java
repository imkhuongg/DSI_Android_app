package com.example.dsidemo.repository;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dsidemo.helpers.APILinkHelper;
import com.example.dsidemo.helpers.api.ApiService;
import com.example.dsidemo.views.MainScreen.shopManage.addProduct;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadIMGRepository {



    public void uploadIMG(Context context, String path, SharedPreferences sharedPreferences ) {
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

    public void uploadIMGs(Context context, String path1, String path2 , ProgressBar progressBar) {
        progressBar.setVisibility(VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILinkHelper.getBaseURL()) // nhớ có "/" cuối URL base
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo Retrofit service
        ApiService apiService = retrofit.create(ApiService.class);

        // Chuẩn bị 2 file ảnh
        File file1 = new File(path1);
        File file2 = new File(path2);

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/*"), file2);

        MultipartBody.Part body1 = MultipartBody.Part.createFormData("file", file1.getName(), requestFile1);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("file", file2.getName(), requestFile2);

        // Gom 2 file vào list
        List<MultipartBody.Part> fileList = new ArrayList<>();
        fileList.add(body1);
        fileList.add(body2);

        // Gọi API
        Call<String> call = apiService.uploadImages(fileList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Upload thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Upload thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }





}
