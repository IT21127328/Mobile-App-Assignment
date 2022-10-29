package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.foodmania.R;

public class SplashActivity extends AppCompatActivity {

    private VideoView videov;
    private MediaController mdiaC;
    private int count = 0;
    private Animation top;
    private TextView name;
    private static int SPLASH_SCREEN = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Oncreate Components
        oncreate_objects();

        //loop video
        videov.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        //play video
        set_video();


        //delay screen
        delay();

    }

    //Delay Screen
    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }

    //set video components
    private void set_video() {
        String videopath = "android.resource://com.example.foodmania/" + R.raw.vid;
        Uri uri = Uri.parse(videopath);
        videov.setVideoURI(uri);
        videov.start();

    }

    //oncreate objects
    private void oncreate_objects() {
        videov = findViewById(R.id.video);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        name = findViewById(R.id.text_food);
        name.setAnimation(top);

    }

}