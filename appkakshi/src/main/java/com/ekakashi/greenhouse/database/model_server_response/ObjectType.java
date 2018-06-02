package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ptloc on 1/30/2018.
 */

public class ObjectType implements Parcelable {
    private int type;
    private String description;
    private boolean isSelect;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.description);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public ObjectType(int type, String description, boolean selected) {
        this.type = type;
        this.description = description;
        this.isSelect = selected;
    }

    protected ObjectType(Parcel in) {
        this.type = in.readInt();
        this.description = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ObjectType> CREATOR = new Parcelable.Creator<ObjectType>() {
        @Override
        public ObjectType createFromParcel(Parcel source) {
            return new ObjectType(source);
        }

        @Override
        public ObjectType[] newArray(int size) {
            return new ObjectType[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
