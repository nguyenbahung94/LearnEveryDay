package com.ekakashi.greenhouse.features.timeline_post.post_desciption;

import android.os.Parcel;
import android.os.Parcelable;

public class PostDescriptionItem implements Parcelable {
    private String name;
    private boolean isSelected;

    public PostDescriptionItem() {
    }

    public PostDescriptionItem(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected PostDescriptionItem(Parcel in) {
        this.name = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<PostDescriptionItem> CREATOR = new Creator<PostDescriptionItem>() {
        @Override
        public PostDescriptionItem createFromParcel(Parcel source) {
            return new PostDescriptionItem(source);
        }

        @Override
        public PostDescriptionItem[] newArray(int size) {
            return new PostDescriptionItem[size];
        }
    };
}
