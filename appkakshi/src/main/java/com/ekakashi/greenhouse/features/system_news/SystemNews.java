package com.ekakashi.greenhouse.features.system_news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/25/2018.
 */

public class SystemNews implements Parcelable {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ArrayList<Data> data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Data> getData() {
        return data;
    }


    public static class Data implements Parcelable {
        @SerializedName("outline")
        public String outline;
        @SerializedName("detail")
        public String detail;
        @SerializedName("startDate")
        public String createdAt;
        @SerializedName("endDate")
        public String endDate;
        @SerializedName("id")
        public int id;

        public String getOutline() {
            return outline;
        }

        public String getDetail() {
            return detail;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getEndDate() {
            return endDate;
        }

        public int getId() {
            return id;
        }

        public Data() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.outline);
            dest.writeString(this.detail);
            dest.writeString(this.createdAt);
            dest.writeString(this.endDate);
            dest.writeInt(this.id);
        }

        protected Data(Parcel in) {
            this.outline = in.readString();
            this.detail = in.readString();
            this.createdAt = in.readString();
            this.endDate = in.readString();
            this.id = in.readInt();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    public SystemNews() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    protected SystemNews(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<SystemNews> CREATOR = new Creator<SystemNews>() {
        @Override
        public SystemNews createFromParcel(Parcel source) {
            return new SystemNews(source);
        }

        @Override
        public SystemNews[] newArray(int size) {
            return new SystemNews[size];
        }
    };
}
