package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/2/2018.
 */

public class CurrentRecipeStage {
    @SerializedName("id")
    public int id;
    @SerializedName("recipe")
    public String recipe;
    @SerializedName("stageName")
    public String stageName;
    @SerializedName("description")
    public String description;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;
    @SerializedName("ekFieldSensorPositions")
    public String ekFieldSensorPositions;
    @SerializedName("ekFieldSensorMeasureItems")
    public String ekFieldSensorMeasureItems;

    public int getId() {
        return id;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getStageName() {
        return stageName;
    }

    public String getDescription() {
        return description;
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

    public String getEkFieldSensorPositions() {
        return ekFieldSensorPositions;
    }

    public String getEkFieldSensorMeasureItems() {
        return ekFieldSensorMeasureItems;
    }
}
