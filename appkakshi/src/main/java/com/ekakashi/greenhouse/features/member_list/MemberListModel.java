package com.ekakashi.greenhouse.features.member_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MemberListModel implements Parcelable {

    @SerializedName("invitationId")
    public int invitationId;
    @SerializedName("ekUserId")
    public int ekUserId;
    @SerializedName("email")
    public String email;
    @SerializedName("userName")
    public String userName;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("authority")
    public int authority;
    @SerializedName("urlImage")
    public String urlImage;
    @SerializedName("invitationStatus")
    public String invitationStatus;

    public int getInvitationId() {
        return invitationId;
    }

    public int getEkUserId() {
        return ekUserId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAuthority() {
        return authority;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.invitationId);
        dest.writeInt(this.ekUserId);
        dest.writeString(this.email);
        dest.writeString(this.userName);
        dest.writeString(this.nickName);
        dest.writeInt(this.authority);
        dest.writeString(this.urlImage);
        dest.writeString(this.invitationStatus);
    }

    public MemberListModel() {
    }

    protected MemberListModel(Parcel in) {
        this.invitationId = in.readInt();
        this.ekUserId = in.readInt();
        this.email = in.readString();
        this.userName = in.readString();
        this.nickName = in.readString();
        this.authority = in.readInt();
        this.urlImage = in.readString();
        this.invitationStatus = in.readString();
    }

    public static final Creator<MemberListModel> CREATOR = new Creator<MemberListModel>() {
        @Override
        public MemberListModel createFromParcel(Parcel source) {
            return new MemberListModel(source);
        }

        @Override
        public MemberListModel[] newArray(int size) {
            return new MemberListModel[size];
        }
    };
}
