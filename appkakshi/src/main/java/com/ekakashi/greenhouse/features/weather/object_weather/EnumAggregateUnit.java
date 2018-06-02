package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/15/2018.
 */

public enum EnumAggregateUnit {
    RAW_DATA(R.string.aggregate_unit_raw_data),
    HOUR(R.string.aggregate_unit_hour),
    DAY(R.string.aggregate_unit_day),
    MONTH(R.string.aggregate_unit_month),
    YEAR(R.string.aggregate_unit_year),
    SEASON(R.string.aggregate_unit_season);

    private int label;

    EnumAggregateUnit(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
