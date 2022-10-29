package com.example.foodmania.Api_Models;

import com.google.gson.annotations.SerializedName;

public class APIResponseLogin {

    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private int result;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("f_name")
    private String f_name;
    @SerializedName("l_name")
    private String l_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("user_status")
    private int user_status;
    @SerializedName("user_catergory_id")
    private int user_catergory_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getUser_catergory_id() {
        return user_catergory_id;
    }

    public void setUser_catergory_id(int user_catergory_id) {
        this.user_catergory_id = user_catergory_id;
    }

    @Override
    public String toString() {
        return "APIResponseLogin{" +
                "status='" + status + '\'' +
                ", result=" + result +
                ", user_id=" + user_id +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_status=" + user_status +
                ", user_catergory_id=" + user_catergory_id +
                '}';
    }
}

