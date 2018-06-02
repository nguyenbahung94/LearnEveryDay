package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ptloc on 3/28/2018.
 */

public class ObjectCrop implements Parcelable {
    private int id;
    private String name;
    private String nameJapan;
    private int categoryId;
    private String createAt;
    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNameJapan() {
        return nameJapan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nameJapan);
        dest.writeInt(this.categoryId);
        dest.writeString(this.createAt);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public ObjectCrop() {
    }

    protected ObjectCrop(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.nameJapan = in.readString();
        this.categoryId = in.readInt();
        this.createAt = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<ObjectCrop> CREATOR = new Creator<ObjectCrop>() {
        @Override
        public ObjectCrop createFromParcel(Parcel source) {
            return new ObjectCrop(source);
        }

        @Override
        public ObjectCrop[] newArray(int size) {
            return new ObjectCrop[size];
        }
    };
}
