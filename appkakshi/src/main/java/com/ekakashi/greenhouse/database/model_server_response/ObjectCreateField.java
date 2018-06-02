package com.ekakashi.greenhouse.database.model_server_response;


import android.os.Parcel;
import android.os.Parcelable;

import com.ekakashi.greenhouse.map.CustomLatLngObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectCreateField implements Parcelable {
    private String placeName;
    private String placeType;
    private int recipeId;
    private int currentStageId;
    private String stageName;
    private ObjectRecipe objectRecipe;
    private List<CustomLatLngObject> listPolygon;
    private List<ResponseDetailField.Polygon> polygon;
    private int idField;
    private String IdUser;
    private int countRecipe;
    private int countMember;
    private int countDevice;
    private int sortNo;
    private String address;
    private String recipeName;
    private String recipeDescription;
    private String recipeImage;

    public ObjectCreateField() {
    }

    public ObjectCreateField(String placeName, String placeType) {
        this.placeName = placeName;
        this.placeType = placeType;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getCurrentStageId() {
        return currentStageId;
    }

    public void setCurrentStageId(int currentStageId) {
        this.currentStageId = currentStageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public ObjectRecipe getObjectRecipe() {
        return objectRecipe;
    }

    public void setObjectRecipe(ObjectRecipe objectRecipe) {
        this.objectRecipe = objectRecipe;
    }

    public List<CustomLatLngObject> getListPolygon() {
        return listPolygon;
    }

    public void setListPolygon(List<CustomLatLngObject> listPolygon) {
        this.listPolygon = listPolygon;
    }

    public List<ResponseDetailField.Polygon> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<ResponseDetailField.Polygon> polygon) {
        this.polygon = polygon;
    }

    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public int getCountRecipe() {
        return countRecipe;
    }

    public void setCountRecipe(int countRecipe) {
        this.countRecipe = countRecipe;
    }

    public int getCountMember() {
        return countMember;
    }

    public void setCountMember(int countMember) {
        this.countMember = countMember;
    }

    public int getCountDevice() {
        return countDevice;
    }

    public void setCountDevice(int countDevice) {
        this.countDevice = countDevice;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.placeName);
        dest.writeString(this.placeType);
        dest.writeInt(this.recipeId);
        dest.writeInt(this.currentStageId);
        dest.writeString(this.stageName);
        dest.writeParcelable(this.objectRecipe, flags);
        dest.writeTypedList(this.listPolygon);
        dest.writeTypedList(this.polygon);
        dest.writeInt(this.idField);
        dest.writeString(this.IdUser);
        dest.writeInt(this.countRecipe);
        dest.writeInt(this.countMember);
        dest.writeInt(this.countDevice);
        dest.writeInt(this.sortNo);
        dest.writeString(this.address);
        dest.writeString(this.recipeName);
        dest.writeString(this.recipeDescription);
        dest.writeString(this.recipeImage);
    }

    protected ObjectCreateField(Parcel in) {
        this.placeName = in.readString();
        this.placeType = in.readString();
        this.recipeId = in.readInt();
        this.currentStageId = in.readInt();
        this.stageName = in.readString();
        this.objectRecipe = in.readParcelable(ObjectRecipe.class.getClassLoader());
        this.listPolygon = in.createTypedArrayList(CustomLatLngObject.CREATOR);
        this.polygon = in.createTypedArrayList(ResponseDetailField.Polygon.CREATOR);
        this.idField = in.readInt();
        this.IdUser = in.readString();
        this.countRecipe = in.readInt();
        this.countMember = in.readInt();
        this.countDevice = in.readInt();
        this.sortNo = in.readInt();
        this.address = in.readString();
        this.recipeName = in.readString();
        this.recipeDescription = in.readString();
        this.recipeImage = in.readString();
    }

    public static final Creator<ObjectCreateField> CREATOR = new Creator<ObjectCreateField>() {
        @Override
        public ObjectCreateField createFromParcel(Parcel source) {
            return new ObjectCreateField(source);
        }

        @Override
        public ObjectCreateField[] newArray(int size) {
            return new ObjectCreateField[size];
        }
    };
}
