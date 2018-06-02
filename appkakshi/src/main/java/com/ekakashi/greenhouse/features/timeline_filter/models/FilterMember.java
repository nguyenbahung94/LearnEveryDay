package com.ekakashi.greenhouse.features.timeline_filter.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 1/12/2018.
 */

public class FilterMember implements Parcelable {

    @SerializedName("ekUserId")
    public Integer ekUserId;
    @SerializedName("userName")
    public String userName;

    private boolean isSelected;

    public Integer getEkUserId() {
        return ekUserId;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public FilterMember() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ekUserId);
        dest.writeString(this.userName);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected FilterMember(Parcel in) {
        this.ekUserId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userName = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<FilterMember> CREATOR = new Creator<FilterMember>() {
        @Override
        public FilterMember createFromParcel(Parcel source) {
            return new FilterMember(source);
        }

        @Override
        public FilterMember[] newArray(int size) {
            return new FilterMember[size];
        }
    };
}
