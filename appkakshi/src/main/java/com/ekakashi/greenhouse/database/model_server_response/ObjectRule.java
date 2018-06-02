package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ptloc on 12/12/2017.
 */

public class ObjectRule implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("ruleType")
    private int ruleType;
    @SerializedName("conditions")
    private List<ObjectCondition> conditions;
    @SerializedName("actions")
    private List<ObjectAction> actions;
    @SerializedName("isNotify")
    private boolean isNotify;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public List<ObjectCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<ObjectCondition> conditions) {
        this.conditions = conditions;
    }

    public List<ObjectAction> getActions() {
        return actions;
    }

    public void setActions(List<ObjectAction> actions) {
        this.actions = actions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public ObjectRule() {
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
        dest.writeInt(this.ruleType);
        dest.writeTypedList(this.conditions);
        dest.writeTypedList(this.actions);
        dest.writeByte(this.isNotify ? (byte) 1 : (byte) 0);
    }

    protected ObjectRule(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.ruleType = in.readInt();
        this.conditions = in.createTypedArrayList(ObjectCondition.CREATOR);
        this.actions = in.createTypedArrayList(ObjectAction.CREATOR);
        this.isNotify = in.readByte() != 0;
    }

    public static final Creator<ObjectRule> CREATOR = new Creator<ObjectRule>() {
        @Override
        public ObjectRule createFromParcel(Parcel source) {
            return new ObjectRule(source);
        }

        @Override
        public ObjectRule[] newArray(int size) {
            return new ObjectRule[size];
        }
    };
}
