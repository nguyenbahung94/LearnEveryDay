package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptloc on 12/6/2017.
 */

public class ObjectRecipe implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("recipeName")
    private String recipeName;
    @SerializedName("imagePath")
    private String image;
    @SerializedName("category")
    private String category;
    @SerializedName("prefectures")
    private String prefecture;
    @SerializedName("changeAvailability")
    private boolean availability;
    @SerializedName("scope")
    private boolean scope;
    @SerializedName("crop")
    private String crop;
    @SerializedName("recipeVersion")
    private int version;
    @SerializedName("overview")
    private String overview;
    @SerializedName("recipeStages")
    private List<ObjectGrowth> stages;
    private boolean isSelected;
    @SerializedName("description")
    private String description;
    @SerializedName("recipeType")
    private boolean recipeType;
    @SerializedName("ekfields")
    private List<ObjectEkFields> ekFields;
    @SerializedName("revisionHistory")
    private List<RevisionHistory> revisionHistoryList;
    @SerializedName("ekUser")
    private ObjectEkUser ekUser;
    @SerializedName("organizationName")
    private String organizationName;
    @SerializedName("imageType")
    private int imageType;
    @SerializedName("officialFlag")
    private int officialFlag;
    @SerializedName("parentRecipeId")
    private int parentRecipeId;
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
    @SerializedName("iscomplete")
    private boolean complete;
    @SerializedName("templates")
    private List<EditRecipeRequest.Template> templates;
    private String place;
    private int currentStageId;
    private String stageName;
    private boolean isNew;

    public ObjectRecipe() {
    }

    public List<EditRecipeRequest.Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<EditRecipeRequest.Template> templates) {
        this.templates = templates;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
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

    public int getParentRecipeId() {
        return parentRecipeId;
    }

    public void setParentRecipeId(int parentRecipeId) {
        this.parentRecipeId = parentRecipeId;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }


    public List<RevisionHistory> getRevisionHistoryList() {
        return revisionHistoryList;
    }

    public void setRevisionHistoryList(List<RevisionHistory> revisionHistoryList) {
        this.revisionHistoryList = revisionHistoryList;
    }

    public ObjectEkUser getEkUser() {
        return ekUser;
    }

    public void setEkUser(ObjectEkUser ekUser) {
        this.ekUser = ekUser;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isScope() {
        return scope;
    }

    public void setScope(boolean scope) {
        this.scope = scope;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<ObjectGrowth> getStages() {
        return stages;
    }

    public void setStages(List<ObjectGrowth> stages) {
        this.stages = stages;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecipeType() {
        return recipeType;
    }

    public void setRecipeType(boolean recipeType) {
        this.recipeType = recipeType;
    }

    public List<ObjectEkFields> getEkFields() {
        return ekFields;
    }

    public void setEkFields(List<ObjectEkFields> ekFields) {
        this.ekFields = ekFields;
    }

    public int getCurrentStageId() {
        return currentStageId;
    }

    public void setCurrentStageId(int currentStageId) {
        this.currentStageId = currentStageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.recipeName);
        dest.writeString(this.image);
        dest.writeString(this.category);
        dest.writeString(this.prefecture);
        dest.writeByte(this.availability ? (byte) 1 : (byte) 0);
        dest.writeByte(this.scope ? (byte) 1 : (byte) 0);
        dest.writeString(this.crop);
        dest.writeInt(this.version);
        dest.writeString(this.overview);
        dest.writeTypedList(this.stages);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeByte(this.recipeType ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.ekFields);
        dest.writeTypedList(this.revisionHistoryList);
        dest.writeParcelable(this.ekUser, flags);
        dest.writeString(this.organizationName);
        dest.writeInt(this.imageType);
        dest.writeInt(this.officialFlag);
        dest.writeInt(this.parentRecipeId);
        dest.writeByte(this.publicContent1 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent2 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent3 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent4 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.publicFlag);
        dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.templates);
        dest.writeString(this.place);
        dest.writeInt(this.currentStageId);
        dest.writeString(this.stageName);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
    }

    protected ObjectRecipe(Parcel in) {
        this.id = in.readInt();
        this.recipeName = in.readString();
        this.image = in.readString();
        this.category = in.readString();
        this.prefecture = in.readString();
        this.availability = in.readByte() != 0;
        this.scope = in.readByte() != 0;
        this.crop = in.readString();
        this.version = in.readInt();
        this.overview = in.readString();
        this.stages = in.createTypedArrayList(ObjectGrowth.CREATOR);
        this.isSelected = in.readByte() != 0;
        this.description = in.readString();
        this.recipeType = in.readByte() != 0;
        this.ekFields = in.createTypedArrayList(ObjectEkFields.CREATOR);
        this.revisionHistoryList = in.createTypedArrayList(RevisionHistory.CREATOR);
        this.ekUser = in.readParcelable(ObjectEkUser.class.getClassLoader());
        this.organizationName = in.readString();
        this.imageType = in.readInt();
        this.officialFlag = in.readInt();
        this.parentRecipeId = in.readInt();
        this.publicContent1 = in.readByte() != 0;
        this.publicContent2 = in.readByte() != 0;
        this.publicContent3 = in.readByte() != 0;
        this.publicContent4 = in.readByte() != 0;
        this.publicFlag = in.readInt();
        this.complete = in.readByte() != 0;
        this.templates = in.createTypedArrayList(EditRecipeRequest.Template.CREATOR);
        this.place = in.readString();
        this.currentStageId = in.readInt();
        this.stageName = in.readString();
        this.isNew = in.readByte() != 0;
    }

    public static final Creator<ObjectRecipe> CREATOR = new Creator<ObjectRecipe>() {
        @Override
        public ObjectRecipe createFromParcel(Parcel source) {
            return new ObjectRecipe(source);
        }

        @Override
        public ObjectRecipe[] newArray(int size) {
            return new ObjectRecipe[size];
        }
    };
}
