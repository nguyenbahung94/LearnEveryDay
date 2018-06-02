package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 4/3/2018.
 */

public class SystemNewsCountResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("count")
        public int count;
        @SerializedName("newsId")
        public ArrayList<String> newsId;

        public int getCount() {
            return count;
        }

        public ArrayList<String> getNewsId() {
            return newsId;
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
