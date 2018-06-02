package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/20/2018.
 */

public enum EnumAggregateMethod {
    AVERAGE(R.string.aggregate_method_average),
    DAYTIME_AVERAGE(R.string.aggregate_method_daytime_average),
    NIGHTTIME_AVERAGE(R.string.aggregate_method_nighttime_average),
    MAXIMUM(R.string.aggregate_method_max),
    MINIMUM(R.string.aggregate_method_min),
    DIFFERENCE(R.string.aggregate_method_difference),
    TOTAL(R.string.aggregate_method_total);

    private int label;

    EnumAggregateMethod(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
