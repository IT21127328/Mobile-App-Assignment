package com.example.foodmania.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Api_Models.APIStory;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Story extends AppCompatActivity {

    Bitmap bitmap;
    int IMG_REQUEST = 21;
    int user_id;
    TextView caption;
    ImageView imageView;
    ConstraintLayout add_photo_layout;
    Button btn_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        create_data();
        oncreate_objects();
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        add_photo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        String Title = caption.getText().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        Call<APIStory> call = ApiClient.getApiClient().create(ApiInterface.class).savestory(encodedImage, Title, user_id);
        System.out.println(user_id + Title + encodedImage);
        call.enqueue(new Callback<APIStory>() {
            @Override
            public void onResponse(Call<APIStory> call, Response<APIStory> response) {

                if (response.body().getStatus_code() == 1) {

                    Toast.makeText(getApplicationContext(), "Added Successfully ", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.body().getStatus_code() == 0) {
                    Toast.makeText(getApplicationContext(), "Error While Adding ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIStory> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void oncreate_objects() {

        imageView = findViewById(R.id.imageView);
        caption = findViewById(R.id.txt_story_caption);
        add_photo_layout = findViewById(R.id.add_photo_layout);
        btn_post = findViewById(R.id.btn_post);

    }

    void create_data() {
        user_id = getIntent().getIntExtra("user_id", 0);
    }
}