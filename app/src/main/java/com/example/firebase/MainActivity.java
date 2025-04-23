package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.firebase.adapter.VideoFireBaseAdapter;
import com.example.firebase.auth.PrefManager;
import com.example.firebase.models.User;
import com.example.firebase.models.Video;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideoFireBaseAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        PrefManager prefManager = new PrefManager(MainActivity.this);
        User currentUser = prefManager.getUser();

        if (!prefManager.isUserLoggedIn()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            ImageView ivProfile = findViewById(R.id.ivProfile);
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    finish();
                }}
            );

            TextView tvWelcome = findViewById(R.id.tvWelcome);

            // Load avatar (circle crop) nếu có URL
            if (currentUser.getImgUrl() != null && !currentUser.getImgUrl().isEmpty()) {
                Glide.with(this)
                        .load(currentUser.getImgUrl())
                        .circleCrop()
                        .into(ivProfile);
            } else {
                // Dùng placeholder mặc định
                ivProfile.setImageResource(R.drawable.user_profile);
            }
            tvWelcome.setText("Welcome, " + currentUser.getFullName());
        }

        // xử lý sự kiện nút logout
        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.clearUserData();

                // Ví dụ: chuyển sang màn hình đăng nhập
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        viewPager2 = findViewById(R.id.vpager);
        getVideos();
    }


    private void getVideos() {
        // Kết nối đến Realtime Database node "videos"
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference("videos");

        // Tạo tùy chọn cho FirebaseRecyclerAdapter
        FirebaseRecyclerOptions<Video> options =
                new FirebaseRecyclerOptions.Builder<Video>()
                        .setQuery(mDataBase, Video.class)
                        .build();

        // Gắn adapter cho ViewPager2
        videosAdapter = new VideoFireBaseAdapter(options);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL); // Vuốt dọc
        viewPager2.setAdapter(videosAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (videosAdapter != null) {
            videosAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videosAdapter != null) {
            videosAdapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videosAdapter != null) {
            videosAdapter.notifyDataSetChanged();
        }
    }
}
