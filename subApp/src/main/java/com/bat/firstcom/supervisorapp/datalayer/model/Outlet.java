package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 27-Jul-17.
 */

public class Outlet {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("OutletLocationId")
    @Expose
    private int outletLocationId;
    @SerializedName("SupId")
    @Expose
    private Object supId;
    @SerializedName("RequestType")
    @Expose
    private String requestType;
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
    @SerializedName("BrandId")
    @Expose
    private int brandId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("OwnerName")
    @Expose
    private String ownerName;
    @SerializedName("OwnerPhone")
    @Expose
    private String ownerPhone;
    @SerializedName("VistorQuantity")
    @Expose
    private int vistorQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutletLocationId() {
        return outletLocationId;
    }

    public void setOutletLocationId(int outletLocationId) {
        this.outletLocationId = outletLocationId;
    }

    public Object getSupId() {
        return supId;
    }

    public void setSupId(Object supId) {
        this.supId = supId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
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

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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

    public int getVistorQuantity() {
        return vistorQuantity;
    }

    public void setVistorQuantity(int vistorQuantity) {
        this.vistorQuantity = vistorQuantity;
    }
}
