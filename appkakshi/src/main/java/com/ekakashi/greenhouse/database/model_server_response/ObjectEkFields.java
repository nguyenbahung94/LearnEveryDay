package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 1/15/2018.
 */

public class ObjectEkFields implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("ekfield")
    private ObjectEkField ekField;
    @SerializedName("currentStage")
    private ObjectGrowth currentStage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjectEkField getEkField() {
        return ekField;
    }

    public void setEkField(ObjectEkField ekField) {
        this.ekField = ekField;
    }

    public ObjectGrowth getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(ObjectGrowth currentStage) {
        this.currentStage = currentStage;
    }

    public static Creator<ObjectEkFields> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.ekField, flags);
        dest.writeParcelable(this.currentStage, flags);
    }

    public ObjectEkFields() {
    }

    protected ObjectEkFields(Parcel in) {
        this.id = in.readInt();
        this.ekField = in.readParcelable(ObjectEkField.class.getClassLoader());
        this.currentStage = in.readParcelable(ObjectGrowth.class.getClassLoader());
    }

    public static final Parcelable.Creator<ObjectEkFields> CREATOR = new Parcelable.Creator<ObjectEkFields>() {
        @Override
        public ObjectEkFields createFromParcel(Parcel source) {
            return new ObjectEkFields(source);
        }

        @Override
        public ObjectEkFields[] newArray(int size) {
            return new ObjectEkFields[size];
        }
    };
}
