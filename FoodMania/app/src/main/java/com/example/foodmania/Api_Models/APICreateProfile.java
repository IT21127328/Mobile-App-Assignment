package com.example.foodmania.Api_Models;

import com.google.gson.annotations.SerializedName;

public class APICreateProfile {


    @SerializedName("status_code")
    private int status_code;
    @SerializedName("remaks")
    private String remaks;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getRemaks() {
        return remaks;
    }

    public void setRemaks(String remaks) {
        this.remaks = remaks;
    }
}
