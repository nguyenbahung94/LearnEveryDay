package com.ekakashi.greenhouse.features.weather.object_weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nquochuy on 3/8/2018.
 */

public class ObjectWidget {
    @SerializedName("widgetName")
    private String widgetName;
    private int tag;
    private EnumMeasurementItem measurementItem;

    public ObjectWidget() {
    }

    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public EnumMeasurementItem getMeasurementItem() {
        return measurementItem;
    }

    public void setMeasurementItem(EnumMeasurementItem measurementItem) {
        this.measurementItem = measurementItem;
    }

    @Override
    public String toString() {
        return "ObjectWidget{" +
                "widgetName='" + widgetName + '\'' +
                ", tag=" + tag +
                ", measurementItem=" + measurementItem +
                '}';
    }
}
