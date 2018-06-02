package com.ekakashi.greenhouse.database.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.common.Utils;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Utils.Device.TABLE_NAME_LIST_DEVICE, daoClass = DeviceObjectDao.class)
public class DeviceObject implements Parcelable {

    @DatabaseField(id = true,columnName = Utils.Device.ID_DEVICE)
    @SerializedName("id")
    public String id;

    @SerializedName("abnormal")
    public boolean abnormal;
    @SerializedName("activationStatus")
    public int activationStatus;
    @SerializedName("activationStatusUpdateDatetime")
    public String activationStatusUpdate;
    @SerializedName("batteryVoltage")
    public int batteryVoltage;
    @SerializedName("controlStatus")
    public int controlStatus;
    @SerializedName("controlStatusUdpateDatetime")
    public String controlStatusUdpate;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("deletedAt")
    public String deletedAt;
    @SerializedName("ekfieldId")
    public int ekFieldId;
    @SerializedName("entryStatus")
    public int entryStatus;
    @SerializedName("entryStatusUpdateDatetime")
    public String entryStatusUpdate;
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
    @SerializedName("installationPlace")
    public String installationPlace;
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
    public String measureDataLast;
    @SerializedName("measureInterval")
    public int measureInterval;
    @SerializedName("measureStartTime")
    public String measureStartTime;
    @SerializedName("nodeTimezone")
    public String nodeTimezone;
    @SerializedName("openDegree")
    public int openDegree;
    @SerializedName("receivingStatus")
    public int receivingStatus;
    @SerializedName("receivingStatusUpdateDatetime")
    public String receivingStatusUpdate;

    @DatabaseField(columnName = Utils.Device.DATE_REGISTER)
    @SerializedName("registrationDate")
    public String registrationDate;

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

    @SerializedName("snIconPath")
    public String snIconPath;

    @DatabaseField(columnName = Utils.Device.NAME_DEVICE)
    @SerializedName("snName")
    public String snName;

    @SerializedName("sunrise")
    public String sunrise;
    @SerializedName("sunset")
    public String sunset;

    @DatabaseField(columnName = Utils.Device.AUTOMATIC)
    @SerializedName("systemAutoControl")
    public boolean systemAutoControl;

    @SerializedName("updatedAt")
    public String updatedAt;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.abnormal ? (byte) 1 : (byte) 0);
        dest.writeInt(this.activationStatus);
        dest.writeString(this.activationStatusUpdate);
        dest.writeInt(this.batteryVoltage);
        dest.writeInt(this.controlStatus);
        dest.writeString(this.controlStatusUdpate);
        dest.writeString(this.createdAt);
        dest.writeString(this.deletedAt);
        dest.writeInt(this.ekFieldId);
        dest.writeInt(this.entryStatus);
        dest.writeString(this.entryStatusUpdate);
        dest.writeInt(this.geodeticUseStatus);
        dest.writeString(this.gpsInformation);
        dest.writeString(this.gwCustomerName);
        dest.writeString(this.gwDeviceTypeId);
        dest.writeString(this.gwName);
        dest.writeString(this.iccId);
        dest.writeString(this.installationPlace);
        dest.writeString(this.ipv4Address);
        dest.writeString(this.item1);
        dest.writeString(this.item2);
        dest.writeString(this.item3);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.latitudeM2m);
        dest.writeDouble(this.latitudeUser);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.longitudeM2m);
        dest.writeDouble(this.longitudeUser);
        dest.writeString(this.measureDataLast);
        dest.writeInt(this.measureInterval);
        dest.writeString(this.measureStartTime);
        dest.writeString(this.nodeTimezone);
        dest.writeInt(this.openDegree);
        dest.writeInt(this.receivingStatus);
        dest.writeString(this.receivingStatusUpdate);
        dest.writeString(this.registrationDate);
        dest.writeInt(this.rssi1);
        dest.writeInt(this.rssi2);
        dest.writeString(this.snCustomerName);
        dest.writeString(this.snDeviceTypeId);
        dest.writeString(this.snDeviceTypeName);
        dest.writeString(this.snIconPath);
        dest.writeString(this.snName);
        dest.writeString(this.sunrise);
        dest.writeString(this.sunset);
        dest.writeByte(this.systemAutoControl ? (byte) 1 : (byte) 0);
        dest.writeString(this.updatedAt);
    }

    public DeviceObject() {
    }

    protected DeviceObject(Parcel in) {
        this.abnormal = in.readByte() != 0;
        this.activationStatus = in.readInt();
        this.activationStatusUpdate = in.readString();
        this.batteryVoltage = in.readInt();
        this.controlStatus = in.readInt();
        this.controlStatusUdpate = in.readString();
        this.createdAt = in.readString();
        this.deletedAt = in.readString();
        this.ekFieldId = in.readInt();
        this.entryStatus = in.readInt();
        this.entryStatusUpdate = in.readString();
        this.geodeticUseStatus = in.readInt();
        this.gpsInformation = in.readString();
        this.gwCustomerName = in.readString();
        this.gwDeviceTypeId = in.readString();
        this.gwName = in.readString();
        this.iccId = in.readString();
        this.installationPlace = in.readString();
        this.ipv4Address = in.readString();
        this.item1 = in.readString();
        this.item2 = in.readString();
        this.item3 = in.readString();
        this.latitude = in.readDouble();
        this.latitudeM2m = in.readDouble();
        this.latitudeUser = in.readDouble();
        this.longitude = in.readDouble();
        this.longitudeM2m = in.readDouble();
        this.longitudeUser = in.readDouble();
        this.measureDataLast = in.readString();
        this.measureInterval = in.readInt();
        this.measureStartTime = in.readString();
        this.nodeTimezone = in.readString();
        this.openDegree = in.readInt();
        this.receivingStatus = in.readInt();
        this.receivingStatusUpdate = in.readString();
        this.registrationDate = in.readString();
        this.rssi1 = in.readInt();
        this.rssi2 = in.readInt();
        this.snCustomerName = in.readString();
        this.snDeviceTypeId = in.readString();
        this.snDeviceTypeName = in.readString();
        this.snIconPath = in.readString();
        this.snName = in.readString();
        this.sunrise = in.readString();
        this.sunset = in.readString();
        this.systemAutoControl = in.readByte() != 0;
        this.updatedAt = in.readString();
    }

    public static final Creator<DeviceObject> CREATOR = new Creator<DeviceObject>() {
        @Override
        public DeviceObject createFromParcel(Parcel source) {
            return new DeviceObject(source);
        }

        @Override
        public DeviceObject[] newArray(int size) {
            return new DeviceObject[size];
        }
    };
}
