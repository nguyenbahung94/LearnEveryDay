package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectDataForWidgetGraph {
    @SerializedName("data")
    private DataTemplate data;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;

    public static class Temperature {
        @SerializedName("minTemperature")
        private String minTemperature;
        @SerializedName("maxTemperature")
        private String maxTemperature;
        @SerializedName("currentTemperature")
        private String currentTemperature;

        public String getMinTemperature() {
            return minTemperature;
        }

        public String getMaxTemperature() {
            return maxTemperature;
        }

        public String getCurrentTemperature() {
            return currentTemperature;
        }
    }

    public DataTemplate getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public static class DataTemplate {
        @SerializedName("graphData")
        private List<graphData> graphData;
        @SerializedName("widgetDataResponse")
        private List<WidgetDataResponse> widgetDataResponse;

        public void setGraphData(List<graphData> graphData) {
            this.graphData = graphData;
        }

        public void setWidgetDataResponse(List<WidgetDataResponse> widgetDataResponse) {
            this.widgetDataResponse = widgetDataResponse;
        }

        public List<graphData> getGraphData() {
            return graphData;
        }

        public List<WidgetDataResponse> getWidgetDataResponse() {
            return widgetDataResponse;
        }
    }

    public static class WidgetDataResponse {
        @SerializedName("data")
        private WidgetData data;
        @SerializedName("templatedConfigId")
        private int templatedConfigId;

        public WidgetData getData() {
            return data;
        }

        public int getTemplatedConfigId() {
            return templatedConfigId;
        }
    }

    public static class WidgetData {
        @SerializedName("weather")
        private String weather;
        @SerializedName("precipitation")
        private String precipitation;
        @SerializedName("temperature")
        private Temperature temperature;
        @SerializedName("reportTime")
        private String reportTime;
        @SerializedName("windSpeed")
        private String windSpeed;
        @SerializedName("windDirection")
        private String windDirection;
        @SerializedName("moisture")
        private String moisture;
        @SerializedName("solarRadiation")
        private String solarRadiation;
        @SerializedName("sunRiseSunSet")
        private SunRiseSunSet sunRiseSunSet;
        @SerializedName("forecast2Data")
        private List<temperatureForeData> forecast2Data;
        @SerializedName("forecast8Data")
        private List<temperatureForeData> forecast8Data;

        public String getWeather() {
            return weather;
        }

        public String getPrecipitation() {
            return precipitation;
        }

        public Temperature getTemperature() {
            return temperature;
        }

        public String getReportTime() {
            return reportTime;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public String getMoisture() {
            return moisture;
        }

        public String getSolarRadiation() {
            return solarRadiation;
        }

        public SunRiseSunSet getSunRiseSunSet() {
            return sunRiseSunSet;
        }

        public List<temperatureForeData> getForecast2Data() {
            return forecast2Data;
        }

        public List<temperatureForeData> getForecast8Data() {
            return forecast8Data;
        }
    }

    public static class SunRiseSunSet {
        @SerializedName("sunRise")
        private String sunRise;
        @SerializedName("sunSet")
        private String sunSet;

        public String getSunRise() {
            return sunRise;
        }

        public String getSunSet() {
            return sunSet;
        }
    }

    public static class temperatureForeData {
        @SerializedName("temperature")
        private Temperature temperature;
        @SerializedName("weather")
        private String weather;
        @SerializedName("dateTime")
        private String dateTime;

        public Temperature getTemperature() {
            return temperature;
        }

        public String getWeather() {
            return weather;
        }

        public String getDateTime() {
            return dateTime;
        }
    }

}
