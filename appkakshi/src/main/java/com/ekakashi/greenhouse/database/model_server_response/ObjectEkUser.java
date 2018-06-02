package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 1/16/2018.
 */

public class ObjectEkUser implements Parcelable {
    @SerializedName("validEmail")
    private boolean validEmail;
    @SerializedName("userName")
    private String userName;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("password")
    private String password;
    @SerializedName("officialUserFlag")
    private int officialUserFlag;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("name")
    private String name;
    @SerializedName("lastLoginDatetime")
    private String lastLoginDatetime;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("email")
    private String email;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("id")
    private int id;



    public boolean isValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOfficialUserFlag() {
        return officialUserFlag;
    }

    public void setOfficialUserFlag(int officialUserFlag) {
        this.officialUserFlag = officialUserFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(String lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<ObjectEkUser> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.validEmail ? (byte) 1 : (byte) 0);
        dest.writeString(this.userName);
        dest.writeString(this.updatedAt);
        dest.writeString(this.password);
        dest.writeInt(this.officialUserFlag);
        dest.writeString(this.nickName);
        dest.writeString(this.name);
        dest.writeString(this.lastLoginDatetime);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.email);
        dest.writeString(this.createdAt);
        dest.writeInt(this.id);
    }

    public ObjectEkUser(String nickName) {
        this.nickName = nickName;
    }

    protected ObjectEkUser(Parcel in) {
        this.validEmail = in.readByte() != 0;
        this.userName = in.readString();
        this.updatedAt = in.readString();
        this.password = in.readString();
        this.officialUserFlag = in.readInt();
        this.nickName = in.readString();
        this.name = in.readString();
        this.lastLoginDatetime = in.readString();
        this.isActive = in.readByte() != 0;
        this.email = in.readString();
        this.createdAt = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<ObjectEkUser> CREATOR = new Creator<ObjectEkUser>() {
        @Override
        public ObjectEkUser createFromParcel(Parcel source) {
            return new ObjectEkUser(source);
        }

        @Override
        public ObjectEkUser[] newArray(int size) {
            return new ObjectEkUser[size];
        }
    };
}
