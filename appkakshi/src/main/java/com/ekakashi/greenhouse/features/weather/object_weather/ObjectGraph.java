package com.ekakashi.greenhouse.features.weather.object_weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 3/8/2018.
 */

public class ObjectGraph {

    @SerializedName("id")
    public int id;
    @SerializedName("templateConfigId")
    public int templateConfigId;
    @SerializedName("preriod")
    public int preriod;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    @SerializedName("isDisplayStage")
    public boolean isDisplayStage;
    @SerializedName("startStage")
    public String startStage;
    @SerializedName("endStage")
    public String endStage;
    @SerializedName("sortNo")
    public int sortNo;
    @SerializedName("graphV3YAxis")
    public ArrayList<ObjectYaxis> yAxis;
    @SerializedName("xaxisMovingAverage")
    public boolean xaxisMovingAverage;
    @SerializedName("xaxisDatetime")
    public boolean xaxisDatetime;
    @SerializedName("xaxisIntegration")
    public boolean xaxisIntegration;
    @SerializedName("xaxisSenorMesureItemEquipment")
    public int xaxisSenorMesureItemEquipment;
    @SerializedName("xaxisAggregateUnit")
    public Integer xaxisAggregateUnit;
    @SerializedName("xaxisRange")
    public Integer xaxisRange;
    @SerializedName("xaxisAggregationmethod")
    public Integer xaxisAggregationMethod;
    @SerializedName("xaxisBasisValue")
    public Integer xaxisBasisValue;
    @SerializedName("xgraphMeasurementItem")
    public int xgraphMeasurementItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplateConfigId() {
        return templateConfigId;
    }

    public void setTemplateConfigId(int templateConfigId) {
        this.templateConfigId = templateConfigId;
    }

    public int getPreriod() {
        return preriod;
    }

    public void setPreriod(int preriod) {
        this.preriod = preriod;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isDisplayStage() {
        return isDisplayStage;
    }

    public void setDisplayStage(boolean displayStage) {
        isDisplayStage = displayStage;
    }

    public String getStartStage() {
        return startStage;
    }

    public void setStartStage(String startStage) {
        this.startStage = startStage;
    }

    public String getEndStage() {
        return endStage;
    }

    public void setEndStage(String endStage) {
        this.endStage = endStage;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public ArrayList<ObjectYaxis> getYaxis() {
        return yAxis;
    }

    public void setYaxis(ArrayList<ObjectYaxis> yAxis) {
        this.yAxis = yAxis;
    }

    public boolean isXaxisMovingAverage() {
        return xaxisMovingAverage;
    }

    public void setXaxisMovingAverage(boolean xaxisMovingAverage) {
        this.xaxisMovingAverage = xaxisMovingAverage;
    }

    public boolean isXaxisDatetime() {
        return xaxisDatetime;
    }

    public void setXaxisDatetime(boolean xaxisDatetime) {
        this.xaxisDatetime = xaxisDatetime;
    }

    public boolean isXaxisIntegration() {
        return xaxisIntegration;
    }

    public void setXaxisIntegration(boolean xaxisIntegration) {
        this.xaxisIntegration = xaxisIntegration;
    }

    public int getXaxisSenorMesureItemEquipment() {
        return xaxisSenorMesureItemEquipment;
    }

    public void setXaxisSenorMesureItemEquipment(int xaxisSenorMesureItemEquipment) {
        this.xaxisSenorMesureItemEquipment = xaxisSenorMesureItemEquipment;
    }

    public Integer getXaxisAggregateUnit() {
        return xaxisAggregateUnit;
    }

    public void setXaxisAggregateUnit(Integer xaxisAggregateUnit) {
        this.xaxisAggregateUnit = xaxisAggregateUnit;
    }

    public Integer getXaxisRange() {
        return xaxisRange;
    }

    public void setXaxisRange(Integer xaxisRange) {
        this.xaxisRange = xaxisRange;
    }

    public Integer getXaxisAggregationMethod() {
        return xaxisAggregationMethod;
    }

    public void setXaxisAggregationMethod(Integer xaxisAggregationMethod) {
        this.xaxisAggregationMethod = xaxisAggregationMethod;
    }

    public Integer getXaxisBasisValue() {
        return xaxisBasisValue;
    }

    public void setXaxisBasisValue(Integer xaxisBasisValue) {
        this.xaxisBasisValue = xaxisBasisValue;
    }

    public int getXgraphMeasurementItem() {
        return xgraphMeasurementItem;
    }

    public void setXgraphMeasurementItem(int xgraphMeasurementItem) {
        this.xgraphMeasurementItem = xgraphMeasurementItem;
    }

    @Override
    public String toString() {
        return "ObjectGraph{" +
                "id=" + id +
                ", templateConfigId=" + templateConfigId +
                ", preriod=" + preriod +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isDisplayStage=" + isDisplayStage +
                ", startStage='" + startStage + '\'' +
                ", endStage='" + endStage + '\'' +
                ", sortNo=" + sortNo +
                ", yAxis=" + yAxis +
                ", xaxisMovingAverage=" + xaxisMovingAverage +
                ", xaxisDatetime=" + xaxisDatetime +
                ", xaxisIntegration=" + xaxisIntegration +
                ", xaxisSenorMesureItemEquipment=" + xaxisSenorMesureItemEquipment +
                ", xaxisAggregateUnit=" + xaxisAggregateUnit +
                ", xaxisRange=" + xaxisRange +
                ", xaxisAggregationMethod=" + xaxisAggregationMethod +
                ", xaxisBasisValue=" + xaxisBasisValue +
                ", xgraphMeasurementItem=" + xgraphMeasurementItem +
                '}';
    }
}
