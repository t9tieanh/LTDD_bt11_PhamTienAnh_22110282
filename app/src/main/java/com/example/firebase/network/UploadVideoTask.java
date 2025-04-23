package com.example.firebase.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.firebase.models.Video;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.Map;

public class UploadVideoTask extends AsyncTask<Void, Void, Video> {
    private Context context;
    private String userEmail;
    private Uri videoUri;
    private String name;
    private String desc;
    private OnUploadSuccessListener listener;

    public interface OnUploadSuccessListener {
        void onUploadSuccess(Video video);
    }

    public UploadVideoTask(Context context, String userEmail, Uri videoUri, String name, String desc, OnUploadSuccessListener listener) {
        this.context = context;
        this.userEmail = userEmail;
        this.videoUri = videoUri;
        this.name = name;
        this.desc = desc;
        this.listener = listener;
    }

    @Override
    protected Video doInBackground(Void... voids) {
        try {
            // Khởi tạo Cloudinary
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "df3snzgv2",
                    "api_key", "242516133233971",
                    "api_secret", "8kYfliJa45faDUqNJdH6-9HimZk"
            ));

            // Upload video
            InputStream inputStream = context.getContentResolver().openInputStream(videoUri);
            Map uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.asMap(
                    "resource_type", "video",
                    "folder", "short_videos"
            ));
            String videoUrl = (String) uploadResult.get("secure_url");

            return new Video(name,desc,videoUrl,userEmail,"",0,0);

        } catch (Exception e) {
            Log.e("UploadVideoTask", "Upload failed", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Video video) {
        super.onPostExecute(video);

        if (video != null) {

            // lưu vào db
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("videos");
            String key = ref.push().getKey();
            ref.child(key).setValue(video);

            Toast.makeText(context, "Tải video thành công!", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUploadSuccess(video);
            }
        } else {
            Toast.makeText(context, "Tải video thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}

