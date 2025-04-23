package com.example.firebase.models;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String desc;
    private String url;
    private String uploaderName;
    private String uploaderAvatarUrl;
    private int likeCount;
    private int dislikeCount;

    public Video() {
        // Bắt buộc có constructor rỗng để Firebase mapping
    }

    public Video(String title, String desc, String url, String uploaderName, String uploaderAvatarUrl, int likeCount, int dislikeCount) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.uploaderName = uploaderName;
        this.uploaderAvatarUrl = uploaderAvatarUrl;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getUrl() { return url; }
    public String getUploaderName() { return uploaderName; }
    public String getUploaderAvatarUrl() { return uploaderAvatarUrl; }
    public int getLikeCount() { return likeCount; }
    public int getDislikeCount() { return dislikeCount; }

    public void setTitle(String title) { this.title = title; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setUrl(String url) { this.url = url; }
    public void setUploaderName(String uploaderName) { this.uploaderName = uploaderName; }
    public void setUploaderAvatarUrl(String uploaderAvatarUrl) { this.uploaderAvatarUrl = uploaderAvatarUrl; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
}
