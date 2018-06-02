package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/2/2018.
 */

public class EkUserConfig implements Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("ekUser")
    public String ekUser;
    @SerializedName("locale")
    public String locale;
    @SerializedName("timezone")
    public String timezone;
    @SerializedName("emailCharset")
    public String emailCharset;
    @SerializedName("defaultPage")
    public String defaultPage;
    @SerializedName("selectedClientId")
    public int selectedClientId;
    @SerializedName("noticeOpenDateTime")
    public String noticeOpenDateTime;
    @SerializedName("ekUserConfigGwConfigs")
    public String ekUserConfigGwConfigs;
    @SerializedName("ekUserConfigSnConfigs")
    public String ekUserConfigSnConfigs;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;

    public int getId() {
        return id;
    }

    public String getEkUser() {
        return ekUser;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getEmailCharset() {
        return emailCharset;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public int getSelectedClientId() {
        return selectedClientId;
    }

    public String getNoticeOpenDateTime() {
        return noticeOpenDateTime;
    }

    public String getEkUserConfigGwConfigs() {
        return ekUserConfigGwConfigs;
    }

    public String getEkUserConfigSnConfigs() {
        return ekUserConfigSnConfigs;
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
        dest.writeString(this.ekUser);
        dest.writeString(this.locale);
        dest.writeString(this.timezone);
        dest.writeString(this.emailCharset);
        dest.writeString(this.defaultPage);
        dest.writeInt(this.selectedClientId);
        dest.writeString(this.noticeOpenDateTime);
        dest.writeString(this.ekUserConfigGwConfigs);
        dest.writeString(this.ekUserConfigSnConfigs);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
    }

    public EkUserConfig() {
    }

    protected EkUserConfig(Parcel in) {
        this.id = in.readInt();
        this.ekUser = in.readString();
        this.locale = in.readString();
        this.timezone = in.readString();
        this.emailCharset = in.readString();
        this.defaultPage = in.readString();
        this.selectedClientId = in.readInt();
        this.noticeOpenDateTime = in.readString();
        this.ekUserConfigGwConfigs = in.readString();
        this.ekUserConfigSnConfigs = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
    }

    public static final Creator<EkUserConfig> CREATOR = new Creator<EkUserConfig>() {
        @Override
        public EkUserConfig createFromParcel(Parcel source) {
            return new EkUserConfig(source);
        }

        @Override
        public EkUserConfig[] newArray(int size) {
            return new EkUserConfig[size];
        }
    };
}
