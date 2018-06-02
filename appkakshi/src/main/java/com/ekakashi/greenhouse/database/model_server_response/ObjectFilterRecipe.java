package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ptloc on 1/23/2018.
 */

public class ObjectFilterRecipe implements Parcelable {
    @SerializedName("places")
    private List<String> places;
    @SerializedName("prefectures")
    private List<String> prefectures;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("crops")
    private List<String> crops;
    @SerializedName("authors")
    private List<String> authors;

    public ObjectFilterRecipe() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.places);
        dest.writeStringList(this.prefectures);
        dest.writeStringList(this.categories);
        dest.writeStringList(this.crops);
        dest.writeStringList(this.authors);
    }

    protected ObjectFilterRecipe(Parcel in) {
        this.places = in.createStringArrayList();
        this.prefectures = in.createStringArrayList();
        this.categories = in.createStringArrayList();
        this.crops = in.createStringArrayList();
        this.authors = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ObjectFilterRecipe> CREATOR = new Parcelable.Creator<ObjectFilterRecipe>() {
        @Override
        public ObjectFilterRecipe createFromParcel(Parcel source) {
            return new ObjectFilterRecipe(source);
        }

        @Override
        public ObjectFilterRecipe[] newArray(int size) {
            return new ObjectFilterRecipe[size];
        }
    };

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public List<String> getPrefectures() {
        return prefectures;
    }

    public void setPrefectures(List<String> prefectures) {
        this.prefectures = prefectures;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCrops() {
        return crops;
    }

    public void setCrops(List<String> crops) {
        this.crops = crops;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
