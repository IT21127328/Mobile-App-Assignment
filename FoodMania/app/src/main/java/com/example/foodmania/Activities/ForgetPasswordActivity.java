package com.example.foodmania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmania.Api_Models.APIResponseLogin;
import com.example.foodmania.Api_Models.APIResponseProfile;
import com.example.foodmania.Api_Util.ApiClient;
import com.example.foodmania.Api_Util.ApiInterface;
import com.example.foodmania.R;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button verify, send_code;
    private EditText txt_code, txt_email;
    private TextView send_again;
    private int randomCode;
    int user_id;
    String encoded_image;
    String email;
    String f_name, l_name;

    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //On Create Objects
        oncreate_objects();

        //Set Verification Code
        setcode();

        //send code
        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_email.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                } else {
                    check_email();
                }


            }
        });
        //Verify Code
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_email.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                } else {
                    check_code();
                }


            }
        });

        //Send Again
        send_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_email.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                } else {
                    send_code_again();
                }

            }
        });

    }

    //Check Email Exisitng or Not
    private void check_email() {

        email = txt_email.getText().toString();

        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).get_id(email);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {

                        if (response.body().getResult() == 1) {

                            Toast.makeText(getApplicationContext(), "Successful! Existing Account !", Toast.LENGTH_LONG).show();
                            sendemail();
                            txt_email.setEnabled(false);
                            send_code.setEnabled(false);


                        } else {
                            Toast.makeText(getApplicationContext(), "No Account Associate with this email", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });

    }


    //Get Id,Username,ProfileImage
    private void get_id() {

        email = txt_email.getText().toString();

        Call<APIResponseLogin> call = ApiClient.getApiClient().create(ApiInterface.class).get_id(email);
        call.enqueue(new Callback<APIResponseLogin>() {
            @Override
            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {

                        if (response.body().getResult() == 1) {

                            user_id = response.body().getUser_id();
                            get_profile_image();


                        } else {
                            Toast.makeText(getApplicationContext(), "No Account Associate with this email", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

            }
        });

    }


    //get profileImage
    private void get_profile_image() {

        Call<APIResponseProfile> call1 = ApiClient.getApiClient().create(ApiInterface.class).get_profile(user_id);
        call1.enqueue(new Callback<APIResponseProfile>() {
            @Override
            public void onResponse(Call<APIResponseProfile> call1, Response<APIResponseProfile> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus() == 1) {

                        encoded_image = response.body().getEncodedImage();
                        get_name();


                    } else {
                        Toast.makeText(getApplicationContext(), "Error While Getting Profile Picture", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error imge", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<APIResponseProfile> call1, Throwable t) {

            }
        });

    }

    //get Username
    private void get_name() {

        Call<APIResponseProfile> call2 = ApiClient.getApiClient().create(ApiInterface.class).get_profile(user_id);
        call2.enqueue(new Callback<APIResponseProfile>() {
            @Override
            public void onResponse(Call<APIResponseProfile> call2, Response<APIResponseProfile> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus() == 1) {
                        encoded_image = response.body().getEncodedImage();


                        Call<APIResponseLogin> call3 = ApiClient.getApiClient().create(ApiInterface.class).get_usrname(user_id);
                        call3.enqueue(new Callback<APIResponseLogin>() {
                            @Override
                            public void onResponse(Call<APIResponseLogin> call, Response<APIResponseLogin> response) {
                                if (response.code() == 200) {
                                    if (response.body().getStatus().equals("ok")) {
                                        if (response.body().getResult() == 1) {
                                            f_name = response.body().getF_name();
                                            l_name = response.body().getL_name();
                                            System.out.println(f_name + l_name);
                                            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                                            intent.putExtra("f_name", f_name);
                                            intent.putExtra("l_name", l_name);
                                            intent.putExtra("encoded_image", encoded_image);
                                            intent.putExtra("email", email);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResponseLogin> call, Throwable t) {

                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(), "Error While Getting Profile Picture", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Network Error ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<APIResponseProfile> call2, Throwable t) {

            }
        });
    }


    private void send_code_again() {
        if (txt_email.equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Email ", Toast.LENGTH_LONG).show();
        } else {
            randomCode = rand.nextInt(999999);
            sendemail();
        }

    }

    private void check_code() {

        int entered_code = Integer.parseInt(txt_code.getText().toString());
        if (entered_code == randomCode) {
            get_id();
        } else {
            Toast.makeText(getApplicationContext(), "Code not Match", Toast.LENGTH_LONG).show();

        }
    }


    private void oncreate_objects() {
        verify = findViewById(R.id.btn_verify);
        send_code = findViewById(R.id.btn_send_reset);
        txt_code = findViewById(R.id.txt_code);
        txt_email = findViewById(R.id.txt_email);
        send_again = findViewById(R.id.resend_code);


    }

    private void setcode() {

        randomCode = rand.nextInt(999999);

    }

    private void sendemail() {
        String username = "foodmaniaproject01@gmail.com";
        String password = "Makayath@01";
        String messagetosend = String.valueOf(randomCode);
        String address = txt_email.getText().toString();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject("Rest Code");
            message.setText(messagetosend);
            Transport.send(message);
            Toast.makeText(getApplicationContext(), "Email Sent Successfully", Toast.LENGTH_LONG).show();

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }


    }
}