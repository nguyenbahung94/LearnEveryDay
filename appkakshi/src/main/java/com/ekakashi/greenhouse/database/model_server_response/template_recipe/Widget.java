package com.ekakashi.greenhouse.database.model_server_response.template_recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 3/21/2018.
 */

public class Widget implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("widgetType")
    private int widgetType;
    @SerializedName("sortNo")
    private int sortNo;
    @SerializedName("imagePath")
    private String imagePath;
    @SerializedName("widgetMeasureItemId")
    private int widgetMeasureItemId;

    public int getId() {
        return id;
    }

    public int getWidgetType() {
        return widgetType;
    }

    public int getSortNo() {
        return sortNo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getWidgetMeasureItemId() {
        return widgetMeasureItemId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.widgetType);
        dest.writeInt(this.sortNo);
        dest.writeString(this.imagePath);
        dest.writeInt(this.widgetMeasureItemId);
    }

    public Widget() {
    }

    protected Widget(Parcel in) {
        this.id = in.readInt();
        this.widgetType = in.readInt();
        this.sortNo = in.readInt();
        this.imagePath = in.readString();
        this.widgetMeasureItemId = in.readInt();
    }

    public static final Parcelable.Creator<Widget> CREATOR = new Parcelable.Creator<Widget>() {
        @Override
        public Widget createFromParcel(Parcel source) {
            return new Widget(source);
        }

        @Override
        public Widget[] newArray(int size) {
            return new Widget[size];
        }
    };
}
