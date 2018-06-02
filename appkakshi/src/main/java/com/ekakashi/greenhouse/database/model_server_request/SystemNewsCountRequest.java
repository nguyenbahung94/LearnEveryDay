package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 4/3/2018.
 */

public class SystemNewsCountRequest {

    @SerializedName("userId")
    public int userId;

    public SystemNewsCountRequest(int userId) {
        this.userId = userId;
    }
}
