package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/29/2018.
 */

public class InviteMemberRequest {

    @SerializedName("fieldId")
    public int fieldId;
    @SerializedName("userInviteList")
    public ArrayList<UserInviteList> userInviteList;

    public static class UserInviteList {
        @SerializedName("authority")
        public int authority;
        @SerializedName("email")
        public String email;

        public UserInviteList(int authority, String email) {
            this.authority = authority;
            this.email = email;
        }
    }

    public InviteMemberRequest(int fieldId, ArrayList<UserInviteList> userInviteList) {
        this.fieldId = fieldId;
        this.userInviteList = userInviteList;
    }
}
