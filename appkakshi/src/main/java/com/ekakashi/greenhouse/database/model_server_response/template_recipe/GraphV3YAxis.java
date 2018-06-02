package com.ekakashi.greenhouse.database.model_server_response.template_recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 3/20/2018.
 */

public class GraphV3YAxis implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("graphV3Id")
    private int graphV3Id;
    @SerializedName("axisSide")
    private int axisSide;
    @SerializedName("measuringInstrument")
    private int measuringInstrument;
    @SerializedName("aggregateType")
    private int aggregateType;
    @SerializedName("isIntegration")
    private boolean isIntegration;
    @SerializedName("basisValue")
    private int basisValue;
    @SerializedName("graphType")
    private int graphType;
    @SerializedName("ygraphMeasurementItemId")
    private int ygraphMeasurementItemId;
    @SerializedName("color")
    private String color;

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getGraphV3Id() {
        return graphV3Id;
    }

    public int getAxisSide() {
        return axisSide;
    }

    public int getMeasuringInstrument() {
        return measuringInstrument;
    }

    public int getAggregateType() {
        return aggregateType;
    }

    public boolean isIntegration() {
        return isIntegration;
    }

    public int getBasisValue() {
        return basisValue;
    }

    public int getGraphType() {
        return graphType;
    }

    public int getYgraphMeasurementItemId() {
        return ygraphMeasurementItemId;
    }

    public GraphV3YAxis() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.graphV3Id);
        dest.writeInt(this.axisSide);
        dest.writeInt(this.measuringInstrument);
        dest.writeInt(this.aggregateType);
        dest.writeByte(this.isIntegration ? (byte) 1 : (byte) 0);
        dest.writeInt(this.basisValue);
        dest.writeInt(this.graphType);
        dest.writeInt(this.ygraphMeasurementItemId);
        dest.writeString(this.color);
    }

    protected GraphV3YAxis(Parcel in) {
        this.id = in.readInt();
        this.graphV3Id = in.readInt();
        this.axisSide = in.readInt();
        this.measuringInstrument = in.readInt();
        this.aggregateType = in.readInt();
        this.isIntegration = in.readByte() != 0;
        this.basisValue = in.readInt();
        this.graphType = in.readInt();
        this.ygraphMeasurementItemId = in.readInt();
        this.color = in.readString();
    }

    public static final Creator<GraphV3YAxis> CREATOR = new Creator<GraphV3YAxis>() {
        @Override
        public GraphV3YAxis createFromParcel(Parcel source) {
            return new GraphV3YAxis(source);
        }

        @Override
        public GraphV3YAxis[] newArray(int size) {
            return new GraphV3YAxis[size];
        }
    };
}
