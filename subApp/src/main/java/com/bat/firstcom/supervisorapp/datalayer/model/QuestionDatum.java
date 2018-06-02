package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class QuestionDatum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("HasNA")
    @Expose
    private Integer hasNA;
    @SerializedName("GroupId")
    @Expose
    private Integer groupId;
    @SerializedName("GroupName")
    @Expose
    private String groupName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getHasNA() {
        return hasNA;
    }

    public void setHasNA(Integer hasNA) {
        this.hasNA = hasNA;
    }
}
