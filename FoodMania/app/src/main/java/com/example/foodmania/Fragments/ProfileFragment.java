package com.example.foodmania.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodmania.Activities.WelcomeActivity;
import com.example.foodmania.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    View view;
    Button btn_cook_book, btn_edit_profile, btn_cookies, btn_collections, btn_meeting, btn_chat;
    String fname, lname, encoded_Image, description, email, designation;
    CircleImageView profile_pic;
    TextView txt_description, txt_username_profile, txt_designation;
    int user_id;
    Bundle bundle = new Bundle();
Button btn_log_out;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //On Create Objects
        oncreate_objects();

        //Get Data Bundle
        get_data_bundle();

        //Set Profile Picture
        set_profile_pic_and_details();

        //Create Data Bundle
        Create_Data_Bundle();

        //OnCreate CookBook
        CookBookFragment EP = new CookBookFragment();
        EP.setArguments(bundle);
        replacefragment(EP);

        //Set Button
//        btn_cookies.getBackground().setColorFilter(ContextCompat.getColor(getContext(),R.color.white ), PorterDuff.Mode.MULTIPLY);
//        btn_edit_profile.getBackground().setColorFilter(ContextCompat.getColor(getContext(),R.color.white ), PorterDuff.Mode.MULTIPLY);
//        btn_cook_book.getBackground().setColorFilter(ContextCompat.getColor(getContext(),R.color.white ), PorterDuff.Mode.MULTIPLY);
//        btn_collections.getBackground().setColorFilter(ContextCompat.getColor(getContext(),R.color.white ), PorterDuff.Mode.MULTIPLY);

        //Edit Profile Button Click Event
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_edit_profile.setTextColor(Color.parseColor("#eec1d9"));
                btn_collections.setTextColor(Color.parseColor("#000000"));
                btn_cook_book.setTextColor(Color.parseColor("#000000"));
                btn_log_out.setTextColor(Color.parseColor("#000000"));

                EditProfileFragment EP = new EditProfileFragment();
                EP.setArguments(bundle);
                replacefragment(EP);
            }
        });

        //Cook Book Button Click Event
        btn_cook_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cook_book.setTextColor(Color.parseColor("#eec1d9"));
                btn_edit_profile.setTextColor(Color.parseColor("#000000"));
                btn_collections.setTextColor(Color.parseColor("#000000"));
                btn_log_out.setTextColor(Color.parseColor("#000000"));
                CookBookFragment EP = new CookBookFragment();
                EP.setArguments(bundle);
                replacefragment(EP);


            }
        });

        //Cookies Button Click Event
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacefragment(new CookiesFragment());
                btn_cookies.setTextColor(Color.parseColor("#eec1d9"));
                btn_edit_profile.setTextColor(Color.parseColor("#000000"));
                btn_cook_book.setTextColor(Color.parseColor("#000000"));
                btn_collections.setTextColor(Color.parseColor("#000000"));


            }
        });

        //Collection Button Click Event
        btn_collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionFragment C = new CollectionFragment();
                C.setArguments(bundle);
                replacefragment(C);
                btn_collections.setTextColor(Color.parseColor("#eec1d9"));
                btn_edit_profile.setTextColor(Color.parseColor("#000000"));
                btn_cook_book.setTextColor(Color.parseColor("#000000"));
                btn_log_out.setTextColor(Color.parseColor("#000000"));

            }
        });
        btn_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_meeting(view);
            }
        });
        //Click on Chat
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.streamchatdemo");
                startActivity(intent);
            }
        });
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
    @Override
    public void startActivityForResult(Intent intent,int requestcode)
    {
if(intent==null)
{
    intent =new Intent();
}
super.startActivityForResult(intent,requestcode);
    }
    //Set On Create Object
    private void oncreate_objects() {

        btn_collections = view.findViewById(R.id.btn_Collection);
        btn_cook_book = view.findViewById(R.id.btn_Cook_book);
        btn_log_out = view.findViewById(R.id.btn_log_out);
        btn_edit_profile = view.findViewById(R.id.btn_Edit_Profile);
        profile_pic = view.findViewById(R.id.profile_pic);
        txt_description = view.findViewById(R.id.txt_description);
        txt_designation = view.findViewById(R.id.txt_designation);
        txt_username_profile = view.findViewById(R.id.txt_username_profile);
        btn_meeting = view.findViewById(R.id.btn_meet);
        btn_chat = view.findViewById(R.id.btn_chat);

    }

    //Set Profile Pic
    private void set_profile_pic_and_details() {
        byte[] imageInByte = Base64.decode(encoded_Image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        profile_pic.setImageBitmap(decodedImage);
        txt_description.setText(description);
        txt_username_profile.setText(fname + " " + lname);
        txt_designation.setText(designation);
    }

    //Get Data Bundle
    private void get_data_bundle() {

        fname = getArguments().getString("f_name");
        lname = getArguments().getString("l_name");
        encoded_Image = getArguments().getString("encodedImage");
        description = getArguments().getString("description");
        designation = getArguments().getString("designation");
        email = getArguments().getString("email");
        user_id = getArguments().getInt("user_id");
    }

    //Replace Fragment
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.profile_frame, fragment);
        fragmentTransaction.commit();

    }

    //Meeting Link
    private void new_meeting(View v) {
        String text = fname + lname;
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            JitsiMeetActivity.launch(getActivity(), options);
        }
    }

    //Create Data Bundle
    private void Create_Data_Bundle() {

        bundle.putInt("user_id", user_id);
        bundle.putString("f_name", fname);
        bundle.putString("l_name", lname);
        bundle.putString("email", email);
        // bundle.putString("password", password);
        // bundle.putInt("status",status);
        bundle.putString("designation", designation);
        bundle.putString("description", description);
        // bundle.putString("DOB",DOB);
        bundle.putString("encodedImage", encoded_Image);
    }
}