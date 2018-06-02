package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptloc on 1/15/2018.
 */

public class ObjectEkField implements Parcelable {

    @SerializedName("polygon")
    private List<Polygon> polygon;
    @SerializedName("fieldType")
    private int fieldType;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("location")
    private String location;
    @SerializedName("name")
    private String name;
    @SerializedName("ekUser")
    private ObjectEkUser ekUser;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("id")
    private int id;

    public List<Polygon> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<Polygon> polygon) {
        this.polygon = polygon;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectEkUser getEkUser() {
        return ekUser;
    }

    public void setEkUser(ObjectEkUser ekUser) {
        this.ekUser = ekUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Polygon implements Parcelable {
        @SerializedName("lat")
        private float lat;
        @SerializedName("lng")
        private float lng;

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.lat);
            dest.writeFloat(this.lng);
        }

        public Polygon() {
        }

        protected Polygon(Parcel in) {
            this.lat = in.readFloat();
            this.lng = in.readFloat();
        }

        public static final Creator<Polygon> CREATOR = new Creator<Polygon>() {
            @Override
            public Polygon createFromParcel(Parcel source) {
                return new Polygon(source);
            }

            @Override
            public Polygon[] newArray(int size) {
                return new Polygon[size];
            }
        };
    }

    public ObjectEkField() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.polygon);
        dest.writeInt(this.fieldType);
        dest.writeString(this.updatedAt);
        dest.writeString(this.startDate);
        dest.writeString(this.location);
        dest.writeString(this.name);
        dest.writeParcelable(this.ekUser, flags);
        dest.writeString(this.createdAt);
        dest.writeInt(this.id);
    }

    protected ObjectEkField(Parcel in) {
        this.polygon = new ArrayList<Polygon>();
        in.readList(this.polygon, Polygon.class.getClassLoader());
        this.fieldType = in.readInt();
        this.updatedAt = in.readString();
        this.startDate = in.readString();
        this.location = in.readString();
        this.name = in.readString();
        this.ekUser = in.readParcelable(ObjectEkUser.class.getClassLoader());
        this.createdAt = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<ObjectEkField> CREATOR = new Creator<ObjectEkField>() {
        @Override
        public ObjectEkField createFromParcel(Parcel source) {
            return new ObjectEkField(source);
        }

        @Override
        public ObjectEkField[] newArray(int size) {
            return new ObjectEkField[size];
        }
    };
}
