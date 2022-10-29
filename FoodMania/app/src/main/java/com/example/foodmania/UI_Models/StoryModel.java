package com.example.foodmania.UI_Models;

import android.graphics.Bitmap;

public class StoryModel {

    Bitmap profile,story;
    String caption,f_name,l_name;
    public StoryModel(Bitmap profile, Bitmap story, String caption, String f_name, String l_name) {
        this.profile = profile;
        this.story = story;
        this.caption = caption;
        this.f_name = f_name;
        this.l_name = l_name;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public Bitmap getStory() {
        return story;
    }

    public void setStory(Bitmap story) {
        this.story = story;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
