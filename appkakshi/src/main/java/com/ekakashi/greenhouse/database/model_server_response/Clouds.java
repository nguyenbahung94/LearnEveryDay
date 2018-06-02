package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/8/2018.
 */

public class Clouds {
    @SerializedName("all")
    public int all;

    public int getAll() {
        return all;
    }
}
