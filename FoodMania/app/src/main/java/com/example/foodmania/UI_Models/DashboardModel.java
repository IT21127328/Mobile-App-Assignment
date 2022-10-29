package com.example.foodmania.UI_Models;

import android.graphics.Bitmap;

public class DashboardModel {

Bitmap dashboardImg;
    Bitmap profile ;
    String user_name, bio, like, comment, share,cap;

    public DashboardModel(Bitmap profile, Bitmap dashboardImg, String user_name, String bio, String like, String comment, String share,String cap) {

        this.profile = profile;
        this.dashboardImg = dashboardImg;
        this.user_name = user_name;
        this.bio = bio;
        this.like = like;
        this.comment = comment;
        this.share = share;
        this.cap=cap;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public Bitmap getDashboardImg() {
        return dashboardImg;
    }

    public void setDashboardImg(Bitmap dashboardImg) {
        this.dashboardImg = dashboardImg;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
