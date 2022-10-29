package com.example.foodmania.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodmania.Adapters.FriendReqAdapter;
import com.example.foodmania.Api_Models.APIFriendReq;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.FriendReqModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CollectionFragment extends Fragment {
    View view;
    int user_id;
    ArrayList<FriendReqModel> requestList;
    RecyclerView friendreqRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_collection, container, false);
        friendreqRV=view.findViewById(R.id.friendreqRV);

        //Catch Data sent by previous Fragment
        Data_Bind();

        //Set Recycler
        get_req();



        return view;
    }

    private  void get_req()
    {
        requestList=new ArrayList<>();

        Call<List<APIFriendReq>> call= ApiClient.getApiClient().create(ApiInterface.class).get_friend_req(user_id);
        call.enqueue(new Callback<List<APIFriendReq>>() {
            @Override
            public void onResponse(Call<List<APIFriendReq>> call, Response<List<APIFriendReq>> response) {
                List<APIFriendReq> data=response.body();

                for(int i=0; i<data.size();i++)
                {
                    String f_name=data.get(i).getF_name();
                    String l_name=data.get(i).getL_name();
                    String encodedImage=data.get(i).getEncodedImage();
                    String designation= data.get(i).getDesignation();

                    byte[] imageInByte= Base64.decode(encodedImage,Base64.DEFAULT);
                    Bitmap decodedImage= BitmapFactory.decodeByteArray(imageInByte,0,imageInByte.length);

                    requestList.add(new FriendReqModel(decodedImage,f_name+" "+l_name,designation));
                    FriendReqAdapter friendReqAdapter=new FriendReqAdapter(requestList,getContext());
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    friendreqRV.setLayoutManager(linearLayoutManager);
                    friendreqRV.setAdapter(friendReqAdapter);

                }



            }

            @Override
            public void onFailure(Call<List<APIFriendReq>> call, Throwable t) {

            }
        });



    }

    private void Data_Bind()
    {
        user_id = getArguments().getInt("user_id");
    }
}