package com.example.foodmania.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.R;
import com.example.foodmania.Activities.CreateProfileActivity;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.Api_Models.APIResponseLogin;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    View view;
    private EditText txt_f_name, txt_l_name, txt_email, txt_password;
    private Spinner dropdown;
    private Button btn_signup;
    private TextView txt_wait;
    private ProgressBar progress;
    private int set_category_id;
    String selectedVal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        //oncreate Objects
        oncreate_objects();

        //Button Action Event
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vali();
            }
        });
dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         selectedVal=dropdown.getSelectedItem().toString();

        if (selectedVal .equals("User")) {
            set_category_id = 1;
        } else if (selectedVal .equals ("Skill Seller")) {
            set_category_id = 2;
        } else if (selectedVal .equals ("Resturant Owner")) {
            set_category_id = 3;
        } else {
            Toast.makeText(getActivity(), "Please Select Valid Category ! ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        return view;
    }
    //Validation
    private void vali()
    {
        if(txt_f_name.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"First Name Cannot Be Empty",Toast.LENGTH_LONG).show();

        }
        else if(!Pattern.matches("[a-zA-Z]+",txt_f_name.getText()))
        {
            Toast.makeText(getActivity(),"First Name Cannot Include Numbers",Toast.LENGTH_LONG).show();
        }
        else if(txt_l_name.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Last Name Cannot Be Empty",Toast.LENGTH_LONG).show();
        }
        else if(!Pattern.matches("[a-zA-Z]+",txt_l_name.getText()))
        {
            Toast.makeText(getActivity(),"Last Name Cannot Include Numbers",Toast.LENGTH_LONG).show();
        }
        else if(txt_email.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Email Cannot Be Empty",Toast.LENGTH_LONG).show();
        }
        else if(!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", txt_email.getText())))
        {
            Toast.makeText(getActivity(),"Invalid Email Format",Toast.LENGTH_LONG).show();
        }
        else if(txt_password.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Password Cannot Be Empty",Toast.LENGTH_LONG).show();

        }
        else
        {
            //set_status();
            set_please_wait();
            perofrmsignup();
        }

    }

    //pleasewait show
    private void set_please_wait() {
        // txt_wait.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
    }

    //Select Category Id
    private void set_status() {


    }

    //oncrate objects
    private void oncreate_objects() {

        txt_f_name = view.findViewById(R.id.txt_firstname);
        txt_l_name = view.findViewById(R.id.txt_lastname);
        txt_email = view.findViewById(R.id.txt_email);
        txt_password = view.findViewById(R.id.txt_password);
        dropdown = view.findViewById(R.id.user_catergory);
        btn_signup = view.findViewById(R.id.btn_signup);
//        txt_wait= getActivity().getWindow().findViewById(R.id.txt_wait);
        progress = getActivity().getWindow().findViewById(R.id.progress);
    }

    //signup process
    private void perofrmsignup() {
        String f_name = txt_f_name.getText().toString();
        String l_name = txt_l_name.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        int status = 1;


        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).performSignup(f_name, l_name, email, password, status, set_category_id);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {

                            Toast.makeText(getActivity(), "Succesfully Registered ! ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("f_name", f_name);
                            intent.putExtra("l_name", l_name);
                            intent.putExtra("user_catergory_id", set_category_id);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getActivity(), "Email ALready Exists", Toast.LENGTH_LONG).show();
                            progress.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong ! ", Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error ! ", Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });
    }

}