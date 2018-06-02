package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectCommonTemplateRecipe {
    private List<Data> dataTemplateRecipe;
    private ObjectDataForWidgetGraph.DataTemplate dataTemplateMainField;
    private List<graphData> listGraph;
    private List<ObjectDataForWidgetGraph.WidgetDataResponse> listWidgetDataResponses;
    private Map<Integer, Object> listMapObject = new HashMap<>();

    public ObjectCommonTemplateRecipe() {
    }

    public Map<Integer, Object> getListMapObject() {
        return listMapObject;
    }

    public ObjectDataForWidgetGraph.DataTemplate getdataTemplateMainField() {
        return dataTemplateMainField;
    }

    public void setdataTemplateMainField(ObjectDataForWidgetGraph.DataTemplate dataTemplateMainField) {
        this.dataTemplateMainField = dataTemplateMainField;
        this.listGraph = dataTemplateMainField.getGraphData();
        this.listWidgetDataResponses = dataTemplateMainField.getWidgetDataResponse();
    }

    public List<Data> getdataTemplateRecipe() {
        return dataTemplateRecipe;
    }

    public void setdataTemplateRecipe(List<Data> dataTemplateRecipe) {
        this.dataTemplateRecipe = dataTemplateRecipe;
    }

    public void compateTwoList() {
        listMapObject.clear();
        List<Data> data = new ArrayList<>();
        for (Data ss : dataTemplateRecipe) {
            boolean find = false;
            for (graphData ssGraphData : listGraph) {
                if (ss.getId() == ssGraphData.getTemplatedId()) {
                    find = true;
                    data.add(ss);
                    listMapObject.put(ss.getId(), ssGraphData);
                }
            }
            if (!find) {
                for (ObjectDataForWidgetGraph.WidgetDataResponse ssWidget : listWidgetDataResponses) {
                    if (ss.getId() == ssWidget.getTemplatedConfigId()) {
                        data.add(ss);
                        listMapObject.put(ss.getId(), ssWidget);
                    }
                }
            }
        }
        setdataTemplateRecipe(data);
    }
}
