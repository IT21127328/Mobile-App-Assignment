package com.example.foodmania.Fragments;

import static com.example.foodmania.R.color.green;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Activities.ForgetPasswordActivity;
import com.example.foodmania.Api_Models.APIResponseProfile;
import com.example.foodmania.AppConfig.AppConfig;
import com.example.foodmania.R;
import com.example.foodmania.Activities.MainActivity;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.Api_Models.APIResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private Button btn_login;
    private EditText txt_email, txt_password;
    private View view;
    private TextView txt_wait, forget;
    private ProgressBar progress;
    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;
    int user_id;
    String f_name;
    String l_name;
    String email;
    String password;
    int user_status;
    int user_catergory_id;

    int profile_id, status;
    String designation, description, DOB, encodedImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //oncreate Objects
        oncreate_object();
       /* appConfig = new AppConfig(getContext());
        if(appConfig.isUserLogin())
        {
            String name=appConfig.getNameofUser();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();

        }*/
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = txt_email.getText().toString();
                String Password = txt_password.getText().toString();
                if(Email.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_LONG).show();


                }
                else if(Password.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Enter Password ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    performLogin();
                    set_please_wait();
                }


            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



    //oncreate object
    private void oncreate_object() {
        btn_login = view.findViewById(R.id.btn_login);
        progress = getActivity().getWindow().findViewById(R.id.progress);
        txt_email = view.findViewById(R.id.txt_email);
        txt_password = view.findViewById(R.id.txt_password);
//        txt_wait = getActivity().getWindow().findViewById(R.id.txt_wait);
        forget = view.findViewById(R.id.fgtpw);

    }

    //pleasewait show
    private void set_please_wait() {
//        txt_wait.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        txt_email.setEnabled(false);
        txt_password.setEnabled(false);
    }

    private void performLogin() {
        String Email = txt_email.getText().toString();
        String Password = txt_password.getText().toString();

        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(Email, Password);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {

                            user_id = response.body().getUser_id();
                            f_name = response.body().getF_name();
                            l_name = response.body().getL_name();
                            email = response.body().getEmail();
                            password = response.body().getPassword();
                            user_status = response.body().getUser_status();
                            user_catergory_id = response.body().getUser_catergory_id();
                           /* if(isRememberUserLogin)
                            {
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameofUser(f_name+" "+l_name);
                            }*/


                            //Get User Profile
                            get_user_details();


                        } else {
                            Toast.makeText(getActivity(), "Incorrect Email or Password ! ", Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.INVISIBLE);
                            txt_email.setEnabled(true);
                            txt_password.setEnabled(true);
                        }

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.INVISIBLE);
                        txt_email.setEnabled(true);
                        txt_password.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error ! ", Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.INVISIBLE);
                    txt_email.setEnabled(true);
                    txt_password.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });


    }

    public void checkBoxClicked(View view ) {
isRememberUserLogin=((CheckBox)view).isChecked();
    }

    private void get_user_details() {
        Call<APIResponseProfile> call = ApiClient.getApiClient().create(ApiInterface.class).get_profile(user_id);
        call.enqueue(new Callback<APIResponseProfile>() {
            @Override
            public void onResponse(Call<APIResponseProfile> call, Response<APIResponseProfile> response) {
                APIResponseProfile profile = response.body();
                if (profile != null) {
                    profile_id = profile.getProfile_id();
                    status = profile.getStatus();
                    designation = profile.getDesignation();
                    description = profile.getDescription();
                    DOB = profile.getDOB();
                    encodedImage = profile.getEncodedImage();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
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
                    progress.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "Invalid Profile Details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponseProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}