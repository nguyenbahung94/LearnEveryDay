package com.ekakashi.greenhouse.features.weather.object_weather;

import java.util.ArrayList;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectTemplate {
    private String templateId;
    private EnumWeather TemplateType;
    private String graph;
    private String iconUrl;
    private String name;
    private ArrayList<String> tags;
    private int sortNo;
    private int numberOfColumns;

    public ObjectTemplate(String templateId, EnumWeather templateType, String graph, String iconUrl, String name, ArrayList<String> tags, int sortNo, int numberOfColumns) {
        this.templateId = templateId;
        TemplateType = templateType;
        this.graph = graph;
        this.iconUrl = iconUrl;
        this.name = name;
        this.tags = tags;
        this.sortNo = sortNo;
        this.numberOfColumns = numberOfColumns;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public EnumWeather getTemplateType() {
        return TemplateType;
    }

    public void setTemplateType(EnumWeather templateType) {
        TemplateType = templateType;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }
}
