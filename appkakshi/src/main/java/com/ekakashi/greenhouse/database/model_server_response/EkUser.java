package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class EkUser implements Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("userName")
    public String userName;
    @SerializedName("password")
    public String password;
    @SerializedName("salt")
    public String salt;
    @SerializedName("officialUserFlag")
    public int officialUserFlag;
    @SerializedName("name")
    public String name;
    @SerializedName("nickName")
    public String nickName;
    @SerializedName("email")
    public String email;
    @SerializedName("validEmail")
    public boolean validEmail;
    @SerializedName("lastLoginDatetime")
    public String lastLoginDatetime;
    @SerializedName("isActive")
    public boolean isActive;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;
    @SerializedName("ekUserConfig")
    public EkUserConfig ekUserConfig;
    @SerializedName("clientEkUsers")
    public ClientEkUsers clientEkUsers;
    @SerializedName("accountNonExpired")
    public boolean accountNonExpired;
    @SerializedName("credentialsNonExpired")
    public boolean credentialsNonExpired;
    @SerializedName("accountNonLocked")
    public boolean accountNonLocked;
    @SerializedName("roles")
    public String roles;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public int getOfficialUserFlag() {
        return officialUserFlag;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValidEmail() {
        return validEmail;
    }

    public String getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public EkUserConfig getEkUserConfig() {
        return ekUserConfig;
    }

    public ClientEkUsers getClientEkUsers() {
        return clientEkUsers;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public String getRoles() {
        return roles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.salt);
        dest.writeInt(this.officialUserFlag);
        dest.writeString(this.name);
        dest.writeString(this.nickName);
        dest.writeString(this.email);
        dest.writeByte(this.validEmail ? (byte) 1 : (byte) 0);
        dest.writeString(this.lastLoginDatetime);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
        dest.writeParcelable(this.ekUserConfig, flags);
        dest.writeParcelable(this.clientEkUsers, flags);
        dest.writeByte(this.accountNonExpired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.credentialsNonExpired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.accountNonLocked ? (byte) 1 : (byte) 0);
        dest.writeString(this.roles);
    }

    public EkUser() {
    }

    protected EkUser(Parcel in) {
        this.id = in.readInt();
        this.userName = in.readString();
        this.password = in.readString();
        this.salt = in.readString();
        this.officialUserFlag = in.readInt();
        this.name = in.readString();
        this.nickName = in.readString();
        this.email = in.readString();
        this.validEmail = in.readByte() != 0;
        this.lastLoginDatetime = in.readString();
        this.isActive = in.readByte() != 0;
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
        this.ekUserConfig = in.readParcelable(EkUserConfig.class.getClassLoader());
        this.clientEkUsers = in.readParcelable(ClientEkUsers.class.getClassLoader());
        this.accountNonExpired = in.readByte() != 0;
        this.credentialsNonExpired = in.readByte() != 0;
        this.accountNonLocked = in.readByte() != 0;
        this.roles = in.readString();
    }

    public static final Creator<EkUser> CREATOR = new Creator<EkUser>() {
        @Override
        public EkUser createFromParcel(Parcel source) {
            return new EkUser(source);
        }

        @Override
        public EkUser[] newArray(int size) {
            return new EkUser[size];
        }
    };
}
