package com.example.dsidemo.views.MainScreen.Component;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.dsidemo.R;
import com.example.dsidemo.events.CloseSellerRegisterEvent;
import com.example.dsidemo.helpers.RealPathUtil;
import com.example.dsidemo.helpers.helper;
import com.example.dsidemo.repository.UploadIMGRepository;
import com.example.dsidemo.views.MainScreen.MainScreen;
import com.example.dsidemo.views.MainScreen.profiles.UserPorfolio;
import com.example.dsidemo.views.MainScreen.shopManage.AddShopperAvatarActivity;
import com.google.android.material.imageview.ShapeableImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EditManocanhFragment extends Fragment {

    private ConstraintLayout btn_pose, btn_cloth;
    private ImageView img_pose;
    private UploadIMGRepository uploadIMGRepository;
    private Uri imageUri;
    private String nameImg;
    private Bitmap bitmapImg;
    private String path;
    private ActivityResultLauncher<Intent> resultLauncher;
    private ProgressBar loading;

    private boolean isPose = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result != null && result.getData() != null) {
                        imageUri = result.getData().getData();
                        nameImg = helper.getImageName(imageUri, getContext());
                        path = RealPathUtil.getRealPath(getContext(), imageUri);


                        if (isPose) {
                            bitmapImg = BitmapFactory.decodeFile(path);
                            if (img_pose != null) {
                                img_pose.setImageBitmap(bitmapImg);
                                Toast.makeText(getContext(), "Name pose path: " + nameImg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Name Cloth path: " + nameImg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editmanocanh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_pose = view.findViewById(R.id.img_pose);
        btn_pose = view.findViewById(R.id.post_pose_layout);
        btn_cloth = view.findViewById(R.id.post_cloth_layout);
        loading = view.findViewById(R.id.loading);

        loading.setVisibility(INVISIBLE);

        btn_pose.setOnClickListener(v -> {
            isPose = true;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            resultLauncher.launch(intent);
        });

        btn_cloth.setOnClickListener(v -> {
            isPose = false;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            resultLauncher.launch(intent);
        });
    }
}
