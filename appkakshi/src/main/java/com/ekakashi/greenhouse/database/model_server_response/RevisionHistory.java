package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.database.model_server_request.EditRecipeRequest;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 1/26/2018.
 */

public class RevisionHistory implements Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("contestFlag")
    private int contestFlag;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("createdFlag")
    private int createdFlag;
    @SerializedName("description")
    private String description;
    @SerializedName("ekUser")
    private ObjectEkUser ekUser;
    @SerializedName("imageType")
    private int imageType;
    @SerializedName("officialFlag")
    private int officialFlag;
    @SerializedName("organizationName")
    private String organizationName;
    @SerializedName("scope")
    public boolean scope;
    @SerializedName("changeAvailability")
    private boolean changeAvailability;
    @SerializedName("recipeType")
    private boolean recipeType;
    @SerializedName("ekfields")
    private List<ObjectEkFields> ekfields;
    @SerializedName("category")
    private String category;
    @SerializedName("prefectures")
    private String prefectures;
    @SerializedName("recipeName")
    public String recipeName;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("recipeStages")
    private List<ObjectGrowth> recipeStages;
    @SerializedName("recipeVersion")
    private int recipeVersion;
    @SerializedName("imagePath")
    private String imagePath;
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

    public RevisionHistory(ObjectEkUser ekUser) {
        this.ekUser = ekUser;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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

    public ObjectEkUser getEkUser() {
        return ekUser;
    }

    public void setEkUser(ObjectEkUser ekUser) {
        this.ekUser = ekUser;
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

    public boolean isScope() {
        return scope;
    }

    public void setScope(boolean scope) {
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

    public List<ObjectEkFields> getEkfields() {
        return ekfields;
    }

    public void setEkfields(List<ObjectEkFields> ekfields) {
        this.ekfields = ekfields;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<ObjectGrowth> getRecipeStages() {
        return recipeStages;
    }

    public void setRecipeStages(List<ObjectGrowth> recipeStages) {
        this.recipeStages = recipeStages;
    }

    public int getRecipeVersion() {
        return recipeVersion;
    }

    public void setRecipeVersion(int recipeVersion) {
        this.recipeVersion = recipeVersion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.contestFlag);
        dest.writeString(this.createdAt);
        dest.writeInt(this.createdFlag);
        dest.writeString(this.description);
        dest.writeParcelable(this.ekUser, flags);
        dest.writeInt(this.imageType);
        dest.writeInt(this.officialFlag);
        dest.writeString(this.organizationName);
        dest.writeByte(this.scope ? (byte) 1 : (byte) 0);
        dest.writeByte(this.changeAvailability ? (byte) 1 : (byte) 0);
        dest.writeByte(this.recipeType ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.ekfields);
        dest.writeString(this.category);
        dest.writeString(this.prefectures);
        dest.writeString(this.recipeName);
        dest.writeString(this.updatedAt);
        dest.writeTypedList(this.recipeStages);
        dest.writeInt(this.recipeVersion);
        dest.writeString(this.imagePath);
        dest.writeByte(this.publicContent1 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent2 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent3 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.publicContent4 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.publicFlag);
        dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.templates);
    }

    protected RevisionHistory(Parcel in) {
        this.id = in.readInt();
        this.contestFlag = in.readInt();
        this.createdAt = in.readString();
        this.createdFlag = in.readInt();
        this.description = in.readString();
        this.ekUser = in.readParcelable(ObjectEkUser.class.getClassLoader());
        this.imageType = in.readInt();
        this.officialFlag = in.readInt();
        this.organizationName = in.readString();
        this.scope = in.readByte() != 0;
        this.changeAvailability = in.readByte() != 0;
        this.recipeType = in.readByte() != 0;
        this.ekfields = in.createTypedArrayList(ObjectEkFields.CREATOR);
        this.category = in.readString();
        this.prefectures = in.readString();
        this.recipeName = in.readString();
        this.updatedAt = in.readString();
        this.recipeStages = in.createTypedArrayList(ObjectGrowth.CREATOR);
        this.recipeVersion = in.readInt();
        this.imagePath = in.readString();
        this.publicContent1 = in.readByte() != 0;
        this.publicContent2 = in.readByte() != 0;
        this.publicContent3 = in.readByte() != 0;
        this.publicContent4 = in.readByte() != 0;
        this.publicFlag = in.readInt();
        this.complete = in.readByte() != 0;
        this.templates = in.createTypedArrayList(EditRecipeRequest.Template.CREATOR);
    }

    public static final Creator<RevisionHistory> CREATOR = new Creator<RevisionHistory>() {
        @Override
        public RevisionHistory createFromParcel(Parcel source) {
            return new RevisionHistory(source);
        }

        @Override
        public RevisionHistory[] newArray(int size) {
            return new RevisionHistory[size];
        }
    };
}
