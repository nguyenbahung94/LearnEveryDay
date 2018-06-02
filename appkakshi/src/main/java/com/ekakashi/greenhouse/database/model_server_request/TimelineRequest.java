package com.ekakashi.greenhouse.database.model_server_request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nquochuy on 1/11/2018.
 */

public class TimelineRequest {
    @SerializedName("returnMaxNum")
    private int returnMaxNum;
    @SerializedName("searchValue")
    private String searchValue;
    @SerializedName("fromDate")
    private String fromDay;
    @SerializedName("toDate")
    private String toDay;
    @SerializedName("startPosition")
    private int startPosition;
    @SerializedName("place")
    private List<Integer> placeList;
    @SerializedName("member")
    private List<Integer> memberList;
    @SerializedName("timelineType")
    private List<Integer> timelineType;

    public TimelineRequest() {
    }

    public int getReturnMaxNum() {
        return returnMaxNum;
    }

    public void setReturnMaxNum(int returnMaxNum) {
        this.returnMaxNum = returnMaxNum;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public List<Integer> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Integer> placeList) {
        this.placeList = placeList;
    }

    public List<Integer> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Integer> memberList) {
        this.memberList = memberList;
    }

    public List<Integer> getTimelineType() {
        return timelineType;
    }

    public void setTimelineType(List<Integer> timelineType) {
        this.timelineType = timelineType;
    }
}
