package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

public class ObjectPrefecture implements Parcelable {
    private String name;
    private String japanName;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJapanName() {
        return japanName;
    }

    public ObjectPrefecture(String name, String japanName) {
        this.name = name;
        this.japanName = japanName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.japanName);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected ObjectPrefecture(Parcel in) {
        this.name = in.readString();
        this.japanName = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<ObjectPrefecture> CREATOR = new Creator<ObjectPrefecture>() {
        @Override
        public ObjectPrefecture createFromParcel(Parcel source) {
            return new ObjectPrefecture(source);
        }

        @Override
        public ObjectPrefecture[] newArray(int size) {
            return new ObjectPrefecture[size];
        }
    };
}
