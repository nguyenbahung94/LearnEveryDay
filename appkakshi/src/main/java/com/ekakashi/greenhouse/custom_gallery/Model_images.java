package com.ekakashi.greenhouse.custom_gallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nbhung on 3/26/2018.
 */

public class Model_images implements Parcelable{
    private String str_folder;
    private ArrayList<String> al_imagepath;
    public Model_images()
    {

    }

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<String> getAl_imagepath() {
        return al_imagepath;
    }

    public void setAl_imagepath(ArrayList<String> al_imagepath) {
        this.al_imagepath = al_imagepath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.str_folder);
        dest.writeStringList(this.al_imagepath);
    }

    protected Model_images(Parcel in) {
        this.str_folder = in.readString();
        this.al_imagepath = in.createStringArrayList();
    }

    public static final Creator<Model_images> CREATOR = new Creator<Model_images>() {
        @Override
        public Model_images createFromParcel(Parcel source) {
            return new Model_images(source);
        }

        @Override
        public Model_images[] newArray(int size) {
            return new Model_images[size];
        }
    };
}
