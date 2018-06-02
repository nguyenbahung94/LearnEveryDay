package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tung phan on 7/27/17.
 */

public class PostChangeOutletRequest {

    @SerializedName("OwnerName")
    @Expose
    public String ownerName;
    @SerializedName("OwnerPhone")
    @Expose
    public String ownerPhone;
    @SerializedName("VisitorQuantity")
    @Expose
    public Integer visitorQuantity;
    @SerializedName("OutletLocationId")
    @Expose
    private int outletLocationId;
    @SerializedName("RequestType")
    @Expose
    private int requestType;
    @SerializedName("ReasonId")
    @Expose
    private int reasonId;
    @SerializedName("FromHour")
    @Expose
    private int fromHour;
    @SerializedName("FromMinute")
    @Expose
    private int fromMinute;
    @SerializedName("ToHour")
    @Expose
    private int toHour;
    @SerializedName("ToMinute")
    @Expose
    private int toMinute;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Integer getVisitorQuantity() {
        return visitorQuantity;
    }

    public void setVisitorQuantity(Integer visitorQuantity) {
        this.visitorQuantity = visitorQuantity;
    }

    public int getOutletLocationId() {
        return outletLocationId;
    }

    public void setOutletLocationId(int outletLocationId) {
        this.outletLocationId = outletLocationId;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(int fromMinute) {
        this.fromMinute = fromMinute;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getToMinute() {
        return toMinute;
    }

    public void setToMinute(int toMinute) {
        this.toMinute = toMinute;
    }
}
