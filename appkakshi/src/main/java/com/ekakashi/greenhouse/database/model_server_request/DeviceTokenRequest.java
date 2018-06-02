package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 2/22/2018.
 */

public class DeviceTokenRequest {
    @SerializedName("deviceToken")
    public String deviceToken;

    public DeviceTokenRequest(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
