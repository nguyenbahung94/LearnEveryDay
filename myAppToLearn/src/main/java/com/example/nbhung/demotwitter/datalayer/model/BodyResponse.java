package com.example.nbhung.demotwitter.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyResponse {
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("idUser")
    private String idUser;

    public String getToken() {
        return token;
    }

    public String getIdUser() {
        return idUser;
    }
}
