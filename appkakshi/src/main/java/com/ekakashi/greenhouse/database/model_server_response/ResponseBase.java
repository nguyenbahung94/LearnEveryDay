package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/14/2018.
 */

public class ResponseBase {
    @SerializedName("result")
    private boolean result;


    public boolean isResult() {
        return result;
    }
}
