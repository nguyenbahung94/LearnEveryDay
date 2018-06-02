package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 12/26/2017.
 */

public class ObjectCondition implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;//condition 1 or 2
    @SerializedName("deviceType")
    private int deviceType;
    @SerializedName("deviceValue")
    private int measurementItem;//measurement item
    @SerializedName("countingMethod")
    private Integer countingMethod;
    @SerializedName("determinationValue")
    private int determinationValue;
    @SerializedName("determinationMethod")
    private int determinationStandard;//determination standard
    @SerializedName("determinationTarget")
    private Integer additionalInformation;//stage id
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("sortNo")
    private int sortNo;
    @SerializedName("logicalOperator")
    private boolean logicalOperator;
    @SerializedName("stage")
    @Expose
    private ObjectGrowth stage;
    private String content;
    private String stageName;
    private String aggregationMethod;
    private String measurementItemString;
    private String determinationStandardString;

    public ObjectCondition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getMeasurementItem() {
        return measurementItem;
    }

    public void setMeasurementItem(int measurementItem) {
        this.measurementItem = measurementItem;
    }

    public Integer getCountingMethod() {
        return countingMethod;
    }

    public void setCountingMethod(Integer countingMethod) {
        this.countingMethod = countingMethod;
    }

    public int getDeterminationValue() {
        return determinationValue;
    }

    public void setDeterminationValue(int determinationValue) {
        this.determinationValue = determinationValue;
    }

    public int getDeterminationStandard() {
        return determinationStandard;
    }

    public void setDeterminationStandard(int determinationStandard) {
        this.determinationStandard = determinationStandard;
    }

    public Integer getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Integer additionalInformation) {
        this.additionalInformation = additionalInformation;
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

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public boolean isLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(boolean logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getAggregationMethod() {
        return aggregationMethod;
    }

    public void setAggregationMethod(String aggregationMethod) {
        this.aggregationMethod = aggregationMethod;
    }

    public String getMeasurementItemString() {
        return measurementItemString;
    }

    public void setMeasurementItemString(String measurementItemString) {
        this.measurementItemString = measurementItemString;
    }

    public String getDeterminationStandardString() {
        return determinationStandardString;
    }

    public void setDeterminationStandardString(String determinationStandardString) {
        this.determinationStandardString = determinationStandardString;
    }

    public ObjectGrowth getStage() {
        return stage;
    }

    public void setStage(ObjectGrowth stage) {
        this.stage = stage;
    }

    public static Creator<ObjectCondition> getCREATOR() {
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
        dest.writeInt(this.deviceType);
        dest.writeInt(this.measurementItem);
        dest.writeValue(this.countingMethod);
        dest.writeInt(this.determinationValue);
        dest.writeInt(this.determinationStandard);
        dest.writeValue(this.additionalInformation);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.sortNo);
        dest.writeByte(this.logicalOperator ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.stage, flags);
        dest.writeString(this.content);
        dest.writeString(this.stageName);
        dest.writeString(this.aggregationMethod);
        dest.writeString(this.measurementItemString);
        dest.writeString(this.determinationStandardString);
    }

    protected ObjectCondition(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.deviceType = in.readInt();
        this.measurementItem = in.readInt();
        this.countingMethod = (Integer) in.readValue(Integer.class.getClassLoader());
        this.determinationValue = in.readInt();
        this.determinationStandard = in.readInt();
        this.additionalInformation = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.sortNo = in.readInt();
        this.logicalOperator = in.readByte() != 0;
        this.stage = in.readParcelable(ObjectGrowth.class.getClassLoader());
        this.content = in.readString();
        this.stageName = in.readString();
        this.aggregationMethod = in.readString();
        this.measurementItemString = in.readString();
        this.determinationStandardString = in.readString();
    }

    public static final Creator<ObjectCondition> CREATOR = new Creator<ObjectCondition>() {
        @Override
        public ObjectCondition createFromParcel(Parcel source) {
            return new ObjectCondition(source);
        }

        @Override
        public ObjectCondition[] newArray(int size) {
            return new ObjectCondition[size];
        }
    };
}
