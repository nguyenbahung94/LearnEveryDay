package com.ekakashi.greenhouse.features.timeline.models;


import com.ekakashi.greenhouse.features.advice.AdviceDescriptionObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class TimelineResponse implements Serializable {

    @SerializedName("recordNum")
    public int recordNum;
    @SerializedName("listTimelinerespones")
    public ArrayList<ListTimeline> listTimeline;

    public int getRecordNum() {
        return recordNum;
    }

    public ArrayList<ListTimeline> getListTimeline() {
        return listTimeline;
    }

    public static class Diary implements Serializable {
        @SerializedName("diaryId")
        public String diaryId;
        @SerializedName("userId")
        public int userId;
        @SerializedName("userName")
        public String userName;
        @SerializedName("nickName")
        public String nickName;
        @SerializedName("userImage")
        public String userImageUrl;
        @SerializedName("diaryImage")
        public ArrayList<String> diaryImageUrl;
        @SerializedName("diseaseType")
        public String diseaseType;
        @SerializedName("pesticideType")
        public String pesticideType;
        @SerializedName("fertilizierId")
        public String fertilizerId;
        @SerializedName("workType")
        public WorkType workType;
        @SerializedName("crop")
        public String crop;
        @SerializedName("fertilizerType")
        public String fertilizerType;
        @SerializedName("nitrogen")
        public int nitrogen;
        @SerializedName("phosphoric")
        public int phosphoric;
        @SerializedName("potossium")
        public int potossium;
        @SerializedName("issuedDate")
        public String issuedDate;

        public String getDiaryId() {
            return diaryId;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getNickName() {
            return nickName;
        }

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public ArrayList<String> getDiaryImageUrl() {
            return diaryImageUrl;
        }

        public String getDiseaseType() {
            return diseaseType;
        }

        public String getPesticideType() {
            return pesticideType;
        }

        public String getFertilizerId() {
            return fertilizerId;
        }

        public String getFertilizerType() {
            return fertilizerType;
        }

        public WorkType getWorkType() {
            return workType;
        }

        public String getTargetCrop() {
            return crop;
        }

        public int getNitrogen() {
            return nitrogen;
        }

        public int getPhosphoric() {
            return phosphoric;
        }

        public int getPotossium() {
            return potossium;
        }

        public String getIssuedDate() {
            return issuedDate;
        }
    }

    public class Advice implements Serializable {

        @SerializedName("recipeName")
        public String recipeName;
        @SerializedName("currentStageName")
        public String currentStageName;

        public String getRecipeName() {
            return recipeName;
        }

        public String getCurrentStageName() {
            return currentStageName;
        }
    }

    public class Notification implements Serializable {

        @SerializedName("isMoveToNextStage")
        public boolean isMoveToNextStage;
        @SerializedName("recipeId")
        public int recipeId;
        @SerializedName("currentGrowthStageId")
        public int currentGrowthStageId;
        @SerializedName("processToNextStage")
        public int processToNextStage;

        public boolean isMoveToNextStage() {
            return isMoveToNextStage;
        }

        public int getRecipeId() {
            return recipeId;
        }

        public int getCurrentGrowthStageId() {
            return currentGrowthStageId;
        }

        public int getProcessToNextStage() {
            return processToNextStage;
        }

        public void setMoveToNextStage(boolean moveToNextStage) {
            isMoveToNextStage = moveToNextStage;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public void setCurrentGrowthStageId(int currentGrowthStageId) {
            this.currentGrowthStageId = currentGrowthStageId;
        }

        public void setProcessToNextStage(int processToNextStage) {
            this.processToNextStage = processToNextStage;
        }
    }

    public class ListTimeline implements Serializable {
        @SerializedName("timelineId")
        public String timelineId;
        @SerializedName("timelineType")
        public int timelineType;
        @SerializedName("dateTimeline")
        public String dateTimeline;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("ekfieldId")
        public int ekfieldId;
        @SerializedName("fieldName")
        public String fieldName;
        @SerializedName("description")
        public String description;
        public AdviceDescriptionObject descriptionObject;
        @SerializedName("diaryPost")
        public Diary diary;
        @SerializedName("advice")
        public Advice advice;
        @SerializedName("notification")
        public Notification notification;
        @SerializedName("simpleDescription")
        public String simpleDescription;
        @SerializedName("title")
        public String title;

        public String getTimelineId() {
            return timelineId;
        }

        public int getTimelineType() {
            return timelineType;
        }

        public String getDateTimeline() {
            return dateTimeline;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getEkfieldId() {
            return ekfieldId;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getDescription() {
            return description;
        }

        public AdviceDescriptionObject getDescriptionObject() {
            return descriptionObject;
        }

        public Diary getDiary() {
            return diary;
        }

        public String getTitle() {
            return title;
        }

        public Advice getAdvice() {
            return advice;
        }

        public Notification getNotification() {
            return notification;
        }

        public String getSimpleDescription() {
            return simpleDescription;
        }

        public void setDescriptionObject(AdviceDescriptionObject descriptionObject) {
            this.descriptionObject = descriptionObject;
        }
    }
}
