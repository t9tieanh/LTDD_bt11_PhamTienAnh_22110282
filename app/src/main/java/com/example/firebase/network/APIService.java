package com.example.firebase.network;

import com.example.firebase.models.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("getvideos.php")
    Call<VideoResponse> getVideos();
}
