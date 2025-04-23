package com.example.firebase.models;

public class User {
    private String fullName;
    private String email;
    private String imgUrl;

    // Constructor
    public User(String fullName, String email, String imgUrl) {
        this.fullName = fullName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    // Getter và Setter
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}