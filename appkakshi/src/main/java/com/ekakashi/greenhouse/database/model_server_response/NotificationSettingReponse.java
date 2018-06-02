package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 2/26/2018.
 */

public class NotificationSettingReponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("isAdminActive")
        public boolean isAdminActive;
        @SerializedName("isIoTAActive")
        public boolean isIoTAActive;
        @SerializedName("isDiaryActive")
        public boolean isDiaryActive;

        public boolean isAdminActive() {
            return isAdminActive;
        }

        public boolean isIoTAActive() {
            return isIoTAActive;
        }

        public boolean isDiaryActive() {
            return isDiaryActive;
        }
            }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
