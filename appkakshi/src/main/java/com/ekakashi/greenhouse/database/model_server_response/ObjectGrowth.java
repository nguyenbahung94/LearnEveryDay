package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ptloc on 12/8/2017.
 */

public class ObjectGrowth implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("stageName")
    private String name;
    @SerializedName("stageDescription")
    private String description;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("sortNo")
    private int orderPos;
    @SerializedName("rules")
    private List<ObjectRule> rules;
    @SerializedName("status")
    private int status;
    private boolean select;

    public ObjectGrowth() {
    }

    public int getStatus() {
        return status;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public List<ObjectRule> getRules() {
        return rules;
    }

    public void setRules(List<ObjectRule> rules) {
        this.rules = rules;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderPos() {
        return orderPos;
    }

    public void setOrderPos(int orderPos) {
        this.orderPos = orderPos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public static Creator<ObjectGrowth> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.orderPos);
        dest.writeTypedList(this.rules);
        dest.writeInt(this.status);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    protected ObjectGrowth(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.orderPos = in.readInt();
        this.rules = in.createTypedArrayList(ObjectRule.CREATOR);
        this.status = in.readInt();
        this.select = in.readByte() != 0;
    }

    public static final Creator<ObjectGrowth> CREATOR = new Creator<ObjectGrowth>() {
        @Override
        public ObjectGrowth createFromParcel(Parcel source) {
            return new ObjectGrowth(source);
        }

        @Override
        public ObjectGrowth[] newArray(int size) {
            return new ObjectGrowth[size];
        }
    };
}
