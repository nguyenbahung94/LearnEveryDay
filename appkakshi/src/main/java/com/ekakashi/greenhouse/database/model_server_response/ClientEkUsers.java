package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/2/2018.
 */

public class ClientEkUsers implements Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("authClass")
    public int authClass;
    @SerializedName("snConfigs")
    public String snConfigs;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;

    public int getId() {
        return id;
    }

    public int getAuthClass() {
        return authClass;
    }

    public String getSnConfigs() {
        return snConfigs;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.authClass);
        dest.writeString(this.snConfigs);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
    }

    public ClientEkUsers() {
    }

    protected ClientEkUsers(Parcel in) {
        this.id = in.readInt();
        this.authClass = in.readInt();
        this.snConfigs = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
    }

    public static final Creator<ClientEkUsers> CREATOR = new Creator<ClientEkUsers>() {
        @Override
        public ClientEkUsers createFromParcel(Parcel source) {
            return new ClientEkUsers(source);
        }

        @Override
        public ClientEkUsers[] newArray(int size) {
            return new ClientEkUsers[size];
        }
    };
}
