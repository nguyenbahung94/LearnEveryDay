package com.ekakashi.greenhouse.features.timeline.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nquochuy on 4/2/2018.
 */

public class WorkType implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("nameJapan")
    public String nameJapan;
    @SerializedName("ekFieldId")
    public int ekFieldId;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameJapan() {
        return nameJapan;
    }

    public int getEkFieldId() {
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
}
