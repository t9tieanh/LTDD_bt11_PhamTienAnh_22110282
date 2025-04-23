package com.example.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.firebase.adapter.VideoAdapter;
import com.example.firebase.models.VideoResponse;
import com.example.firebase.models.Video;
import com.example.firebase.network.APIService;
import com.example.firebase.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Retrofit extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideoAdapter videosAdapter;
    private List<Video> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.vpager);
        list = new ArrayList<>();

        getVideos();
    }

    private void getVideos() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getVideos().enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = response.body().getResult();
                    videosAdapter = new VideoAdapter(getApplicationContext(), list);
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    viewPager2.setAdapter(videosAdapter);
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
