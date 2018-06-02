package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/11/2018.
 */

public class Place {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("type")
    public int type;
    @SerializedName("polygon")
    public String polygon;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;
    @SerializedName("place")
    public List<Place> listPolygon;

    public List<Place> getListPolygon() {
        return listPolygon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getPolygon() {
        return polygon;
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


    /////

}
