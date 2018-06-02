package com.ekakashi.greenhouse.database.model_server_response.template_recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 3/20/2018.
 */

public class Graph implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("temType")
    private int temType;
    @SerializedName("templateConfigId")
    private int templateConfigId;
    @SerializedName("preriod")
    private int preriod;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("isDisplayStage")
    private boolean isDisplayStage;
    @SerializedName("startStage")
    private String startStage;
    @SerializedName("endStage")
    private String endStage;
    @SerializedName("sortNo")
    private int sortNo;
    @SerializedName("isXAxisMovingAverage")
    private boolean isXAxisMovingAverage;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("deletedAt")
    private String deletedAt;
    @SerializedName("tags")
    private String tags;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;
    @SerializedName("numberOfColumn")
    private String numberOfColumn;
    @SerializedName("graphV3YAxis")
    private List<GraphV3YAxis> graphV3YAxis;
    @SerializedName("xaxisMovingAverage")
    private boolean xaxisMovingAverage;
    @SerializedName("xaxisDatetime")
    private boolean xaxisDatetime;
    @SerializedName("xaxisIntegration")
    private boolean xaxisIntegration;
    @SerializedName("xgraphMeasurementItemId")
    private int xgraphMeasurementItemId;
    @SerializedName("xaxisAggregateType")
    private int xaxisAggregateType;
    @SerializedName("xaxisSenorMesureItemType")
    private String xaxisSenorMesureItemType;
    @SerializedName("xaxisMovingAverageNum")
    private String xaxisMovingAverageNum;
    @SerializedName("xaxisSenorMesureItemEquipment")
    private int xaxisSenorMesureItemEquipment;
    @SerializedName("xaxisAggregateUnit")
    private String xaxisAggregateUnit;
    @SerializedName("xaxisRange")
    private String xaxisRange;
    @SerializedName("xaxisAggregationmethod")
    private String xaxisAggregationmethod;
    @SerializedName("xaxisBasisValue")
    private String xaxisBasisValue;
    @SerializedName("xgraphMeasurementItem")
    private int xgraphMeasurementItem;

    public int getId() {
        return id;
    }

    public int getTemType() {
        return temType;
    }

    public int getTemplateConfigId() {
        return templateConfigId;
    }

    public int getPreriod() {
        return preriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isDisplayStage() {
        return isDisplayStage;
    }

    public String getStartStage() {
        return startStage;
    }

    public String getEndStage() {
        return endStage;
    }

    public int getSortNo() {
        return sortNo;
    }

    public boolean isXAxisMovingAverage() {
        return isXAxisMovingAverage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public String getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getNumberOfColumn() {
        return numberOfColumn;
    }

    public List<GraphV3YAxis> getGraphV3YAxis() {
        return graphV3YAxis;
    }

    public boolean isXaxisMovingAverage() {
        return xaxisMovingAverage;
    }

    public boolean isXaxisDatetime() {
        return xaxisDatetime;
    }

    public boolean isXaxisIntegration() {
        return xaxisIntegration;
    }

    public int getXgraphMeasurementItemId() {
        return xgraphMeasurementItemId;
    }

    public int getXaxisAggregateType() {
        return xaxisAggregateType;
    }

    public String getXaxisSenorMesureItemType() {
        return xaxisSenorMesureItemType;
    }

    public String getXaxisMovingAverageNum() {
        return xaxisMovingAverageNum;
    }

    public int getXaxisSenorMesureItemEquipment() {
        return xaxisSenorMesureItemEquipment;
    }

    public String getXaxisAggregateUnit() {
        return xaxisAggregateUnit;
    }

    public String getXaxisRange() {
        return xaxisRange;
    }

    public String getXaxisAggregationmethod() {
        return xaxisAggregationmethod;
    }

    public String getXaxisBasisValue() {
        return xaxisBasisValue;
    }

    public int getXgraphMeasurementItem() {
        return xgraphMeasurementItem;
    }

    public Graph() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.temType);
        dest.writeInt(this.templateConfigId);
        dest.writeInt(this.preriod);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeByte(this.isDisplayStage ? (byte) 1 : (byte) 0);
        dest.writeString(this.startStage);
        dest.writeString(this.endStage);
        dest.writeInt(this.sortNo);
        dest.writeByte(this.isXAxisMovingAverage ? (byte) 1 : (byte) 0);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
        dest.writeString(this.tags);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.numberOfColumn);
        dest.writeTypedList(this.graphV3YAxis);
        dest.writeByte(this.xaxisMovingAverage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.xaxisDatetime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.xaxisIntegration ? (byte) 1 : (byte) 0);
        dest.writeInt(this.xgraphMeasurementItemId);
        dest.writeInt(this.xaxisAggregateType);
        dest.writeString(this.xaxisSenorMesureItemType);
        dest.writeString(this.xaxisMovingAverageNum);
        dest.writeInt(this.xaxisSenorMesureItemEquipment);
        dest.writeString(this.xaxisAggregateUnit);
        dest.writeString(this.xaxisRange);
        dest.writeString(this.xaxisAggregationmethod);
        dest.writeString(this.xaxisBasisValue);
        dest.writeInt(this.xgraphMeasurementItem);
    }

    protected Graph(Parcel in) {
        this.id = in.readInt();
        this.temType = in.readInt();
        this.templateConfigId = in.readInt();
        this.preriod = in.readInt();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.isDisplayStage = in.readByte() != 0;
        this.startStage = in.readString();
        this.endStage = in.readString();
        this.sortNo = in.readInt();
        this.isXAxisMovingAverage = in.readByte() != 0;
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
        this.tags = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.numberOfColumn = in.readString();
        this.graphV3YAxis = in.createTypedArrayList(GraphV3YAxis.CREATOR);
        this.xaxisMovingAverage = in.readByte() != 0;
        this.xaxisDatetime = in.readByte() != 0;
        this.xaxisIntegration = in.readByte() != 0;
        this.xgraphMeasurementItemId = in.readInt();
        this.xaxisAggregateType = in.readInt();
        this.xaxisSenorMesureItemType = in.readString();
        this.xaxisMovingAverageNum = in.readString();
        this.xaxisSenorMesureItemEquipment = in.readInt();
        this.xaxisAggregateUnit = in.readString();
        this.xaxisRange = in.readString();
        this.xaxisAggregationmethod = in.readString();
        this.xaxisBasisValue = in.readString();
        this.xgraphMeasurementItem = in.readInt();
    }

    public static final Creator<Graph> CREATOR = new Creator<Graph>() {
        @Override
        public Graph createFromParcel(Parcel source) {
            return new Graph(source);
        }

        @Override
        public Graph[] newArray(int size) {
            return new Graph[size];
        }
    };
}
