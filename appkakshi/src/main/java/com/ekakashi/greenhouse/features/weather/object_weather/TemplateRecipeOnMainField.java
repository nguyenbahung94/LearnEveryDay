package com.ekakashi.greenhouse.features.weather.object_weather;

import android.os.Parcel;

import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TemplateRecipeOnMainField {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<DataTemplate> data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<DataTemplate> getData() {
        return data;
    }

    public static class DataTemplate {
        @SerializedName("graphData")
        private List<graphData> graphData;
        @SerializedName("widgetDataResponse")
        private List<ObjectDataForWidgetGraph.WidgetDataResponse> widgetDataResponse;

        public void setGraphData(List<graphData> graphData) {
            this.graphData = graphData;
        }

        public void setWidgetDataResponse(List<ObjectDataForWidgetGraph.WidgetDataResponse> widgetDataResponse) {
            this.widgetDataResponse = widgetDataResponse;
        }

        public List<graphData> getGraphData() {
            return graphData;
        }

        public List<ObjectDataForWidgetGraph.WidgetDataResponse> getWidgetDataResponse() {
            return widgetDataResponse;
        }
    }
}
