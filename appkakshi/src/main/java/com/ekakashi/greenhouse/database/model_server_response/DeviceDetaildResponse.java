package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/30/2018.
 */

public class DeviceDetaildResponse {

    @SerializedName("activationStatus")
    public int activationStatus;
    @SerializedName("activationStatusUpdateDatetime")
    public String activationStatusUpdateDatetime;
    @SerializedName("batteryVoltage")
    public int batteryVoltage;
    @SerializedName("controlStatus")
    public int controlStatus;
    @SerializedName("controlStatusUdpateDatetime")
    public String controlStatusUdpateDatetime;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("entryStatus")
    public int entryStatus;
    @SerializedName("entryStatusUpdateDatetime")
    public String entryStatusUpdateDatetime;
    @SerializedName("geodeticUseStatus")
    public int geodeticUseStatus;
    @SerializedName("gpsInformation")
    public String gpsInformation;
    @SerializedName("gwCustomerName")
    public String gwCustomerName;
    @SerializedName("gwDeviceTypeId")
    public String gwDeviceTypeId;
    @SerializedName("gwName")
    public String gwName;
    @SerializedName("iccId")
    public String iccId;
    @SerializedName("id")
    public String id;
    @SerializedName("ipv4Address")
    public String ipv4Address;
    @SerializedName("item1")
    public String item1;
    @SerializedName("item2")
    public String item2;
    @SerializedName("item3")
    public String item3;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("latitudeM2m")
    public double latitudeM2m;
    @SerializedName("latitudeUser")
    public double latitudeUser;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("longitudeM2m")
    public double longitudeM2m;
    @SerializedName("longitudeUser")
    public double longitudeUser;
    @SerializedName("measureDataLastDatetime")
    public String measureDataLastDatetime;
    @SerializedName("measureInterval")
    public int measureInterval;
    @SerializedName("measureStartTime")
    public String measureStartTime;
    @SerializedName("nodeTimezone")
    public String nodeTimezone;
    @SerializedName("receivingStatus")
    public int receivingStatus;
    @SerializedName("receivingStatusUpdateDatetime")
    public String receivingStatusUpdateDatetime;
    @SerializedName("rssi1")
    public int rssi1;
    @SerializedName("rssi2")
    public int rssi2;
    @SerializedName("snCustomerName")
    public String snCustomerName;
    @SerializedName("snDeviceTypeId")
    public String snDeviceTypeId;
    @SerializedName("snDeviceTypeName")
    public String snDeviceTypeName;
    @SerializedName("snName")
    public String snName;
    @SerializedName("sunrise")
    public String sunrise;
    @SerializedName("sunset")
    public String sunset;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("systemAutoControl")
    public boolean systemAutoControl;
    @SerializedName("openDegree")
    public int openDegree;
    @SerializedName("installationPlace")
    public String installationPlace;
    @SerializedName("registrationDate")
    public String registrationDate;
    @SerializedName("snIconPath")
    public String snIconPath;
    @SerializedName("abnormal")
    public boolean abnormal;

    public int getActivationStatus() {
        return activationStatus;
    }

    public String getActivationStatusUpdateDatetime() {
        return activationStatusUpdateDatetime;
    }

    public int getBatteryVoltage() {
        return batteryVoltage;
    }

    public int getControlStatus() {
        return controlStatus;
    }

    public String getControlStatusUdpateDatetime() {
        return controlStatusUdpateDatetime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getEntryStatus() {
        return entryStatus;
    }

    public String getEntryStatusUpdateDatetime() {
        return entryStatusUpdateDatetime;
    }

    public int getGeodeticUseStatus() {
        return geodeticUseStatus;
    }

    public String getGpsInformation() {
        return gpsInformation;
    }

    public String getGwCustomerName() {
        return gwCustomerName;
    }

    public String getGwDeviceTypeId() {
        return gwDeviceTypeId;
    }

    public String getGwName() {
        return gwName;
    }

    public String getIccId() {
        return iccId;
    }

    public String getId() {
        return id;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }

    public String getItem3() {
        return item3;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLatitudeM2m() {
        return latitudeM2m;
    }

    public double getLatitudeUser() {
        return latitudeUser;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLongitudeM2m() {
        return longitudeM2m;
    }

    public double getLongitudeUser() {
        return longitudeUser;
    }

    public String getMeasureDataLastDatetime() {
        return measureDataLastDatetime;
    }

    public int getMeasureInterval() {
        return measureInterval;
    }

    public String getMeasureStartTime() {
        return measureStartTime;
    }

    public String getNodeTimezone() {
        return nodeTimezone;
    }

    public int getReceivingStatus() {
        return receivingStatus;
    }

    public String getReceivingStatusUpdateDatetime() {
        return receivingStatusUpdateDatetime;
    }

    public int getRssi1() {
        return rssi1;
    }

    public int getRssi2() {
        return rssi2;
    }

    public String getSnCustomerName() {
        return snCustomerName;
    }

    public String getSnDeviceTypeId() {
        return snDeviceTypeId;
    }

    public String getSnDeviceTypeName() {
        return snDeviceTypeName;
    }

    public String getSnName() {
        return snName;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isSystemAutoControl() {
        return systemAutoControl;
    }

    public int getOpenDegree() {
        return openDegree;
    }

    public String getInstallationPlace() {
        return installationPlace;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getSnIconPath() {
        return snIconPath;
    }

    public boolean isAbnormal() {
        return abnormal;
    }
}
