package com.example.foodmania.Api_Util;


import com.example.foodmania.Api_Models.APICreateProfile;
import com.example.foodmania.Api_Models.APIFriend;
import com.example.foodmania.Api_Models.APIFriendReq;
import com.example.foodmania.Api_Models.APIGetOwnPosts;
import com.example.foodmania.Api_Models.APINotifications;
import com.example.foodmania.Api_Models.APIResponse;
import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Models.APIResponseProfile;
import com.example.foodmania.Api_Models.APISearchModel;
import com.example.foodmania.Api_Models.APIStory;
import com.example.foodmania.Api_Models.AllPosts;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("login.php")
    Call<APIResponseLogin> performUserLogin(@Field("email") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<APIResponseLogin> performSignup(@Field("f_name") String f_name, @Field("l_name") String l_name, @Field("email") String email, @Field("password") String password,
                                         @Field("status") int status, @Field("user_category_id") int user_category_id);

    @FormUrlEncoded
    @POST("download_profile_picture.php")
    Call<APIResponseProfile> get_profile(@Field("user_id") int user_id);

    @GET("get_all_post.php")
    Call<List<AllPosts>> getall();

    @FormUrlEncoded
    @POST("get_id.php")
    Call<APIResponseLogin> get_id(@Field("email") String email);

    @FormUrlEncoded
    @POST("create_profile.php")
    Call<APICreateProfile> upload_profile_pic(
            @Field("email") String email, @Field("encoded_image") String encoded_image, @Field("designation") String designation,
            @Field("description") String description, @Field("status") int status, @Field("dob") String dob, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("upload_post.php")
    Call<APICreateProfile> upload_post(
            @Field("encodedImage") String encodedImage,
            @Field("title") String title,
            @Field("location") String location,
            @Field("status") int status,
            @Field("user_id") int user_id);


    @FormUrlEncoded
    @POST("change_password.php")
    Call<APIResponseLogin> changepassword(@Field("email") String email,
                                          @Field("password") String password);


    @FormUrlEncoded
    @POST("get_username.php")
    Call<APIResponseLogin> get_usrname(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("search.php")
    Call<List<APISearchModel>> search(@Field("name") String name,@Field("location") String location);


    @FormUrlEncoded
    @POST("get_user_detail.php")
    Call<APIResponseLogin> get_user_details(@Field("user_id") int user_id);


    @FormUrlEncoded
    @POST("update_description.php")
    Call<APIResponseLogin> update_description(@Field("user_id") int user_id, @Field("description") String description);

    @FormUrlEncoded
    @POST("update_designation.php")
    Call<APIResponse> update_designation(@Field("user_id") int user_id, @Field("description") String designation);

    @FormUrlEncoded
    @POST("update_location.php")
    Call<APIResponseLogin> update_location(@Field("user_id") int user_id, @Field("location") String location);


    @FormUrlEncoded
    @POST("get_own_photos.php")
    Call<List<APIGetOwnPosts>> getcookbook(@Field("user_id") int user_id);

    @GET("get_all_story.php")
    Call<List<APIStory>> getstory();

    @FormUrlEncoded
    @POST("create_story.php")
    Call<APIStory> savestory(@Field("encodedImage") String encodedImage, @Field("title") String title, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("check_friend.php")
    Call<APIFriend> check_friend(@Field("sender_id") int sender_id, @Field("receiver_id") int receiver_id);


    @FormUrlEncoded
    @POST("send_friend_requests.php")
    Call<APIFriend> Addfriend(@Field("sender_id") int sender_id, @Field("receiver_id") int receiver_id);

    @FormUrlEncoded
    @POST("delete_request.php")
    Call<APIFriend> Delete_req(@Field("sender_id") int sender_id, @Field("receiver_id") int receiver_id);

    @FormUrlEncoded
    @POST("Send_notification.php")
    Call<APIFriend> Send_Notification(@Field("sender_id") int sender_id, @Field("receiver_id") int receiver_id, @Field("content") String content);

    @FormUrlEncoded
    @POST("delete_notification.php")
    Call<APIFriend> Delete_Notification(@Field("sender_id") int sender_id, @Field("receiver_id") int receiver_id);

    @FormUrlEncoded
    @POST("get_notification.php")
    Call<List<APINotifications>> Get_Notification(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("get_friend_requests.php")
    Call<List<APIFriendReq>> get_friend_req(@Field("user_id") int user_id);
}
