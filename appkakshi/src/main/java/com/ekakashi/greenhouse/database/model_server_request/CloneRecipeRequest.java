package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 1/14/2018.
 */

public class CloneRecipeRequest {
    @SerializedName("recipeId")
    public int recipeId;
    @SerializedName("userId")
    public int userId;

    public CloneRecipeRequest(int recipeId, int userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CloneRecipeRequest{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                '}';
    }
}
