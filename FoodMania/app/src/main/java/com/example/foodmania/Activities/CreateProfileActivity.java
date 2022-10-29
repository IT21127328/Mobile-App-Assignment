package com.example.foodmania.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Api_Models.APICreateProfile;
import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfileActivity extends AppCompatActivity implements LocationListener {
    String f_name, l_name, email, designation, description, dob;
    int status = 1;
    int user_id;
    Bitmap bitmap=null;
    TextView txt_username, txt_email, txt_address;
    Button btn_create, btn_select_img, btn_location_picker;
    ImageView profile_pic;
    EditText txt_dob, txt_description, txt_designation;
    int IMG_REQUEST = 21;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Bind Data
        Bind_Data();

        //set on create objects
        Oncreate_Object();

        //set Text Objects
        set_Text();


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vali();

            }
        });
txt_dob.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showdate(txt_dob);
    }
});
        btn_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_img();
            }
        });

        btn_location_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });


    }
//Date Pick
private void showdate(final EditText txt_dob)
{
    final Calendar calendar=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayofMonth);
            Date date=new Date();
            date=calendar.getTime();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String d=simpleDateFormat.format(date);
            txt_dob.setText(d);
        }
    };
    new DatePickerDialog(CreateProfileActivity.this,onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONDAY),calendar.get(Calendar.DAY_OF_MONTH)).show();
}
    //Validations
    private void vali() {
        if (txt_designation.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Designation Cannot Be Empty", Toast.LENGTH_LONG).show();
        } else if (txt_dob.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please select your DOB", Toast.LENGTH_LONG).show();
        } else if (txt_description.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Description Cannot Be Empty", Toast.LENGTH_LONG).show();
        } else if (txt_address.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Location not selected", Toast.LENGTH_LONG).show();
        } else if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "Please Select a Image ", Toast.LENGTH_LONG).show();
        } else {
           get_id();
        }


    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, CreateProfileActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Select Image Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profile_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Get ID
    private void get_id() {
        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).get_id(email);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {


                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {

                            user_id = response.body().getUser_id();
                            Create_Profile();


                        } else {
                            Toast.makeText(getApplicationContext(), "Error While Getting Id  ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error ! ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });


    }

    //Create Data in Database
    private void Create_Profile() {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedProfileImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        designation = txt_designation.getText().toString();
        description = txt_description.getText().toString();
        dob = txt_dob.getText().toString().trim();
        email = txt_email.getText().toString().trim();

        System.out.println(email);
        System.out.println(designation);
        System.out.println(description);
        System.out.println(status);
        System.out.println(dob);
        System.out.println(user_id);
        System.out.println(encodedProfileImage);

        Call<APICreateProfile> call = ApiClient.getApiClient().create(ApiInterface.class).upload_profile_pic(email, encodedProfileImage, designation, description, status, dob, user_id);
        call.enqueue(new Callback<APICreateProfile>() {
            @Override
            public void onResponse(Call<APICreateProfile> call, Response<APICreateProfile> response) {
                System.out.println(response.body().getStatus_code());
                if (response.body().getStatus_code() == 1) {
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    Toast.makeText(getApplicationContext(), "Successfully Created Profile.Please Login !   ", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                } else if (response.body().getStatus_code() == 0) {
                    Toast.makeText(getApplicationContext(), "Error While Creating Profile  ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error  ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<APICreateProfile> call, Throwable t) {

            }
        });

    }

    //Bind Data
    private void Bind_Data() {
        f_name = getIntent().getStringExtra("f_name");
        l_name = getIntent().getStringExtra("l_name");
        email = getIntent().getStringExtra("email");


    }

    //On Create
    private void Oncreate_Object() {

        txt_email = findViewById(R.id.txt_email);
        txt_username = findViewById(R.id.txt_username);
        btn_create = findViewById(R.id.btn_create_pro);
        btn_select_img = findViewById(R.id.btn_select_img);
        profile_pic = findViewById(R.id.profile_pic);
        txt_description = findViewById(R.id.txt_description);
        txt_designation = findViewById(R.id.txt_designation);
        txt_dob = findViewById(R.id.txt_dob);
        txt_address = findViewById(R.id.txt_address);
        btn_location_picker = findViewById(R.id.btn_location_picker);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(CreateProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateProfileActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    //Set Text
    private void set_Text() {
        txt_email.setText(email);
        txt_username.setText(f_name + " " + l_name);
    }

    //Select Image
    private void select_img() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(CreateProfileActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addresses.get(0);
            String city = address.getLocality();


            txt_address.setText(city);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}