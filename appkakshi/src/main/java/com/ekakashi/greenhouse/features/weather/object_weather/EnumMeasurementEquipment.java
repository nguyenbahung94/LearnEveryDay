package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/15/2018.
 */

public enum EnumMeasurementEquipment {
    KM1_MESH_UPTO_3DAYS(R.string._1km_mesh);

    private int label;

    EnumMeasurementEquipment(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
