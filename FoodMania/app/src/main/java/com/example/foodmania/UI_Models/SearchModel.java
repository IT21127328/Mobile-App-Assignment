package com.example.foodmania.UI_Models;

import android.graphics.Bitmap;

public class SearchModel {

  Bitmap profile_pic;
    String username,designation;
    int id;

    public SearchModel(Bitmap profile_pic, String username, String designation, int id) {
        this.profile_pic = profile_pic;
        this.username = username;
        this.designation = designation;
        this.id = id;
    }


    public Bitmap getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Bitmap profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
