package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodmania.R;

public class ViewCookBook extends AppCompatActivity {
    ImageView image;
    TextView title;
    String encodedimage;
    String Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cook_book);
        oncreate_object();
        get_data();
    }

    private void oncreate_object() {
        image = findViewById(R.id.image);
        title = findViewById(R.id.titile);
    }

    private void get_data() {
        encodedimage = getIntent().getStringExtra("image");
        Title = getIntent().getStringExtra("title");
        byte[] imageInByte = Base64.decode(encodedimage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        image.setImageBitmap(decodedImage);
        title.setText(Title);
    }

}