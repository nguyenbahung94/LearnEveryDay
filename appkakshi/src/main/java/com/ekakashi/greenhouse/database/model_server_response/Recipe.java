package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;



public class Recipe {
    @SerializedName("id")
    public String id;
    @SerializedName("ekUser")
    public String ekUser;
    @SerializedName("parentRecipe")
    public String parentRecipe;
    @SerializedName("recipeName")
    public String recipeName;
    @SerializedName("sector")
    public String sector;
    @SerializedName("sectorIcon")
    public String sectorIcon;
    @SerializedName("prefectures")
    public int prefectures;
    @SerializedName("organizationName")
    public String organizationName;
    @SerializedName("description")
    public String description;
    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("deletedAt")
    public String deletedAt;
    @SerializedName("recipeStages")
    public String recipeStages;
    @SerializedName("recipeKeywords")
    public String recipeKeywords;
    @SerializedName("recipeRequests")
    public String recipeRequests;
    @SerializedName("category")
    public String category;


    public String getId() {
        return id;
    }

    public String getEkUser() {
        return ekUser;
    }

    public String getParentRecipe() {
        return parentRecipe;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getSector() {
        return sector;
    }

    public String getSectorIcon() {
        return sectorIcon;
    }

    public int getPrefectures() {
        return prefectures;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
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

    public String getRecipeStages() {
        return recipeStages;
    }

    public String getRecipeKeywords() {
        return recipeKeywords;
    }

    public String getRecipeRequests() {
        return recipeRequests;
    }

    public String getCategory() {
        return category;
    }
}
