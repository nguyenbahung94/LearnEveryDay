package com.ekakashi.greenhouse.features.timeline_filter.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/8/2018.
 */

public class FilterModel implements Parcelable {
    private String fromDay;
    private String toDay;
    private String searchText;
    private int startPosition;

    private ArrayList<FilterField> placeList;
    private ArrayList<FilterMember> memberList;
    private ArrayList<String> cropList;
    private ArrayList<Integer> typeList;

    public FilterModel() {
        startPosition = 1;
    }

    public ArrayList<Integer> getSelectedPlaceIds() {
        if (placeList == null || placeList.isEmpty()) {
            return new ArrayList<>();
        } else {
            ArrayList<Integer> ids = new ArrayList<>();
            for (FilterField place : placeList) {
                ids.add(place.getId());
            }
            return ids;
        }
    }

    public ArrayList<Integer> getSelectedMemberIds() {
        if (memberList == null || memberList.isEmpty()) {
            return new ArrayList<>();
        } else {
            ArrayList<Integer> ids = new ArrayList<>();
            for (FilterMember member : memberList) {
                ids.add(member.getEkUserId());
            }
            return ids;
        }
    }

    public ArrayList<FilterField> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(ArrayList<FilterField> placeList) {
        this.placeList = placeList;
    }

    public ArrayList<FilterMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<FilterMember> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<String> getCropList() {
        return cropList;
    }

    public void setCropList(ArrayList<String> cropList) {
        this.cropList = cropList;
    }

    public String getFromDay() {
        return fromDay;
    }

    public void setFromDay(String fromDay) {
        this.fromDay = fromDay;
    }

    public String getToDay() {
        return toDay;
    }

    public void setToDay(String toDay) {
        this.toDay = toDay;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public ArrayList<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<Integer> typeList) {
        this.typeList = typeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fromDay);
        dest.writeString(this.toDay);
        dest.writeString(this.searchText);
        dest.writeInt(this.startPosition);
        dest.writeTypedList(this.placeList);
        dest.writeTypedList(this.memberList);
        dest.writeStringList(this.cropList);
        dest.writeList(this.typeList);
    }

    protected FilterModel(Parcel in) {
        this.fromDay = in.readString();
        this.toDay = in.readString();
        this.searchText = in.readString();
        this.startPosition = in.readInt();
        this.placeList = in.createTypedArrayList(FilterField.CREATOR);
        this.memberList = in.createTypedArrayList(FilterMember.CREATOR);
        this.cropList = in.createStringArrayList();
        this.typeList = new ArrayList<Integer>();
        in.readList(this.typeList, Integer.class.getClassLoader());
    }

    public static final Creator<FilterModel> CREATOR = new Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel source) {
            return new FilterModel(source);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };
}
