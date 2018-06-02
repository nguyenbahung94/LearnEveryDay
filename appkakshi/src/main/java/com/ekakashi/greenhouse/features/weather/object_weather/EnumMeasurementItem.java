package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/8/2018.
 */

public enum EnumMeasurementItem {
    DATE_TIME(R.string.measurement_item_datetime),
    WEATHER(R.string.measurement_item_weather),
    TEMPERATURE(R.string.measurement_item_temperature),
    HUMIDITY(R.string.measurement_item_humidity),
    WIND_DIRECTION(R.string.measurement_item_wind_direction),
    WIND_VELOCITY(R.string.measurement_item_wind_velecity),
    PRECIPITATION(R.string.measurement_item_precipitation),
    AMOUNT_OF_INSOLATION(R.string.measurement_item_amount_insolation),
    INSOLATION_FORECAST_3_DAYS(R.string.measurement_item_insolation_forecast_3_days),
    INSOLATION_FORECAST_8_DAYS(R.string.measurement_item_insolation_forecast_8_days),
    WEATHER_FORECAST_2_DAYS(R.string.measurement_item_weather_forecast_2_days),
    TEMPERATURE_FORECAST_2_DAYS(R.string.measurement_item_temperature_forecast_2_days),
    HUMIDITY_FORECAST_2_DAYS(R.string.measurement_item_humidity_forecast_2_days),
    WIND_DIRECTION_FORECAST_2_DAYS(R.string.measurement_item_wind_direction_forecast_2_days),
    WIND_VELOCITY_FORECAST_2_DAYS(R.string.measurement_item_wind_velocity_forecast_2_days),
    PRECIPITATION_FORECAST_2_DAYS(R.string.measurement_item_precipitation_forecast_2_days),
    WEATHER_FORECAST_8_DAYS(R.string.measurement_item_weather_forecast_8_days),
    TEMPERATURE_FORECAST_8_DAYS(R.string.measurement_item_temperature_forecast_8_days),
    HUMIDITY_FORECAST_8_DAYS(R.string.measurement_item_humidity_forecast_8_days),
    WIND_DIRECTION_FORECAST_8_DAYS(R.string.measurement_item_wind_direction_forecast_8_days),
    WIND_VELOCITY_FORECAST_8_DAYS(R.string.measurement_item_wind_velocity_forecast_8_days),
    PRECIPITATION_FORECAST_8_DAYS(R.string.measurement_item_precipitation_forecast_8_days),
    WEATHER_HISTORY(R.string.measurement_item_weather_history),
    TEMPERATURE_HISTORY(R.string.measurement_item_temperature_history),
    HUMIDITY_HISTORY(R.string.measurement_item_humidity_history),
    WIND_DIRECTION_HISTORY(R.string.measurement_item_wind_direction_history),
    WIND_VELOCITY_HISTORY(R.string.measurement_item_wind_velecity_history),
    PRECIPITATION_HISTORY(R.string.measurement_item_precipitation_history),
    INSOLATION_HISTORY(R.string.measurement_item_insolation_history),
    SUNRISE(R.string.measurement_item_sunrise),
    SUNSET(R.string.measurement_item_sunset);

    private int label;

    EnumMeasurementItem(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
