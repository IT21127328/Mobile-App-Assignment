package com.example.foodmania.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Activities.MainActivity;
import com.example.foodmania.Api_Models.APICreateProfile;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPostFragment extends Fragment {
    View view;
    int IMG_REQUEST = 21;
    ImageView imageView;
    TextView textview, textview2,location;
    Bitmap bitmap;
    EditText  caption;
    ConstraintLayout add_photo_layout;
    Context applicationContext;
    Button btn_post;
    int user_id, status = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        //oncreate objects
        oncreate_objects();
        //get data bundle
        get_data_bundle();
        applicationContext = MainActivity.getContextOfApplication();

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
        return view;
    }

    private void oncreate_objects() {
        add_photo_layout = view.findViewById(R.id.add_photo_layout);
        imageView = view.findViewById(R.id.imageView);
        textview = view.findViewById(R.id.textView);
        textview2 = view.findViewById(R.id.textView2);
        location = view.findViewById(R.id.txt_Location);
        caption = view.findViewById(R.id.txt_caption);
        btn_post = view.findViewById(R.id.btn_post);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                textview.setVisibility(View.INVISIBLE);
                textview2.setVisibility(View.INVISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        String Title = caption.getText().toString();
        String Location = location.getText().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        Call<APICreateProfile> call = ApiClient.getApiClient().create(ApiInterface.class).upload_post(encodedImage, Title, Location, status, user_id);
        call.enqueue(new Callback<APICreateProfile>() {
            @Override
            public void onResponse(Call<APICreateProfile> call, Response<APICreateProfile> response) {


                if (response.body().getStatus_code() == 1) {
                    Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();

                } else if (response.body().getStatus_code() == 0) {
                    Toast.makeText(getActivity(), "Error While Adding ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Network Error ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APICreateProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");


    }
}