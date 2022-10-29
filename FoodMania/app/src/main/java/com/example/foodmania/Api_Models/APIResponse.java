package com.example.foodmania.Api_Models;

import com.google.gson.annotations.SerializedName;

public class APIResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private int result;

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
}
