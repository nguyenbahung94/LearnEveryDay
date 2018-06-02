package com.ekakashi.greenhouse.features.weather.object_weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;

/**
 * Created by nquochuy on 3/20/2018.
 */

public enum EnumPeriod {
    LATEST(R.string.period_latest_title),
    TODAY(R.string.period_today_title),
    YESTERDAY(R.string.period_yesterday_title),
    LAST_7_DAYS(R.string.period_last7days_title),
    LAST_30_DAYS(R.string.period_last30days_title),
    LAST_MONTH(R.string.period_last_month_title),
    SPECIFY_DATE(R.string.period_specify_date_title),
    DESIGNATE_STAGE(R.string.period_specify_stage_title);

    private int label;

    EnumPeriod(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return App.getsInstance().getString(label);
    }
}
