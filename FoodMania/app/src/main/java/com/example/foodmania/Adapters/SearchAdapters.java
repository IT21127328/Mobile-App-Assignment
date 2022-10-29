package com.example.foodmania.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmania.Activities.ExternalProfileActivity;
import com.example.foodmania.Activities.MainActivity;
import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Models.APIResponseProfile;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.SearchModel;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapters extends RecyclerView.Adapter<SearchAdapters.viewHolder> {
    ArrayList<SearchModel> list;
    Context context;
    SearchModel model;

    int user_id;
    String f_name;
    String l_name;
    String email;
    String password;
    int user_status;
    int user_catergory_id;
    int sender_id;
    int profile_id, status;
    String designation, description, DOB, encodedImage;

    public SearchAdapters(ArrayList<SearchModel> list, Context context, int sender_id) {
        this.context = context;
        this.list = list;
        this.sender_id = sender_id;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_result, parent, false);
        return new viewHolder(view);
    }

    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        model = list.get(position);
        holder.profile_pic.setImageBitmap(model.getProfile_pic());
        holder.username.setText(model.getUsername());
        holder.id.setText(Integer.toString(model.getId()));
        holder.designation.setText(model.getDesignation());
        holder.profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = Integer.parseInt(holder.id.getText().toString());
                Show_External_Profile();
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_id = Integer.parseInt(holder.id.getText().toString());
                Show_External_Profile();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView username, id, designation;
        CircleImageView profile_pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile_pic = itemView.findViewById(R.id.img_profile);
            username = itemView.findViewById(R.id.txt_username);
            id = itemView.findViewById(R.id.txt_id);
            designation = itemView.findViewById(R.id.txt_designation);
        }
    }


    public void Show_External_Profile() {


        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).get_user_details(user_id);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {

                            f_name = response.body().getF_name();
                            l_name = response.body().getL_name();
                            email = response.body().getEmail();
                            password = response.body().getPassword();
                            user_status = response.body().getUser_status();
                            user_catergory_id = response.body().getUser_catergory_id();

                            //Get User Profile
                            Call<APIResponseProfile> call1 = ApiClient.getApiClient().create(ApiInterface.class).get_profile(user_id);
                            call1.enqueue(new Callback<APIResponseProfile>() {
                                @Override
                                public void onResponse(Call<APIResponseProfile> call1, Response<APIResponseProfile> response) {
                                    APIResponseProfile profile = response.body();
                                    if (profile != null) {
                                        profile_id = profile.getProfile_id();
                                        status = profile.getStatus();
                                        designation = profile.getDesignation();
                                        description = profile.getDescription();
                                        DOB = profile.getDOB();
                                        encodedImage = profile.getEncodedImage();
                                        Intent intent = new Intent(context, ExternalProfileActivity.class);
                                        intent.putExtra("user_id", user_id);
                                        intent.putExtra("f_name", f_name);
                                        intent.putExtra("l_name", l_name);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", password);
                                        intent.putExtra("user_status", user_status);
                                        intent.putExtra("user_catergory_id", user_catergory_id);
                                        intent.putExtra("profile_id", profile_id);
                                        intent.putExtra("status", status);
                                        intent.putExtra("encodedImage", encodedImage);
                                        intent.putExtra("description", description);
                                        intent.putExtra("designation", designation);
                                        intent.putExtra("DOB", DOB);
                                        intent.putExtra("sender_id", sender_id);
                                        context.startActivity(intent);


                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "Invalid Profile Details", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<APIResponseProfile> call1, Throwable t) {
                                    Toast.makeText(context.getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            Toast.makeText(context.getApplicationContext(), "Incorrect user ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(context.getApplicationContext(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "Network Error ! ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });


    }


}
