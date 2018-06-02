package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

public class ObjectHistory implements Parcelable{

    private String id;
    private String author;
    private String createDate;

    public ObjectHistory(String author, String createDate) {
        this.author = author;
        this.createDate = createDate;
    }

    protected ObjectHistory(Parcel in) {
        id = in.readString();
        author = in.readString();
        createDate = in.readString();
    }

    public static final Creator<ObjectHistory> CREATOR = new Creator<ObjectHistory>() {
        @Override
        public ObjectHistory createFromParcel(Parcel in) {
            return new ObjectHistory(in);
        }

        @Override
        public ObjectHistory[] newArray(int size) {
            return new ObjectHistory[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(createDate);
    }
}
