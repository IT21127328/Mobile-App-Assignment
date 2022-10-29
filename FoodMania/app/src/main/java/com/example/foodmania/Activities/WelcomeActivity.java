package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.foodmania.R;
import com.example.foodmania.Fragments.LoginFragment;
import com.example.foodmania.Fragments.RegisterFragment;

public class WelcomeActivity extends AppCompatActivity {
    Button btn_login, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Default Values and Objects when Activity Loads
        oncreate_object();

        //onclick login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacefragment(new LoginFragment());
                //btn_login.setBackgroundColor(Color.parseColor("#d571a6"));
            }
        });

        //onclick signup
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacefragment(new RegisterFragment());
                btn_signup.setBackgroundResource(R.drawable.btn_green_select);
            }
        });


    }

    private void get_and_send_profile_data() {

    }


    private void oncreate_object() {
        btn_login = findViewById(R.id.btnlogin);
        btn_signup = findViewById(R.id.btnsignup);
        replacefragment(new LoginFragment());
        //btn_login.setBackgroundColor(Color.parseColor("#d571a6"));
        btn_signup.setBackgroundResource(R.drawable.btn_green_no_select);
    }

    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frm_usr_authentic, fragment);
        fragmentTransaction.commit();
    }
}