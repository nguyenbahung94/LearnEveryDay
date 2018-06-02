package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 2/26/2018.
 */

public class NotificationSettingRequest {

    @SerializedName("isAdminActive")
    private boolean isAdminActive;
    @SerializedName("isDiaryActive")
    private boolean isDiaryActive;
    @SerializedName("isIoTAActive")
    private boolean isIoTAActive;

    public NotificationSettingRequest() {
    }

    public NotificationSettingRequest(boolean isAdminActive, boolean isDiaryActive, boolean isIoTAActive) {
        this.isAdminActive = isAdminActive;
        this.isDiaryActive = isDiaryActive;
        this.isIoTAActive = isIoTAActive;
    }

    public boolean isAdminActive() {
        return isAdminActive;
    }

    public void setAdminActive(boolean adminActive) {
        isAdminActive = adminActive;
    }

    public boolean isDiaryActive() {
        return isDiaryActive;
    }

    public void setDiaryActive(boolean diaryActive) {
        isDiaryActive = diaryActive;
    }

    public boolean isIoTAActive() {
        return isIoTAActive;
    }

    public void setIoTAActive(boolean ioTAActive) {
        isIoTAActive = ioTAActive;
    }
}
