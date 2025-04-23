package com.example.firebase.models;

import java.io.Serializable;
import java.util.List;

public class VideoResponse implements Serializable {
    private boolean success;
    private String message;
    private List<Video> result;

    public VideoResponse() {
    }

    // Constructor đầy đủ
    public VideoResponse(boolean success, String message, List<Video> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Video> getResult() {
        return result;
    }

    public void setResult(List<Video> result) {
        this.result = result;
    }
}
