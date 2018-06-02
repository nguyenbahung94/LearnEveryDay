package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 1/4/2018.
 */

public class ObjectCategory implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("categoryName")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("categoryNameJapan")
    private String japanName;
    private boolean isSelected = false;

    public ObjectCategory(String name) {
        this.name = name;
    }

    public String getJapanName() {
        if (japanName == null) {
            return "";
        }
        return japanName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.japanName);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected ObjectCategory(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.japanName = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<ObjectCategory> CREATOR = new Creator<ObjectCategory>() {
        @Override
        public ObjectCategory createFromParcel(Parcel source) {
            return new ObjectCategory(source);
        }

        @Override
        public ObjectCategory[] newArray(int size) {
            return new ObjectCategory[size];
        }
    };
}
