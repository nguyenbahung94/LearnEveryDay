package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 3/1/2018.
 */

public class AddRecipeForFieldRequest {

    @SerializedName("currentStage")
    public CurrentStage currentStage;
    @SerializedName("recipe")
    public Recipe recipe;
    @SerializedName("ekfield")
    public Ekfield ekfield;

    public AddRecipeForFieldRequest() {
    }

    public AddRecipeForFieldRequest(Ekfield ekfield, Recipe recipe, CurrentStage currentStage) {
        this.currentStage = currentStage;
        this.recipe = recipe;
        this.ekfield = ekfield;
    }

    public static class CurrentStage {
        @SerializedName("id")
        public int id;

        public CurrentStage(int currentStageId) {
            this.id = currentStageId;
        }
    }

    public static class Recipe {
        @SerializedName("id")
        public int id;

        public Recipe(int recipeId) {
            this.id = recipeId;
        }
    }

    public static class Ekfield {
        @SerializedName("id")
        public int id;

        public Ekfield(int ekFieldId) {
            this.id = ekFieldId;
        }
    }
}
