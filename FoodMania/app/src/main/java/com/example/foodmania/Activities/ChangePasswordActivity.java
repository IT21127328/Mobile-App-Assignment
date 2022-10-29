package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btn_change;
    TextView txt_username, txt_email;
    EditText Password, cPassword;
    CircleImageView profile_pic;
    String f_name, l_name, encoded_Image, email, password, cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //On Create Objects
        onCreate_Objects();

        //Bind Data
        Bind_Data();

        //Set Profile Pic
        set_profilepic();


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void Bind_Data() {
        f_name = getIntent().getStringExtra("f_name");
        l_name = getIntent().getStringExtra("l_name");
        encoded_Image = getIntent().getStringExtra("encoded_image");
        email = getIntent().getStringExtra("email");


    }

    private void validation() {
        password = Password.getText().toString();
        cpass = cPassword.getText().toString();

        if (password.equals(cpass)) {
            change_password();
        } else {
            Toast.makeText(getApplicationContext(), "Passwords Didn't Match ", Toast.LENGTH_LONG).show();
        }

    }

    private void onCreate_Objects() {

        btn_change = findViewById(R.id.btn_change_pw);
        txt_username = findViewById(R.id.txt_username);
        Password = findViewById(R.id.txt_password);
        cPassword = findViewById(R.id.txt_cpassword);
        profile_pic = findViewById(R.id.profile_image);
        txt_email = findViewById(R.id.txt_email);

    }

    private void set_profilepic() {
        byte[] imageInByte = Base64.decode(encoded_Image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        profile_pic.setImageBitmap(decodedImage);
        txt_username.setText(f_name + " " + l_name);
        txt_email.setText(email);
    }

    private void change_password() {

        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).changepassword(email, password);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResult() == 1) {
                            Toast.makeText(getApplicationContext(), "Successfully Updated Password", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Unknown Error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });
    }


}