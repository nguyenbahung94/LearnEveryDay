package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/18/2018.
 */

public class ResponseCloneRecipe {

    @SerializedName("id")
    public int id;
    @SerializedName("contestFlag")
    public int contestFlag;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("createdFlag")
    public int createdFlag;
    @SerializedName("ekUser")
    public EkUser ekUser;
    @SerializedName("imageType")
    public int imageType;
    @SerializedName("officialFlag")
    public int officialFlag;
    @SerializedName("organizationName")
    public String organizationName;
    @SerializedName("scope")
    public int scope;
    @SerializedName("changeAvailability")
    public boolean changeAvailability;
    @SerializedName("recipeType")
    public boolean recipeType;
    @SerializedName("category")
    public String category;
    @SerializedName("parentRecipeId")
    public int parentRecipeId;
    @SerializedName("prefectures")
    public int prefectures;
    @SerializedName("publicContent1")
    public boolean publicContent1;
    @SerializedName("publicContent2")
    public boolean publicContent2;
    @SerializedName("publicContent3")
    public boolean publicContent3;
    @SerializedName("publicContent4")
    public boolean publicContent4;
    @SerializedName("publicFlag")
    public int publicFlag;
    @SerializedName("recipeName")
    public String recipeName;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("recipeStages")
    public List<RecipeStages> recipeStages;
    @SerializedName("recipeVersion")
    public int recipeVersion;

    public int getId() {
        return id;
    }

    public int getContestFlag() {
        return contestFlag;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getCreatedFlag() {
        return createdFlag;
    }

    public EkUser getEkUser() {
        return ekUser;
    }

    public int getImageType() {
        return imageType;
    }

    public int getOfficialFlag() {
        return officialFlag;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public int getScope() {
        return scope;
    }

    public boolean isChangeAvailability() {
        return changeAvailability;
    }

    public boolean isRecipeType() {
        return recipeType;
    }

    public String getCategory() {
        return category;
    }

    public int getParentRecipeId() {
        return parentRecipeId;
    }

    public int getPrefectures() {
        return prefectures;
    }

    public boolean isPublicContent1() {
        return publicContent1;
    }

    public boolean isPublicContent2() {
        return publicContent2;
    }

    public boolean isPublicContent3() {
        return publicContent3;
    }

    public boolean isPublicContent4() {
        return publicContent4;
    }

    public int getPublicFlag() {
        return publicFlag;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<RecipeStages> getRecipeStages() {
        return recipeStages;
    }

    public int getRecipeVersion() {
        return recipeVersion;
    }

    public static class RecipeStages {
    }
}
