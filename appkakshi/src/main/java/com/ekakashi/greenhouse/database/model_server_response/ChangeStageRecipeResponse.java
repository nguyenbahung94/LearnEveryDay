package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptloc on 2/28/2018.
 */

public class ChangeStageRecipeResponse {

    @SerializedName("data")
    private Data data;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data {
        @SerializedName("isComplete")
        private boolean isComplete;
        @SerializedName("endAt")
        private String endAt;
        @SerializedName("startAt")
        private String startAt;
        @SerializedName("sortNo")
        private int sortNo;
        @SerializedName("stageName")
        private String stageName;
        @SerializedName("recipeId")
        private int recipeId;
        @SerializedName("ekfieldId")
        private int ekfieldId;
        @SerializedName("stageDescription")
        private String stageDescription;
        @SerializedName("status")
        private int status;


        public void setStatus(int status) {
            this.status = status;
        }

        public boolean getIsComplete() {
            return isComplete;
        }

        public void setIsComplete(boolean isComplete) {
            this.isComplete = isComplete;
        }

        public String getEndAt() {
            return endAt;
        }

        public void setEndAt(String endAt) {
            this.endAt = endAt;
        }

        public String getStartAt() {
            return startAt;
        }

        public void setStartAt(String startAt) {
            this.startAt = startAt;
        }

        public String getStageDescription() {
            return stageDescription;
        }

        public void setStageDescription(String stageDescription) {
            this.stageDescription = stageDescription;
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

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public int getEkfieldId() {
            return ekfieldId;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public int getStatus() {
            return status;
        }

        public void setEkfieldId(int ekfieldId) {
            this.ekfieldId = ekfieldId;
        }
    }
}
