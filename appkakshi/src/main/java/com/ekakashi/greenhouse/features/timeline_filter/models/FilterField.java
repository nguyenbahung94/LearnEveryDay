package com.ekakashi.greenhouse.features.timeline_filter.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.database.model_server_response.EnumUserRoleOnField;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 1/6/2018.
 */

public class FilterField implements Parcelable {

    @SerializedName("ekFieldId")
    public Integer id;
    @SerializedName("ekFieldName")
    private String placeName;
    @SerializedName("userRoleOnField")
    private int userRoleOnField;
    @SerializedName("nameJapan")
    private String nameJapan;
    private boolean isSelected;

    public String getNameJapan() {
        return nameJapan;
    }

    public int getUserRoleOnField() {
        return userRoleOnField;
    }

    public void setUserRoleOnField(int userRoleOnField) {
        this.userRoleOnField = userRoleOnField;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public EnumUserRoleOnField getRoleOnField() {
        switch (userRoleOnField) {
            case 0:
                return EnumUserRoleOnField.Owner;
            case 1:
                return EnumUserRoleOnField.Administrator;
            case 2:
                return EnumUserRoleOnField.Worker;
            case 3:
                return EnumUserRoleOnField.Viewer;
        }
        return null;
    }


    public FilterField() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.placeName);
        dest.writeInt(this.userRoleOnField);
        dest.writeString(this.nameJapan);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected FilterField(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.placeName = in.readString();
        this.userRoleOnField = in.readInt();
        this.nameJapan = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<FilterField> CREATOR = new Creator<FilterField>() {
        @Override
        public FilterField createFromParcel(Parcel source) {
            return new FilterField(source);
        }

        @Override
        public FilterField[] newArray(int size) {
            return new FilterField[size];
        }
    };
}
