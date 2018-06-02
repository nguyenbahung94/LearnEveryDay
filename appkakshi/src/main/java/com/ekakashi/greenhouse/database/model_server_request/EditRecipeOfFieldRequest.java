package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/16/2018.
 */

public class EditRecipeOfFieldRequest {

    @SerializedName("ekfield")
    private Ekfield ekfield;
    @SerializedName("recipe")
    private Recipe recipe;
    @SerializedName("currentStage")
    private CurrentStage currentStage;

    public EditRecipeOfFieldRequest(Ekfield ekfield, Recipe recipe, CurrentStage currentStage) {
        this.ekfield = ekfield;
        this.recipe = recipe;
        this.currentStage = currentStage;
    }

    public static class Ekfield {
        @SerializedName("id")
        private String id;

        public Ekfield(String id) {
            this.id = id;
        }
    }

    public static class Recipe {
        @SerializedName("id")
        private String id;

        public Recipe(String id) {
            this.id = id;
        }
    }

    public static class CurrentStage {
        @SerializedName("id")
        private String id;

        public CurrentStage(String id) {
            this.id = id;
        }
    }
}
