package com.ekakashi.greenhouse.features.timeline_filter.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/15/2018.
 */

public class FilterMemberModel {
    @SerializedName("listEkFieldId")
    private ArrayList<Integer> ekFieldId;

    public FilterMemberModel(ArrayList<Integer> ekFieldId) {
        this.ekFieldId = ekFieldId;
    }

    public ArrayList<Integer> getEkFieldId() {
        return ekFieldId;
    }

    public void setEkFieldId(ArrayList<Integer> ekFieldId) {
        this.ekFieldId = ekFieldId;
    }
}
