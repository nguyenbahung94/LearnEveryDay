package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class CheckingRequest {

    @SerializedName("BaId")
    @Expose
    public Integer baId;
    @SerializedName("OutletLocationId")
    @Expose
    public Integer outletLocationId;
    @SerializedName("RouteId")
    @Expose
    public Integer routeId;
    @SerializedName("Suggestion")
    @Expose
    public String suggestion;
    @SerializedName("LngBefore")
    @Expose
    public double lngBefore;
    @SerializedName("LatBefore")
    @Expose
    public double latBefore;
    @SerializedName("LngAfter")
    @Expose
    public double lngAfter;
    @SerializedName("LatAfter")
    @Expose
    public double latAfter;
    @SerializedName("Answers")
    @Expose
    public List<Answer> answers = null;
    @SerializedName("Long")
    @Expose
    public Float _long;
    @SerializedName("Lat")
    @Expose
    public String lat;
    @SerializedName("Image")
    @Expose
    public String image;

    public Integer getBaId() {
        return baId;
    }

    public void setBaId(Integer baId) {
        this.baId = baId;
    }

    public Integer getOutletLocationId() {
        return outletLocationId;
    }

    public void setOutletLocationId(Integer outletLocationId) {
        this.outletLocationId = outletLocationId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public double getLngBefore() {
        return lngBefore;
    }

    public void setLngBefore(double lngBefore) {
        this.lngBefore = lngBefore;
    }

    public double getLatBefore() {
        return latBefore;
    }

    public void setLatBefore(double latBefore) {
        this.latBefore = latBefore;
    }

    public double getLngAfter() {
        return lngAfter;
    }

    public void setLngAfter(double lngAfter) {
        this.lngAfter = lngAfter;
    }

    public double getLatAfter() {
        return latAfter;
    }

    public void setLatAfter(double latAfter) {
        this.latAfter = latAfter;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Float get_long() {
        return _long;
    }

    public void set_long(Float _long) {
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
