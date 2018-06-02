package com.ekakashi.greenhouse.features.timeline_add_detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nquochuy on 2/6/2018.
 */

public class TimelineAddDetailModel implements Parcelable{
    private boolean isSelectTargetCrop = false;
    private boolean isSelectWorkType = false;
    private boolean isSelectDisease = false;
    private boolean isSelectPesticide = false;
    private boolean isSelectFertilizer = false;
    private boolean isSelectDate = false;

    public TimelineAddDetailModel() {
    }

    public boolean isSelectTargetCrop() {
        return isSelectTargetCrop;
    }

    public void setSelectTargetCrop(boolean selectTargetCrop) {
        isSelectTargetCrop = selectTargetCrop;
    }

    public boolean isSelectWorkType() {
        return isSelectWorkType;
    }

    public void setSelectWorkType(boolean selectWorkType) {
        isSelectWorkType = selectWorkType;
    }

    public boolean isSelectDisease() {
        return isSelectDisease;
    }

    public void setSelectDisease(boolean selectDisease) {
        isSelectDisease = selectDisease;
    }

    public boolean isSelectPesticide() {
        return isSelectPesticide;
    }

    public void setSelectPesticide(boolean selectPesticide) {
        isSelectPesticide = selectPesticide;
    }

    public boolean isSelectFertilizer() {
        return isSelectFertilizer;
    }

    public void setSelectFertilizer(boolean selectFertilizer) {
        isSelectFertilizer = selectFertilizer;
    }

    public boolean isSelectDate() {
        return isSelectDate;
    }

    public void setSelectDate(boolean selectDate) {
        isSelectDate = selectDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelectTargetCrop ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelectWorkType ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelectDisease ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelectPesticide ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelectFertilizer ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelectDate ? (byte) 1 : (byte) 0);
    }

    protected TimelineAddDetailModel(Parcel in) {
        this.isSelectTargetCrop = in.readByte() != 0;
        this.isSelectWorkType = in.readByte() != 0;
        this.isSelectDisease = in.readByte() != 0;
        this.isSelectPesticide = in.readByte() != 0;
        this.isSelectFertilizer = in.readByte() != 0;
        this.isSelectDate = in.readByte() != 0;
    }

    public static final Creator<TimelineAddDetailModel> CREATOR = new Creator<TimelineAddDetailModel>() {
        @Override
        public TimelineAddDetailModel createFromParcel(Parcel source) {
            return new TimelineAddDetailModel(source);
        }

        @Override
        public TimelineAddDetailModel[] newArray(int size) {
            return new TimelineAddDetailModel[size];
        }
    };
}
