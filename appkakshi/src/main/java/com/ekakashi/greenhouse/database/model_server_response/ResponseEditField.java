package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nbhung on 1/16/2018.
 */

public class ResponseEditField {

    @SerializedName("data")
    public Data data;

    public Data getData() {
        return data;
    }

    public static class EkUser {
        @SerializedName("id")
        public int id;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("email")
        public String email;
        @SerializedName("isActive")
        public boolean isActive;
        @SerializedName("name")
        public String name;
        @SerializedName("nickName")
        public String nickName;
        @SerializedName("officialUserFlag")
        public int officialUserFlag;
        @SerializedName("password")
        public String password;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("userName")
        public String userName;
        @SerializedName("validEmail")
        public boolean validEmail;

        public int getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getEmail() {
            return email;
        }

        public boolean isActive() {
            return isActive;
        }

        public String getName() {
            return name;
        }

        public String getNickName() {
            return nickName;
        }

        public int getOfficialUserFlag() {
            return officialUserFlag;
        }

        public String getPassword() {
            return password;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getUserName() {
            return userName;
        }

        public boolean isValidEmail() {
            return validEmail;
        }
    }

    public static class Polygon {
        @SerializedName("lng")
        public double lng;
        @SerializedName("lat")
        public double lat;

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }
    }

    public static class Data {
        @SerializedName("id")
        public int id;
        @SerializedName("contestFlag")
        public int contestFlag;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("ekUser")
        public EkUser ekUser;
        @SerializedName("name")
        public String name;
        @SerializedName("sentStartMail")
        public boolean sentStartMail;
        @SerializedName("startDate")
        public String startDate;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("fieldType")
        public int fieldType;
        @SerializedName("polygon")
        public List<Polygon> polygon;
        @SerializedName("recipeCount")
        public int recipeCount;

        public int getId() {
            return id;
        }

        public int getContestFlag() {
            return contestFlag;
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

        public boolean isSentStartMail() {
            return sentStartMail;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getFieldType() {
            return fieldType;
        }

        public List<Polygon> getPolygon() {
            return polygon;
        }

        public int getRecipeCount() {
            return recipeCount;
        }
    }
}
