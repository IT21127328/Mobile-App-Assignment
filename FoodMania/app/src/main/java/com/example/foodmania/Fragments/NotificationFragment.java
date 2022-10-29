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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.foodmania.Adapters.NotificationAdapter;
import com.example.foodmania.Adapters.SearchAdapters;
import com.example.foodmania.Api_Models.APINotifications;
import com.example.foodmania.Api_Models.APISearchModel;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;
import com.example.foodmania.UI_Models.NotificationsModel;
import com.example.foodmania.UI_Models.SearchModel;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {

    int user_id;
    View view;
    RecyclerView notificationsRV;
    ArrayList<NotificationsModel> notificationList;
    LinearLayout progress_l;
    EditText txt_meeting;
    Button btn_meeting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationsRV = view.findViewById(R.id.notificationsRV);
        progress_l = view.findViewById(R.id.progress_l);
        txt_meeting=view.findViewById(R.id.txt_meeting);
        btn_meeting=view.findViewById(R.id.btn_meeting);
        get_data_bundle();
        get_notifications();
        btn_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join_meeting(v);
            }
        });
        return view;
    }

    //Meeting Link
    private void join_meeting(View v) {
        String text =txt_meeting.getText().toString() ;
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            JitsiMeetActivity.launch(getActivity(), options);
        }
    }

    //Get Data Bundle
    private void get_data_bundle() {
        user_id = getArguments().getInt("user_id");
    }

    //Get Notifications
    private void get_notifications() {
        System.out.println(user_id);
        notificationList = new ArrayList<>();
        Call<List<APINotifications>> call = ApiClient.getApiClient().create(ApiInterface.class).Get_Notification(user_id);
        call.enqueue(new Callback<List<APINotifications>>() {
            @Override
            public void onResponse(Call<List<APINotifications>> call, Response<List<APINotifications>> response) {

                List<APINotifications> data = response.body();
                //data.clear();
                for (int i = 0; i < data.size(); i++) {
                    String content = data.get(i).getContent();
                    String encoded_image = data.get(i).getEncodedprofile();
                    String f_name = data.get(i).getF_name();
                    String l_name = data.get(i).getL_name();

                    byte[] imageInByte = Base64.decode(encoded_image, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    notificationList.add(new NotificationsModel(decodedImage, f_name + " " + l_name, content));
                    NotificationAdapter notificationAdapter = new NotificationAdapter(notificationList, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    notificationsRV.setLayoutManager(linearLayoutManager);
                    // searchRV.addItemDecoration(new DividerItemDecoration(searchRV.getContext(), DividerItemDecoration.VERTICAL));
                    // searchRV.setNestedScrollingEnabled(false);
                    notificationsRV.setAdapter(notificationAdapter);
                    progress_l.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<APINotifications>> call, Throwable t) {

            }
        });

    }
}