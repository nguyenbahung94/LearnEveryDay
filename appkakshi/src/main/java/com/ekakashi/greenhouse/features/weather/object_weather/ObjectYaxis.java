package com.ekakashi.greenhouse.features.weather.object_weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 3/8/2018.
 */

public class ObjectYaxis {
    @SerializedName("id")
    public int id;
    @SerializedName("graphV3Id")
    public int graphV3Id;
    @SerializedName("axisSide")
    public int axisSide;
    @SerializedName("measuringInstrument")
    public int measuringInstrument;
    @SerializedName("aggregateType")
    public int aggregateType;
    @SerializedName("isIntegration")
    public boolean isIntegration;
    @SerializedName("basisValue")
    public Integer basisValue;
    @SerializedName("graphType")
    public int graphType;
    @SerializedName("ygraphMeasurementItemId")
    public int ygraphMeasurementItemId;

    private boolean isNewYaxis = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGraphV3Id() {
        return graphV3Id;
    }

    public void setGraphV3Id(int graphV3Id) {
        this.graphV3Id = graphV3Id;
    }

    public int getAxisSide() {
        return axisSide;
    }

    public void setAxisSide(int axisSide) {
        this.axisSide = axisSide;
    }

    public int getMeasuringInstrument() {
        return measuringInstrument;
    }

    public void setMeasuringInstrument(int measuringInstrument) {
        this.measuringInstrument = measuringInstrument;
    }

    public int getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(int aggregateType) {
        this.aggregateType = aggregateType;
    }

    public boolean isIntegration() {
        return isIntegration;
    }

    public void setIntegration(boolean integration) {
        isIntegration = integration;
    }

    public Integer getBasisValue() {
        return basisValue;
    }

    public void setBasisValue(Integer basisValue) {
        this.basisValue = basisValue;
    }

    public int getGraphType() {
        return graphType;
    }

    public void setGraphType(int graphType) {
        this.graphType = graphType;
    }

    public int getYgraphMeasurementItemId() {
        return ygraphMeasurementItemId;
    }

    public void setYgraphMeasurementItemId(int ygraphMeasurementItemId) {
        this.ygraphMeasurementItemId = ygraphMeasurementItemId;
    }

    public boolean isNewYaxis() {
        return isNewYaxis;
    }

    public void setNewYaxis(boolean newYaxis) {
        isNewYaxis = newYaxis;
    }

    @Override
    public String toString() {
        return "ObjectYaxis{" +
                "id=" + id +
                ", graphV3Id=" + graphV3Id +
                ", axisSide=" + axisSide +
                ", measuringInstrument=" + measuringInstrument +
                ", aggregateType=" + aggregateType +
                ", isIntegration=" + isIntegration +
                ", basisValue=" + basisValue +
                ", graphType=" + graphType +
                ", ygraphMeasurementItemId=" + ygraphMeasurementItemId +
                ", isNewYaxis=" + isNewYaxis +
                '}';
    }
}
