package com.example.foodmania.UI_Models;

import android.graphics.Bitmap;

public class NotificationsModel {
    Bitmap profile;
    String username,content;

    public NotificationsModel(Bitmap profile, String username, String content) {
        this.profile = profile;
        this.username = username;
        this.content = content;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
