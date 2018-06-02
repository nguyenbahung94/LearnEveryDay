package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 3/1/2018.
 */

public class RemoveRecipeForFieldRequest {
    @SerializedName("recipeId")
    public int recipeId;
    @SerializedName("fieldId")
    public int fieldId;
    @SerializedName("currentRecipeStageId")
    public int currentRecipeStageId;

    public RemoveRecipeForFieldRequest(int ekFieldId, int recipeId, int currentRecipeStageId) {
        this.recipeId = recipeId;
        this.fieldId = ekFieldId;
        this.currentRecipeStageId = currentRecipeStageId;
    }

    @Override
    public String toString() {
        return "RemoveRecipeForFieldRequest{" +
                "recipeId=" + recipeId +
                ", fieldId=" + fieldId +
                ", currentRecipeStageId=" + currentRecipeStageId +
                '}';
    }
}
