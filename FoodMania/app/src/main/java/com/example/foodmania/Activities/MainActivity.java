package com.example.foodmania.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.foodmania.R;
import com.example.foodmania.Fragments.AddPostFragment;
import com.example.foodmania.Fragments.HomeFragment;
import com.example.foodmania.Fragments.NotificationFragment;
import com.example.foodmania.Fragments.ProfileFragment;
import com.example.foodmania.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String f_name, l_name, email, password;
    int user_catergory_id, user_id, user_status;
    public static Context contextOfApplication;
    Bundle bundle = new Bundle();
    BottomNavigationView navigationView;

    int profile_id;
    int status;
    String designation;
    String description;
    String DOB;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        //Oncreate Object
        oncreate_object();

        //Get Data From Login
        Bind_Data();

        //send Data to Fragments
        Create_Data_Bundle();

        //Navigation
        Navigation();

        //open default frame
        open_home();

        System.out.println("Reciever" + " " + user_id);

    }

    //openHome
    private void open_home() {
        HomeFragment H = new HomeFragment();
        H.setArguments(bundle);
        replacefragment(H);
    }

    //Navigation
    private void Navigation() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeFragment H = new HomeFragment();
                        H.setArguments(bundle);
                        replacefragment(H);
                        break;
                    case R.id.nav_search:
                        SearchFragment S = new SearchFragment();
                        S.setArguments(bundle);
                        replacefragment(S);
                        break;

                    case R.id.nav_add:
                        AddPostFragment A = new AddPostFragment();
                        A.setArguments(bundle);
                        replacefragment(A);
                        break;

                    case R.id.nav_noti:
                        NotificationFragment N = new NotificationFragment();
                        N.setArguments(bundle);
                        replacefragment(N);
                        break;

                    case R.id.nav_pro:
                        ProfileFragment P = new ProfileFragment();
                        P.setArguments(bundle);
                        replacefragment(P);
                        break;
                }
                return true;
            }
        });
    }

    //On create Objects
    private void oncreate_object() {
        navigationView = findViewById(R.id.bottom_navigation);

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
        ;
        designation = getIntent().getStringExtra("designation");
        ;
        description = getIntent().getStringExtra("description");
        ;
        DOB = getIntent().getStringExtra("DOB");
        ;
        encodedImage = getIntent().getStringExtra("encodedImage");
        ;
    }

    //Create Data Bundle
    private void Create_Data_Bundle() {

        bundle.putInt("user_id", user_id);
        bundle.putString("f_name", f_name);
        bundle.putString("l_name", l_name);
        bundle.putString("email", email);
        bundle.putString("password", password);
        bundle.putInt("user_status", user_status);
        bundle.putInt("user_catergory_id", user_catergory_id);

        bundle.putInt("profile_id", profile_id);
        bundle.putInt("status", status);
        bundle.putString("designation", designation);
        bundle.putString("description", description);
        bundle.putString("DOB", DOB);
        bundle.putString("encodedImage", encodedImage);
    }


    //Child Fragment Helper
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    //Replace Fragmnts
    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}