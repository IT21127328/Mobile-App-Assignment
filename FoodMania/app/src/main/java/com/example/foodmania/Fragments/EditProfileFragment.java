package com.example.foodmania.Fragments;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Activities.CreateProfileActivity;
import com.example.foodmania.Api_Models.APIResponse;
import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileFragment extends Fragment implements LocationListener {
    View view;
    TextView  edit_description, edit_designation, txt_email, txt_city;
    EditText txt_description, txt_designation;
    int user_id;
    String description;
    String f_name, l_name, email, password, encoded_Image, designation;
    CircleImageView profile_pic;
    int user_catergory_id, user_status;
    Button btn_edit_address;

    LocationManager locationManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        //On create Objects
        Oncreate_Objects();

        //Get Data From
        get_data_bundle();

        //Set Data
        set_data();
        //button update description
        edit_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_description();
            }
        });

        //button update address
//        edit_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        //button update designation
        edit_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_designation();

            }
        });

        btn_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        return view;
    }

    private void set_data() {
        // txt_address.setHint(location);
        txt_description.setHint(description);
        txt_designation.setHint(designation);
        txt_email.setText(email);
//        byte[] imageInByte = Base64.decode(encoded_Image, Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
//        profile_pic.setImageBitmap(decodedImage);
    }


    private void Oncreate_Objects() {

//        edit_address = view.findViewById(R.id.txt_edit_address);
        edit_description = view.findViewById(R.id.txt_edit_description);
        edit_designation = view.findViewById(R.id.txt_edit_designation);
        txt_city = view.findViewById(R.id.txt_address);
        txt_description = view.findViewById(R.id.txt_description);
        txt_designation = view.findViewById(R.id.txt_designation);
        profile_pic = view.findViewById(R.id.profile_pic);
        txt_email = view.findViewById(R.id.txt_email);
        txt_city = view.findViewById(R.id.txt_city);
        btn_edit_address = view.findViewById(R.id.btn_edit_address);

    }


    private void update_description() {

        String Description = txt_description.getText().toString();
        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).update_description(user_id, Description);

        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {

                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {


                            txt_description.setHint(Description);
                            Toast.makeText(getActivity(), "Updated Successfully ", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), "Incorrect User ! ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error ! ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });

    }


    private void update_address() {

        String Address = txt_city.getText().toString();

        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).update_location(user_id, Address);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {

                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {


                            txt_description.setHint(Address);
                            Toast.makeText(getActivity(), "Updated Successfully ", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), "Incorrect User ! ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error ! ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });

    }


    private void update_designation() {

        String Designation = txt_designation.getText().toString();
        System.out.println(Designation);
        Call<APIResponse> call = ApiClient.getApiClient().create(ApiInterface.class).update_designation(user_id, Designation);
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {


                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {


                            txt_designation.setHint(Designation);
                            Toast.makeText(getActivity(), "Updated Successfully ", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), "Incorrect User ! ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error ! ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                System.out.println("Designation");
            }
        });

    }



    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        else
        {
            try {
                locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,EditProfileFragment.this);

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    private void requestPermission()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},101);
            }
        }

    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
       // Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            Address address = addresses.get(0);
            String city = address.getLocality();

            System.out.println(city);

            txt_city.setText(city);

            update_address();

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void get_data_bundle() {

        f_name = getArguments().getString("f_name");
        l_name = getArguments().getString("l_name");
        encoded_Image = getArguments().getString("encodedImage");
        user_id = getArguments().getInt("user_id");
        email = getArguments().getString("email");
        description = getArguments().getString("description");
        designation = getArguments().getString("designation");
    }

}