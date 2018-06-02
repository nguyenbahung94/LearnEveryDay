package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseDetailField implements Parcelable {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class RecipeStages {
        @SerializedName("id")
        private int id;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("sortNo")
        private int sortNo;
        @SerializedName("stageName")
        private String stageName;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("rules")
        private List<Rules> rules;

        public int getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getSortNo() {
            return sortNo;
        }

        public String getStageName() {
            return stageName;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public List<Rules> getRules() {
            return rules;
        }
    }

    public static class Recipe {
        @SerializedName("id")
        private int id;
        @SerializedName("contestFlag")
        private int contestFlag;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("createdFlag")
        private int createdFlag;
        @SerializedName("description")
        private String description;
        @SerializedName("ekUser")
        private EkUser ekUser;
        @SerializedName("imagePath")
        private String imagePath;
        @SerializedName("imageType")
        private int imageType;
        @SerializedName("officialFlag")
        private int officialFlag;
        @SerializedName("organizationName")
        private String organizationName;
        @SerializedName("scope")
        private String scope;
        @SerializedName("changeAvailability")
        private boolean changeAvailability;
        @SerializedName("recipeType")
        private boolean recipeType;
        @SerializedName("crop")
        private String crop;
        @SerializedName("category")
        private String category;
        @SerializedName("parentRecipeId")
        private int parentRecipeId;
        @SerializedName("prefectures")
        private String prefectures;
        @SerializedName("publicContent1")
        private boolean publicContent1;
        @SerializedName("publicContent2")
        private boolean publicContent2;
        @SerializedName("publicContent3")
        private boolean publicContent3;
        @SerializedName("publicContent4")
        private boolean publicContent4;
        @SerializedName("publicFlag")
        private int publicFlag;
        @SerializedName("recipeName")
        private String recipeName;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("recipeStages")
        private List<RecipeStages> recipeStages;
        @SerializedName("recipeVersion")
        private int recipeVersion;

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

        public String getDescription() {
            return description;
        }

        public EkUser getEkUser() {
            return ekUser;
        }

        public String getImagePath() {
            return imagePath;
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

        public String getScope() {
            return scope;
        }

        public boolean isChangeAvailability() {
            return changeAvailability;
        }

        public boolean isRecipeType() {
            return recipeType;
        }

        public String getCrop() {
            return crop;
        }

        public String getCategory() {
            return category;
        }

        public int getParentRecipeId() {
            return parentRecipeId;
        }

        public String getPrefectures() {
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
    }

    public static class Conditions {
    }

    public static class Actions {
    }

    public static class Rules {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("conditions")
        private List<Conditions> conditions;
        @SerializedName("actions")
        private List<Actions> actions;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("ruleType")
        private int ruleType;
        @SerializedName("isNotify")
        private boolean isNotify;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Conditions> getConditions() {
            return conditions;
        }

        public List<Actions> getActions() {
            return actions;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getRuleType() {
            return ruleType;
        }

        public boolean isNotify() {
            return isNotify;
        }
    }

    public static class CurrentStage {
        @SerializedName("id")
        private int id;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("sortNo")
        private int sortNo;
        @SerializedName("stageName")
        private String stageName;
        @SerializedName("stageDescription")
        private String stageDescription;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("rules")
        private List<Rules> rules;
        @SerializedName("status")
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public CurrentStage(int id, int sortNo, String stageName) {
            this.id = id;
            this.sortNo = sortNo;
            this.stageName = stageName;
        }

        public int getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getSortNo() {
            return sortNo;
        }

        public String getStageName() {
            return stageName;
        }

        public String getStageDescription() {
            return stageDescription;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public List<Rules> getRules() {
            return rules;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }

        public void setStageDescription(String stageDescription) {
            this.stageDescription = stageDescription;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void setRules(List<Rules> rules) {
            this.rules = rules;
        }
    }

    public static class Recipes {
        @SerializedName("id")
        public int id;
        @SerializedName("recipe")
        public Recipe recipe;
        @SerializedName("currentStage")
        private CurrentStage currentStage;

        public int getId() {
            return id;
        }

        public Recipe getRecipe() {
            return recipe;
        }

        public CurrentStage getCurrentStage() {
            return currentStage;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }

        public void setCurrentStage(CurrentStage currentStage) {
            this.currentStage = currentStage;
        }
    }

    public static class SensorNodes {
    }

    public static class Polygon implements Parcelable {
        @SerializedName("lng")
        private double lng;
        @SerializedName("lat")
        private double lat;

        public Polygon(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.lng);
            dest.writeDouble(this.lat);
        }

        protected Polygon(Parcel in) {
            this.lng = in.readDouble();
            this.lat = in.readDouble();
        }

        public static final Creator<Polygon> CREATOR = new Creator<Polygon>() {
            @Override
            public Polygon createFromParcel(Parcel source) {
                return new Polygon(source);
            }

            @Override
            public Polygon[] newArray(int size) {
                return new Polygon[size];
            }
        };
    }

    public static class Data implements Parcelable {
        @SerializedName("id")
        public int id;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("ekUser")
        public EkUser ekUser;
        @SerializedName("name")
        public String name;
        @SerializedName("recipes")
        public List<Recipes> recipes;
        @SerializedName("startDate")
        private String startDate;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("sensorNodes")
        private List<SensorNodes> sensorNodes;
        @SerializedName("fieldType")
        private int fieldType;
        @SerializedName("polygon")
        public List<Polygon> polygon;
        @SerializedName("deviceCount")
        private int deviceCount;
        @SerializedName("recipeCount")
        private int recipeCount;
        @SerializedName("memberCount")
        private int memberCount;
        @SerializedName("location")
        private String location;
        @SerializedName("userRoleOnField")
        private int userRoleOnField;

        public int getUserRoleOnField() {
            return userRoleOnField;
        }

        public EnumUserRoleOnField setUpRoleOnField(int number) {
            switch (number) {
                case 0:
                    return EnumUserRoleOnField.Owner;
                case 1:
                    return EnumUserRoleOnField.Administrator;
                case 2:
                    return EnumUserRoleOnField.Worker;
                case 3:
                    return EnumUserRoleOnField.Viewer;
            }
            return null;
        }

        public String getLocation() {
            return location;
        }


        public void setLocation(String location) {
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public EkUser getEkUser() {
            return ekUser;
        }

        public String getName() {
            return name;
        }

        public List<Recipes> getRecipes() {
            return recipes;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public List<SensorNodes> getSensorNodes() {
            return sensorNodes;
        }

        public int getFieldType() {
            return fieldType;
        }

        public List<Polygon> getPolygon() {
            return polygon;
        }

        public int getDeviceCount() {
            return deviceCount;
        }

        public int getRecipeCount() {
            return recipeCount;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public Data() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.createdAt);
            dest.writeParcelable(this.ekUser, flags);
            dest.writeString(this.name);
            dest.writeList(this.recipes);
            dest.writeString(this.startDate);
            dest.writeString(this.updatedAt);
            dest.writeList(this.sensorNodes);
            dest.writeInt(this.fieldType);
            dest.writeTypedList(this.polygon);
            dest.writeInt(this.deviceCount);
            dest.writeInt(this.recipeCount);
            dest.writeInt(this.memberCount);
            dest.writeString(this.location);
            dest.writeInt(this.userRoleOnField);
        }

        protected Data(Parcel in) {
            this.id = in.readInt();
            this.createdAt = in.readString();
            this.ekUser = in.readParcelable(EkUser.class.getClassLoader());
            this.name = in.readString();
            this.recipes = new ArrayList<Recipes>();
            in.readList(this.recipes, Recipes.class.getClassLoader());
            this.startDate = in.readString();
            this.updatedAt = in.readString();
            this.sensorNodes = new ArrayList<SensorNodes>();
            in.readList(this.sensorNodes, SensorNodes.class.getClassLoader());
            this.fieldType = in.readInt();
            this.polygon = in.createTypedArrayList(Polygon.CREATOR);
            this.deviceCount = in.readInt();
            this.recipeCount = in.readInt();
            this.memberCount = in.readInt();
            this.location = in.readString();
            this.userRoleOnField = in.readInt();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeParcelable(this.data, flags);
    }

    public ResponseDetailField() {
    }

    protected ResponseDetailField(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<ResponseDetailField> CREATOR = new Creator<ResponseDetailField>() {
        @Override
        public ResponseDetailField createFromParcel(Parcel source) {
            return new ResponseDetailField(source);
        }

        @Override
        public ResponseDetailField[] newArray(int size) {
            return new ResponseDetailField[size];
        }
    };
}
