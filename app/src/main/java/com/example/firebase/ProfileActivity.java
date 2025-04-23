package com.example.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.firebase.auth.PrefManager;
import com.example.firebase.models.User;
import com.example.firebase.service.UserProfileService;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final String TAG = "Upload ###";

    private ImageView profileImage;
    private Uri selectedImageUri = null;
    private User currentUser;
    UserProfileService userService = new UserProfileService(this);
    TextView tvFullName;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userService = new UserProfileService(this);

        // Hiện nút quay lại
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // setinfo
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        profileImage = findViewById(R.id.profile_image);

        // lâấy thông tin từ pref
        PrefManager prefManager = new PrefManager(ProfileActivity.this);
        currentUser = prefManager.getUser();
        tvFullName.setText("Họ tên: " + currentUser.getFullName());
        tvEmail.setText("Email: " + currentUser.getEmail());
        // Load avatar bằng Glide
        Glide.with(this)
                .load(currentUser.getImgUrl())
                .error(R.drawable.user_profile)
                .circleCrop()
                .into(profileImage);



        initConfig(); // config cloud
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "df3snzgv2");
        config.put("api_key","242516133233971");
        config.put("api_secret","8kYfliJa45faDUqNJdH6-9HimZk");
        MediaManager.init(this, config);
    }

    public void selectImage(View view) {
        // Mở thư viện ảnh
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    // Hàm được gọi khi click "Cập nhật avatar"
    public void uploadAvatar(View view) {
        if (selectedImageUri != null) {
            Toast.makeText(this, "Đã chọn ảnh. (Ở đây bạn có thể upload ảnh lên server)", Toast.LENGTH_SHORT).show();

            MediaManager.get().upload(selectedImageUri).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart: "+"started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart: "+"uploading");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    Log.d(TAG, "onStart: "+"usuccess");

                    String imageUrl = (String) resultData.get("secure_url");
                    // lấy thông tin user
                    PrefManager prefManager = new PrefManager(ProfileActivity.this);
                    User currentUser = prefManager.getUser();
                    userService.updateUserAvatarByEmail(currentUser.getEmail(),imageUrl);
                    // lưu lại
                    prefManager.saveAvartar(imageUrl);
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }
            }).dispatch();
        } else {
            Toast.makeText(this, "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show();
        }
    }

    // Hoặc click trực tiếp vào ảnh đại diện cũng sẽ chọn ảnh
    public void profileImageOnClick(View view) {
        selectImage(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                profileImage.setImageURI(selectedImageUri); // Gán ảnh đã chọn vào ImageView
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Đóng Activity hiện tại
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
