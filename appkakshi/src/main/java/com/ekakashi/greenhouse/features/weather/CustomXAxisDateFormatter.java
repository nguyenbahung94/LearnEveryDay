package com.ekakashi.greenhouse.features.weather;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.features.weather.object_weather.graphData;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class CustomXAxisDateFormatter implements IAxisValueFormatter {
    private List<graphData.dataOfItemYaxis> longList;

    public CustomXAxisDateFormatter(List<graphData.dataOfItemYaxis> longList) {
        this.longList = longList;

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (!longList.isEmpty() && longList.size() > Math.abs((int) value)) {
            String date = DateTimeNow.parseMillisecondToFormatDate(longList.get((int) value).getTime(), App.getsInstance().getString(R.string.format_date_time));

            return date;
        }
        return "";
    }
}
