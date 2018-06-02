package com.ekakashi.greenhouse.database.model_server_response.template_recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.features.weather.object_weather.EnumWeather;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 3/20/2018.
 */

public class Data implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("graph")
    private Graph graph;
    @SerializedName("widget")
    private Widget widget;
    @SerializedName("iconUrl")
    private String iconUrl;
    @SerializedName("templateType")
    private int templateType;
    @SerializedName("tags")
    private String tags;
    @SerializedName("name")
    private String name;
    @SerializedName("sortNo")
    private int sortNo;
    @SerializedName("numberOfColumn")
    private int numberOfColumn;
    @SerializedName("isDefault")
    private boolean isDefault;
    @SerializedName("recipeId")
    private int recipeId;
    private EnumWeather typeOfWeather;

    public EnumWeather getTypeOfWeather() {
        return typeOfWeather;
    }

    public void setTypeOfWeather(EnumWeather typeOfWeather) {
        this.typeOfWeather = typeOfWeather;
    }

    public int getId() {
        return id;
    }

    public Graph getGraph() {
        return graph;
    }

    public Widget getWidget() {
        return widget;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public int getTemplateType() {
        return templateType;
    }

    public String getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public int getSortNo() {
        return sortNo;
    }

    public int getNumberOfColumn() {
        return numberOfColumn;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", graph=" + graph +
                ", widget=" + widget +
                ", iconUrl='" + iconUrl + '\'' +
                ", templateType=" + templateType +
                ", tags=" + tags +
                ", name='" + name + '\'' +
                ", sortNo=" + sortNo +
                ", numberOfColumn=" + numberOfColumn +
                ", isDefault=" + isDefault +
                ", recipeId=" + recipeId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.graph, flags);
        dest.writeParcelable(this.widget, flags);
        dest.writeString(this.iconUrl);
        dest.writeInt(this.templateType);
        dest.writeString(this.tags);
        dest.writeString(this.name);
        dest.writeInt(this.sortNo);
        dest.writeInt(this.numberOfColumn);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
        dest.writeInt(this.recipeId);
        dest.writeInt(this.typeOfWeather == null ? -1 : this.typeOfWeather.ordinal());
    }

    protected Data(Parcel in) {
        this.id = in.readInt();
        this.graph = in.readParcelable(Graph.class.getClassLoader());
        this.widget = in.readParcelable(Widget.class.getClassLoader());
        this.iconUrl = in.readString();
        this.templateType = in.readInt();
        this.tags = in.readString();
        this.name = in.readString();
        this.sortNo = in.readInt();
        this.numberOfColumn = in.readInt();
        this.isDefault = in.readByte() != 0;
        this.recipeId = in.readInt();
        int tmpTypeOfWeather = in.readInt();
        this.typeOfWeather = tmpTypeOfWeather == -1 ? null : EnumWeather.values()[tmpTypeOfWeather];
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
