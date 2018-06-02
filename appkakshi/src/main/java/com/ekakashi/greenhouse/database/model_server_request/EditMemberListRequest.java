package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 1/29/2018.
 */

public class EditMemberListRequest {

    @SerializedName("authority")
    public int authority;
    @SerializedName("invitationId")
    public int invitationId;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("urlImage")
    public String urlImage;

    public EditMemberListRequest(int invitationId) {
        this.invitationId = invitationId;
    }

    public EditMemberListRequest(int authority, int invitationId, String nickName, String urlImage) {
        this.authority = authority;
        this.invitationId = invitationId;
        this.nickName = nickName;
        this.urlImage = urlImage;
    }
}
