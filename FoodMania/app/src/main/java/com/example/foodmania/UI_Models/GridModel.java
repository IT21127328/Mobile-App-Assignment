package com.example.foodmania.UI_Models;

import android.graphics.Bitmap;

public class GridModel {

    Bitmap picture;
    String Title;

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public GridModel(Bitmap picture, String title) {
        this.picture = picture;
        Title = title;
    }
}
