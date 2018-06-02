package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 3/14/2018.
 */

public class FertilizerResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ArrayList<Fertilizer> fertilizers;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Fertilizer> getFertilizers() {
        return fertilizers;
    }

    public static class Fertilizer implements Parcelable {
        @SerializedName("id")
        public int id;
        @SerializedName("description")
        public String description;
        @SerializedName("nitrogen")
        public int nitrogen;
        @SerializedName("phosphorus")
        public int phosphorus;
        @SerializedName("potassium")
        public int potassium;

        private boolean isSelected = false;

        public Fertilizer(String description, boolean isSelected) {
            this.description = description;
            this.isSelected = isSelected;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public int getNitrogen() {
            return nitrogen;
        }

        public int getPhosphorus() {
            return phosphorus;
        }

        public int getPotassium() {
            return potassium;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.description);
            dest.writeInt(this.nitrogen);
            dest.writeInt(this.phosphorus);
            dest.writeInt(this.potassium);
            dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        }

        protected Fertilizer(Parcel in) {
            this.id = in.readInt();
            this.description = in.readString();
            this.nitrogen = in.readInt();
            this.phosphorus = in.readInt();
            this.potassium = in.readInt();
            this.isSelected = in.readByte() != 0;
        }

        public static final Creator<Fertilizer> CREATOR = new Creator<Fertilizer>() {
            @Override
            public Fertilizer createFromParcel(Parcel source) {
                return new Fertilizer(source);
            }

            @Override
            public Fertilizer[] newArray(int size) {
                return new Fertilizer[size];
            }
        };
    }
}
