package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 3/21/2018.
 */

public class ObjectResponseUpdateSorNoTemplate {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public String data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
