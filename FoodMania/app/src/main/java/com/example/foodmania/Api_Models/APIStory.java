package com.example.foodmania.Api_Models;

import android.graphics.Bitmap;

public class APIStory {
    String encodedImage, caption, remaks, encoded_profile, f_name, l_name;
    int user_id, status_code;

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getRemaks() {
        return remaks;
    }

    public void setRemaks(String remaks) {
        this.remaks = remaks;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getEncoded_profile() {
        return encoded_profile;
    }

    public void setEncoded_profile(String encoded_profile) {
        this.encoded_profile = encoded_profile;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }
}
