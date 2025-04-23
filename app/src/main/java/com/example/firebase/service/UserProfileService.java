package com.example.firebase.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserProfileService {

    private static final String TAG = "UserProfileService";
    private final FirebaseFirestore db;
    private final Context context;

    public UserProfileService(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /**
     * Cập nhật ảnh đại diện theo email người dùng
     */
    public void updateUserAvatarByEmail(String email, String imageUrl) {
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshots -> {
                    if (!querySnapshots.isEmpty()) {
                        // Lấy document đầu tiên khớp email
                        DocumentSnapshot document = querySnapshots.getDocuments().get(0);
                        String docId = document.getId();

                        Map<String, Object> updates = new HashMap<>();
                        updates.put("imgUrl", imageUrl);

                        db.collection("users")
                                .document(docId)
                                .set(updates, SetOptions.merge())
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(context, "Cập nhật ảnh thành công!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Updated avatar for: " + email);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Cập nhật ảnh thất bại!", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Update failed", e);
                                });
                    } else {
                        Toast.makeText(context, "Không tìm thấy người dùng với email này", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "User not found");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi truy vấn Firestore", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Query failed", e);
                });
    }

    /**
     * Lấy ảnh đại diện theo email
     */
    public void getUserAvatarByName(String email, OnSuccessListener<String> onSuccess, OnFailureListener onFailure) {
        db.collection("users")
                .whereEqualTo("fullName", email)
                .get()
                .addOnSuccessListener(querySnapshots -> {
                    if (!querySnapshots.isEmpty()) {
                        DocumentSnapshot document = querySnapshots.getDocuments().get(0);
                        String imageUrl = document.getString("imgUrl");
                        onSuccess.onSuccess(imageUrl);
                    } else {
                        onFailure.onFailure(new Exception("User not found"));
                    }
                })
                .addOnFailureListener(onFailure);
    }
}