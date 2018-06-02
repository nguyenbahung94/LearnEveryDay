package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/19/2018.
 */

public class CloneRecipeResponse {

    @SerializedName("id")
    public int id;
    @SerializedName("contestFlag")
    public int contestFlag;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("createdFlag")
    public int createdFlag;
    @SerializedName("description")
    public String description;
    @SerializedName("ekUser")
    public EkUser ekUser;
    @SerializedName("imagePath")
    public String imagePath;
    @SerializedName("imageType")
    public int imageType;
    @SerializedName("officialFlag")
    public int officialFlag;
    @SerializedName("organizationName")
    public String organizationName;
    @SerializedName("scope")
    public String scope;
    @SerializedName("changeAvailability")
    public boolean changeAvailability;
    @SerializedName("recipeType")
    public boolean recipeType;
    @SerializedName("crop")
    public String crop;
    @SerializedName("category")
    public String category;
    @SerializedName("parentRecipeId")
    public int parentRecipeId;
    @SerializedName("prefectures")
    public String prefectures;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getContestFlag() {
        return contestFlag;
    }

    public void setContestFlag(int contestFlag) {
        this.contestFlag = contestFlag;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedFlag() {
        return createdFlag;
    }

    public void setCreatedFlag(int createdFlag) {
        this.createdFlag = createdFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EkUser getEkUser() {
        return ekUser;
    }

    public void setEkUser(EkUser ekUser) {
        this.ekUser = ekUser;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getOfficialFlag() {
        return officialFlag;
    }

    public void setOfficialFlag(int officialFlag) {
        this.officialFlag = officialFlag;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isChangeAvailability() {
        return changeAvailability;
    }

    public void setChangeAvailability(boolean changeAvailability) {
        this.changeAvailability = changeAvailability;
    }

    public boolean isRecipeType() {
        return recipeType;
    }

    public void setRecipeType(boolean recipeType) {
        this.recipeType = recipeType;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getParentRecipeId() {
        return parentRecipeId;
    }

    public void setParentRecipeId(int parentRecipeId) {
        this.parentRecipeId = parentRecipeId;
    }

    public String getPrefectures() {
        return prefectures;
    }

    public void setPrefectures(String prefectures) {
        this.prefectures = prefectures;
    }

    public boolean isPublicContent1() {
        return publicContent1;
    }

    public void setPublicContent1(boolean publicContent1) {
        this.publicContent1 = publicContent1;
    }

    public boolean isPublicContent2() {
        return publicContent2;
    }

    public void setPublicContent2(boolean publicContent2) {
        this.publicContent2 = publicContent2;
    }

    public boolean isPublicContent3() {
        return publicContent3;
    }

    public void setPublicContent3(boolean publicContent3) {
        this.publicContent3 = publicContent3;
    }

    public boolean isPublicContent4() {
        return publicContent4;
    }

    public void setPublicContent4(boolean publicContent4) {
        this.publicContent4 = publicContent4;
    }

    public int getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(int publicFlag) {
        this.publicFlag = publicFlag;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<RecipeStages> getRecipeStages() {
        return recipeStages;
    }

    public void setRecipeStages(List<RecipeStages> recipeStages) {
        this.recipeStages = recipeStages;
    }

    public int getRecipeVersion() {
        return recipeVersion;
    }

    public void setRecipeVersion(int recipeVersion) {
        this.recipeVersion = recipeVersion;
    }


    public static class Rules {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("ruleType")
        private int ruleType;
        @SerializedName("conditions")
        private List<ObjectCondition> conditions;
        @SerializedName("actions")
        private List<ObjectAction> actions;
        @SerializedName("isNotify")
        private boolean isNotify;
    }

    public static class RecipeStages {
        @SerializedName("id")
        public int id;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("sortNo")
        public int sortNo;
        @SerializedName("stageName")
        public String stageName;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("rules")
        public List<Rules> rules;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Rules> getRules() {
            return rules;
        }

        public void setRules(List<Rules> rules) {
            this.rules = rules;
        }
    }
}
