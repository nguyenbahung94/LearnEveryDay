package com.example.nbhung.demotwitter.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResponse {
    @Expose
    @SerializedName("body")
    private BodyResponse body;
    @Expose
    @SerializedName("message")
    private String message;

    public BodyResponse getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }
}
