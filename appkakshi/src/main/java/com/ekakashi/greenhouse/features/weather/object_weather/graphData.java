package com.ekakashi.greenhouse.features.weather.object_weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class graphData {
    @SerializedName("templatedId")
    private int templatedId;
    @SerializedName("measureName")
    private String measureName;
    @SerializedName("yaxisResponse")
    private List<yaxisResponse> yaxisResponseList;

    public int getTemplatedId() {
        return templatedId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public List<yaxisResponse> getYaxisResponseList() {
        return yaxisResponseList;
    }

    public static class yaxisResponse {
        @SerializedName("yasixId")
        private int yasixId;
        @SerializedName("measureName")
        private String measureName;
        @SerializedName("icon")
        private String icon;
        @SerializedName("data")
        private List<dataOfItemYaxis> dataOfItemYaxisList;
        @SerializedName("calculationMethod")
        private String calculationMethod;
        @SerializedName("ymeasureId")
        private int ymeasureId;
        @SerializedName("ymeasureName")
        private String ymeasureName;

        public String getCalculationMethod() {
            return calculationMethod;
        }

        public int getYasixId() {
            return yasixId;
        }

        public String getMeasureName() {
            return measureName;
        }

        public String getIcon() {
            return icon;
        }

        public List<dataOfItemYaxis> getDataOfItemYaxisList() {
            return dataOfItemYaxisList;
        }
    }

    public static class dataOfItemYaxis {
        @SerializedName("x")
        private double x;
        @SerializedName("y")
        private double y;
        @SerializedName("time")
        private String time;
        @SerializedName("type")
        private int type;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public String getTime() {
            return time;
        }

        public int getType() {
            return type;
        }
    }
}
