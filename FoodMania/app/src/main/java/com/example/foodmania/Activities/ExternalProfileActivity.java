package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Adapters.CookBookGridAdapter;
import com.example.foodmania.Api_Models.APIFriend;
import com.example.foodmania.Api_Models.APIGetOwnPosts;
import com.example.foodmania.Api_Models.APIResponseProfile;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.GridModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExternalProfileActivity extends AppCompatActivity {
    String f_name, l_name, email, password;
    int user_catergory_id, user_id, user_status;
    public static Context contextOfApplication;
    //Bundle bundle = new Bundle();
    int profile_id;
    int status;
    String designation;
    String description;
    String DOB;
    String encodedImage;
    TextView txt_username_profile, txt_description,txt_designation;
    GridView gridView;
    ArrayList<GridModel> grid;
    int sender_id;
    CircleImageView profile_pic;
    Button friend_req;
    String content = "Sent a Friend Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_profile);
        contextOfApplication = getApplicationContext();
        //Bind Data
        Bind_Data();
        //On Create Object
        on_create_objects();
        //Check Friend request
        check_friend();
        //Set Values
        set_values();
        //Get Post
        get_all_posts();

        friend_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (friend_req.getText().toString().equals("Add Friend")) {
                    //Send Requests
                    send_request();
                    send_notification();
                } else {
                    //Cancel Requests
                    delete_request();
                    delete_notofication();
                }

            }
        });

    }

    private void delete_request() {
        Call<APIFriend> call = ApiClient.getApiClient().create(ApiInterface.class).Delete_req(sender_id, user_id);
        call.enqueue(new Callback<APIFriend>() {
            @Override
            public void onResponse(Call<APIFriend> call, Response<APIFriend> response) {
                if (response.body().getStatus().equals("ok")) {
                    if (response.body().getResult() == 1) {
                        friend_req.setText("Add Friend");
                        Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_LONG).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<APIFriend> call, Throwable t) {

            }
        });
    }

    private void send_notification() {
        Call<APIFriend> call = ApiClient.getApiClient().create(ApiInterface.class).Send_Notification(sender_id, user_id, content);
        call.enqueue(new Callback<APIFriend>() {
            @Override
            public void onResponse(Call<APIFriend> call, Response<APIFriend> response) {

                if (response.body().getStatus().equals("ok")) {
                    if (response.body().getResult() == 1) {
                        Toast.makeText(getApplicationContext(), "Notify Successfully", Toast.LENGTH_LONG).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<APIFriend> call, Throwable t) {

            }
        });
    }

    private void delete_notofication() {
        Call<APIFriend> call = ApiClient.getApiClient().create(ApiInterface.class).Delete_Notification(sender_id, user_id);
        call.enqueue(new Callback<APIFriend>() {
            @Override
            public void onResponse(Call<APIFriend> call, Response<APIFriend> response) {
                if (response.body().getStatus().equals("ok")) {
                    if (response.body().getResult() == 1) {
                        Toast.makeText(getApplicationContext(), "Delete Notitfication Successfully", Toast.LENGTH_LONG).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<APIFriend> call, Throwable t) {

            }
        });
    }

    private void send_request() {
        Call<APIFriend> call = ApiClient.getApiClient().create(ApiInterface.class).Addfriend(sender_id, user_id);
        call.enqueue(new Callback<APIFriend>() {
            @Override
            public void onResponse(Call<APIFriend> call, Response<APIFriend> response) {
                if (response.body().getStatus().equals("ok")) {
                    if (response.body().getResult() == 1) {
                        Toast.makeText(getApplicationContext(), "Sent Successfully", Toast.LENGTH_LONG).show();
                        friend_req.setText("Cancel  Request");
                        //friend_req.setEnabled(false);
                    } else {

                    }
                } else {


                }
            }

            @Override
            public void onFailure(Call<APIFriend> call, Throwable t) {

            }
        });
    }


    //Bind Data
    private void Bind_Data() {
        user_id = getIntent().getIntExtra("user_id", 0);
        f_name = getIntent().getStringExtra("f_name");
        l_name = getIntent().getStringExtra("l_name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        user_status = getIntent().getIntExtra("user_status", 0);
        user_catergory_id = getIntent().getIntExtra("user_catergory_id", 0);
        profile_id = getIntent().getIntExtra("profile_id", 0);
        status = getIntent().getIntExtra("status", 0);
        designation = getIntent().getStringExtra("designation");
        description = getIntent().getStringExtra("description");
        DOB = getIntent().getStringExtra("DOB");
        encodedImage = getIntent().getStringExtra("encodedImage");
        sender_id = getIntent().getIntExtra("sender_id", 0);
    }

    //Child Fragment Helper
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }


    private void on_create_objects() {
        txt_username_profile = findViewById(R.id.txt_username_profile);
        txt_description = findViewById(R.id.txt_description);
        profile_pic = findViewById(R.id.profile_pic);
        gridView = findViewById(R.id.gridView);
        friend_req = findViewById(R.id.friend_req);
        txt_designation=findViewById(R.id.txt_designation);
    }

    private void set_values() {
        txt_username_profile.setText(f_name + " " + l_name);
        txt_description.setText(description);
        txt_designation.setText(designation+"");
        byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        profile_pic.setImageBitmap(decodedImage);
    }

    private void check_friend() {

        Call<APIFriend> call = ApiClient.getApiClient().create(ApiInterface.class).check_friend(sender_id, user_id);
        call.enqueue(new Callback<APIFriend>() {
            @Override
            public void onResponse(Call<APIFriend> call, Response<APIFriend> response) {
                if (response.body().getStatus().equals("ok")) {
                    if (response.body().getResult() == 1) {
                        friend_req.setEnabled(false);
                        friend_req.setText("Friends");
                    } else if (response.body().getResult() == 2) {
                        friend_req.setText("Cancel  Request");
                        //friend_req.setEnabled(false);
                    } else {
                        friend_req.setText("Add Friend");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error While Getting Information Please Restart Application", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIFriend> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void get_all_posts() {

        grid = new ArrayList<>();
        Call<List<APIGetOwnPosts>> call = ApiClient.getApiClient().create(ApiInterface.class).getcookbook(user_id);
        call.enqueue(new Callback<List<APIGetOwnPosts>>() {
            @Override
            public void onResponse(Call<List<APIGetOwnPosts>> call, Response<List<APIGetOwnPosts>> response) {
                List<APIGetOwnPosts> data = response.body();

                for (int i = 0; i < data.size(); i++) {
                    String Title = data.get(i).getCaption();
                    String encodedImage = data.get(i).getEncodedImage();
                    byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    grid.add(new GridModel(decodedImage, Title));
                    CookBookGridAdapter gridAdapter = new CookBookGridAdapter(grid, getContextOfApplication());
                    gridView.setAdapter(gridAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), ViewCookBook.class);
                            String encoded;
                            Bitmap bitmap = grid.get(position).getPicture();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                            byte[] imageInByte = byteArrayOutputStream.toByteArray();
                            encoded = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                            String titile = grid.get(position).getTitle();
                            intent.putExtra("image", encoded);
                            intent.putExtra("title", titile);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<APIGetOwnPosts>> call, Throwable t) {

            }
        });
    }
}