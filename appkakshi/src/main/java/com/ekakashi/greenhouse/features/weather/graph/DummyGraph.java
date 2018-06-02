package com.ekakashi.greenhouse.features.weather.graph;

import com.ekakashi.greenhouse.features.weather.object_weather.ObjectGraph;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nquochuy on 3/12/2018.
 */

public class DummyGraph {

    @SerializedName("id")
    public int id;
    @SerializedName("graph")
    public ObjectGraph graph;
    @SerializedName("widget")
    public String widget;
    @SerializedName("iconUrl")
    public String iconUrl;
    @SerializedName("templateType")
    public int templateType;
    @SerializedName("tags")
    public List<String> tags;
    @SerializedName("name")
    public String name;
    @SerializedName("sortNo")
    public int sortNo;
    @SerializedName("numberOfColumn")
    public int numberOfColumn;
    @SerializedName("isDefault")
    public boolean isDefault;
    @SerializedName("recipeId")
    public int recipeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjectGraph getGraph() {
        return graph;
    }

    public void setGraph(ObjectGraph graph) {
        this.graph = graph;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getNumberOfColumn() {
        return numberOfColumn;
    }

    public void setNumberOfColumn(int numberOfColumn) {
        this.numberOfColumn = numberOfColumn;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "DummyGraph{" +
                "id=" + id +
                ", graph=" + graph.toString() +
                ", widget='" + widget + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", templateType=" + templateType +
                ", tags=" + tags +
                ", name='" + name + '\'' +
                ", sortNo=" + sortNo +
                ", numberOfColumn=" + numberOfColumn +
                ", isDefault=" + isDefault +
                ", recipeId=" + recipeId +
                '}';
    }
}
