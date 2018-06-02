package com.ekakashi.greenhouse.features.timeline_post.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ImageViewer implements Parcelable {
    private String url;
    private ImageType type;

    public ImageViewer() {
    }

    public ImageViewer(String url, ImageType type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected ImageViewer(Parcel in) {
        this.url = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : ImageType.values()[tmpType];
    }

    public static final Creator<ImageViewer> CREATOR = new Creator<ImageViewer>() {
        @Override
        public ImageViewer createFromParcel(Parcel source) {
            return new ImageViewer(source);
        }

        @Override
        public ImageViewer[] newArray(int size) {
            return new ImageViewer[size];
        }
    };

    public enum ImageType {
        Diary,
        Gallery,
        Camera
    }
}
