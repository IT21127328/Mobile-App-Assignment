package com.example.foodmania.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Activities.Add_Story;
import com.example.foodmania.Adapters.DashboardAdapter;
import com.example.foodmania.Adapters.StoryAdapter;
import com.example.foodmania.Api_Models.APIStory;
import com.example.foodmania.Api_Models.AllPosts;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.DashboardModel;
import com.example.foodmania.UI_Models.StoryModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    CircleImageView profile_pic;
    View view;
    TextView username;
    String fname, lname, encoded_Image;
    RecyclerView dashboardRV, storyRV;
    ArrayList<DashboardModel> dashboardList;
    RoundedImageView addStoryImg;
    ImageView addStory;
    ArrayList<StoryModel> storyList;
    int user_id;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //get data
        get_data_bundle();

        //on create objects
        oncreate_object();

        get_all_stories();

        //set Components
        set_component_values();

        //Set Profile Image
        set_profile_pic();

        //Get All Posts
        get_all_posts();

        //Add Story
        start_activity_story();
        start_activity_story2();

        return view;
    }

    private void start_activity_story2() {
        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_story();
            }
        });
    }

    private void start_activity_story() {
        addStoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_story();
            }
        });
    }

    private void add_story() {
        Intent intent = new Intent(getActivity(), Add_Story.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

    private void set_profile_pic() {
        byte[] imageInByte = Base64.decode(encoded_Image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        profile_pic.setImageBitmap(decodedImage);
    }

    private void get_data_bundle() {
        fname = getArguments().getString("f_name");
        lname = getArguments().getString("l_name");
        encoded_Image = getArguments().getString("encodedImage");
        user_id = getArguments().getInt("user_id", 0);

    }

    private void oncreate_object() {

        username = (view).findViewById(R.id.txt_username);
        profile_pic = view.findViewById(R.id.profile_image);
        dashboardRV = view.findViewById(R.id.dashboardRV);
        addStoryImg = view.findViewById(R.id.addStoryImg);
        addStory = view.findViewById(R.id.addStory);
        storyRV = view.findViewById(R.id.storyRV);

    }

    private void set_component_values() {
        username.setText(fname + " " + lname);
    }

    private void get_all_stories() {
        storyList = new ArrayList<>();
        Call<List<APIStory>> call = ApiClient.getApiClient().create(ApiInterface.class).getstory();
        call.enqueue(new Callback<List<APIStory>>() {
            @Override
            public void onResponse(Call<List<APIStory>> call, Response<List<APIStory>> response) {
                if (response.isSuccessful()) {
                    List<APIStory> data = response.body();
                    for (int i = 0; i < data.size(); i++) {
                        String encodedImage = data.get(i).getEncodedImage();
                        String caption = data.get(i).getCaption();
                        String encoded_profile = data.get(i).getEncoded_profile();
                        String f_name = data.get(i).getF_name();
                        String l_name = data.get(i).getL_name();
                        System.out.println(l_name);
                        byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                        byte[] profileimageInByte = Base64.decode(encoded_profile, Base64.DEFAULT);
                        Bitmap decodedProfileImage = BitmapFactory.decodeByteArray(profileimageInByte, 0, profileimageInByte.length);
                        storyList.add(new StoryModel(decodedProfileImage, decodedImage, caption, f_name, l_name));
                        StoryAdapter adapter = new StoryAdapter(storyList, getContext());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                        storyRV.setLayoutManager(layoutManager);
                        //storyRV.setNestedScrollingEnabled(false);
                        storyRV.setAdapter(adapter);

                    }

                } else {
                    Toast.makeText(getActivity(), " Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<APIStory>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void get_all_posts() {

        dashboardList = new ArrayList<>();
        Call<List<AllPosts>> call = ApiClient.getApiClient().create(ApiInterface.class).getall();
        call.enqueue(new Callback<List<AllPosts>>() {
            @Override
            public void onResponse(Call<List<AllPosts>> call, Response<List<AllPosts>> response) {
                List<AllPosts> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    int post_id = data.get(i).getPost_id();
                    int status = data.get(i).getStatus();
                    int user_id = data.get(i).getUser_id();
                    String post_caption = data.get(i).getCaption();
                    String location = data.get(i).getLocation();
                    String post_date = data.get(i).getPost_date();
                    String f_name = data.get(i).getF_name();
                    String l_name = data.get(i).getL_name();
                    String email = data.get(i).getEmail();
                    String encodedImage = data.get(i).getEncodedImage();
                    String designation = data.get(i).getDesignation();
                    String description = data.get(i).getDescription();
                    String encodedprofile = data.get(i).getEncodedprofile();

                    byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    byte[] profileimageInByte = Base64.decode(encodedprofile, Base64.DEFAULT);
                    Bitmap decodedProfileImage = BitmapFactory.decodeByteArray(profileimageInByte, 0, profileimageInByte.length);

                    dashboardList.add(new DashboardModel(decodedProfileImage, decodedImage, f_name + " " + l_name, location, "247", "57", "33", post_caption));
                    DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    dashboardRV.setLayoutManager(linearLayoutManager);
                    dashboardRV.addItemDecoration(new DividerItemDecoration(dashboardRV.getContext(), DividerItemDecoration.VERTICAL));
                    dashboardRV.setNestedScrollingEnabled(false);
                    dashboardRV.setAdapter(dashboardAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AllPosts>> call, Throwable t) {

            }
        });
    }
}