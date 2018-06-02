package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 3/30/2018.
 */

public class WorkTypeResponse implements Parcelable {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ArrayList<WorkType> data;

    public static class WorkType implements Parcelable{
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("nameJapan")
        public String nameJapan;
        @SerializedName("ekFieldId")
        public String ekFieldId;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("deletedAt")
        public String deletedAt;

        private boolean isSelected = false;

        public WorkType(String name, boolean isSelected) {
            this.name = name;
            this.isSelected = isSelected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNameJapan() {
            return nameJapan;
        }


        public String getEkFieldId() {
            return ekFieldId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.nameJapan);
            dest.writeString(this.ekFieldId);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
            dest.writeString(this.deletedAt);
            dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        }

        protected WorkType(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.nameJapan = in.readString();
            this.ekFieldId = in.readString();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
            this.deletedAt = in.readString();
            this.isSelected = in.readByte() != 0;
        }

        public static final Creator<WorkType> CREATOR = new Creator<WorkType>() {
            @Override
            public WorkType createFromParcel(Parcel source) {
                return new WorkType(source);
            }

            @Override
            public WorkType[] newArray(int size) {
                return new WorkType[size];
            }
        };
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<WorkType> getData() {
        return data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    public WorkTypeResponse() {
    }

    protected WorkTypeResponse(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(WorkType.CREATOR);
    }

    public static final Creator<WorkTypeResponse> CREATOR = new Creator<WorkTypeResponse>() {
        @Override
        public WorkTypeResponse createFromParcel(Parcel source) {
            return new WorkTypeResponse(source);
        }

        @Override
        public WorkTypeResponse[] newArray(int size) {
            return new WorkTypeResponse[size];
        }
    };
}
