package com.ekakashi.greenhouse.database.model_server_response.template_recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 3/20/2018.
 */

public class TemplateRecipe implements Parcelable {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Data> data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
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

    public TemplateRecipe() {
    }

    protected TemplateRecipe(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<TemplateRecipe> CREATOR = new Creator<TemplateRecipe>() {
        @Override
        public TemplateRecipe createFromParcel(Parcel source) {
            return new TemplateRecipe(source);
        }

        @Override
        public TemplateRecipe[] newArray(int size) {
            return new TemplateRecipe[size];
        }
    };
}
