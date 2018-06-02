package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/30/2018.
 */

public class DeviceDetailRequest {

    @SerializedName("activationStatus")
    private int activationStatus;
    @SerializedName("id")
    private String id;
    @SerializedName("openDegree")
    private int openDegree;
    @SerializedName("snName")
    private String snName;
    @SerializedName("systemAutoControl")
    private boolean systemAutoControl;

    public DeviceDetailRequest(int activationStatus, String id, int openDegree, String snName, boolean systemAutoControl) {
        this.activationStatus = activationStatus;
        this.id = id;
        this.openDegree = openDegree;
        this.snName = snName;
        this.systemAutoControl = systemAutoControl;
    }
}
