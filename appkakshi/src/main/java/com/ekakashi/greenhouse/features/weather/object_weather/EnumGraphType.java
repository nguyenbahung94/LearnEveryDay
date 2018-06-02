package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/27/2018.
 */

public enum EnumGraphType {
    LINE(R.string.graph_line),
    BAR(R.string.graph_bar);

    private int label;

    EnumGraphType(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
