package com.ekakashi.greenhouse.database.model_server_request;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/14/2018.
 */

public class AddFieldRequest {

    @SerializedName("recipes")
    private List<Recipes> recipes;
    @SerializedName("name")
    private String name;
    @SerializedName("fieldType")
    private String fieldType;
    @SerializedName("polygon")
    private List<Polygon> polygon;
    @SerializedName("ekUser")
    private EkUser ekUser;
    @SerializedName("location")
    private String location;

    public AddFieldRequest(List<Recipes> recipes, String name, String fieldType, List<Polygon> polygon, EkUser ekUser, String location) {
        this.recipes = recipes;
        this.name = name;
        this.fieldType = fieldType;
        this.polygon = polygon;
        this.ekUser = ekUser;
        this.location = location;
    }

    @Override
    public String toString() {
        return "AddFieldRequest{" +
                "recipes=" + recipes +
                ", name='" + name + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", polygon=" + polygon +
                ", ekUser=" + ekUser +
                '}';
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
        @SerializedName("sortNo")
        private int sortNo;

        public CurrentStage(String id,int sortNo) {
            this.id = id;
            this.sortNo=sortNo;

        }
    }

    public static class Recipes {
        @SerializedName("recipe")
        private Recipe recipe;
        @SerializedName("currentStage")
        private CurrentStage currentStage;

        public Recipes(Recipe recipe, CurrentStage currentStage) {
            this.recipe = recipe;
            this.currentStage = currentStage;
        }
    }

    public static class Polygon {
        @SerializedName("lng")
        private double lng;
        @SerializedName("lat")
        private double lat;

        public Polygon(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }
    }

    public static class EkUser {
        @SerializedName("id")
        private String id;

        public EkUser(String id) {
            this.id = id;
        }
    }
}
