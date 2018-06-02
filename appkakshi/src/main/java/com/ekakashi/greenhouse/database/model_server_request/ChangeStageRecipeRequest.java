package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 2/28/2018.
 */

public class ChangeStageRecipeRequest {
    @SerializedName("ekfieldId")
    public int ekFieldId;
    @SerializedName("recipeId")
    public int recipeId;
    @SerializedName("growingStageId")
    public int growingStageId;

    public ChangeStageRecipeRequest(int ekFieldId, int recipeId, int growingStageId) {
        this.ekFieldId = ekFieldId;
        this.recipeId = recipeId;
        this.growingStageId = growingStageId;
    }

    @Override
    public String toString() {
        return "ChangeStageRecipeRequest{" +
                "ekFieldId='" + ekFieldId + '\'' +
                ", recipeId='" + recipeId + '\'' +
                ", growingStageId='" + growingStageId + '\'' +
                '}';
    }
}
