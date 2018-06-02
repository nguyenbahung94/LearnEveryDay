package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/2/2018.
 */

public class ResponseGetFields {
    @SerializedName("data")
    public List<Data> data;
    @SerializedName("paging")
    public Paging paging;

    public List<Data> getData() {
        return data;
    }

    public Paging getPaging() {
        return paging;
    }

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("ekUser")
        public EkUser ekUser;
        @SerializedName("name")
        public String name;
        @SerializedName("location")
        public String location;
        @SerializedName("sentStartMail")
        public boolean sentStartMail;
        @SerializedName("fieldType")
        public int fieldType;
        @SerializedName("recipeCount")
        public int recipeCount;

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

        public String getLocation() {
            return location;
        }

        public boolean isSentStartMail() {
            return sentStartMail;
        }

        public int getFieldType() {
            return fieldType;
        }

        public int getRecipeCount() {
            return recipeCount;
        }
    }

    public static class Paging {
        @SerializedName("currentPage")
        public int currentPage;
        @SerializedName("numOfPage")
        public int numOfPage;
        @SerializedName("recordPerPage")
        public int recordPerPage;
        @SerializedName("itemsLoaded")
        public int itemsLoaded;

        public int getCurrentPage() {
            return currentPage;
        }

        public int getNumOfPage() {
            return numOfPage;
        }

        public int getRecordPerPage() {
            return recordPerPage;
        }

        public int getItemsLoaded() {
            return itemsLoaded;
        }
    }
}
