package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/14/2018.
 */

public class GetListFieldRequest {
    @SerializedName("userId")
    private int userId;

    public GetListFieldRequest(int userId) {
        this.userId = userId;
    }
}
