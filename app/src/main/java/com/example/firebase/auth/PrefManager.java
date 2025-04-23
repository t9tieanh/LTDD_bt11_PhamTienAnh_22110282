package com.example.firebase.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firebase.models.User;

public class PrefManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "user_pref";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMG_URL = "img_url";

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin người dùng
    public void saveUser(User user) {
        editor.putString(KEY_FULL_NAME, user.getFullName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_IMG_URL, user.getImgUrl());
        editor.apply();
    }


    //
    public void saveAvartar (String imgUrl) {
        editor.putString(KEY_IMG_URL, imgUrl);
        editor.apply();
    }


    // Lấy thông tin người dùng
    public User getUser() {
        String fullName = sharedPreferences.getString(KEY_FULL_NAME, "");
        String email = sharedPreferences.getString(KEY_EMAIL, "");
        String imgUrl = sharedPreferences.getString(KEY_IMG_URL, "");
        return new User(fullName, email, imgUrl);
    }

    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_EMAIL);
    }

    // Xóa thông tin người dùng khi đăng xuất
    public void clearUserData() {
        editor.clear();
        editor.apply();
    }
}