package com.ekakashi.greenhouse.features.weather;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.features.weather.object_weather.graphData;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class MyMarkerView extends MarkerView {
    private TextView tvContent;
    private List<Data> list;
    private List<graphData.yaxisResponse> listSave;
    private graphData graph;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource, List<Data> list, List<graphData.yaxisResponse> listSave, graphData graph) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvData);
        this.list = list;
        this.listSave = listSave;
        this.graph = graph;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String unit = "";
        for (Data ss : list) {
            if (graph.getTemplatedId() == ss.getId()) {
                if (ss.getTypeOfWeather() != null) {
                    unit = NumberOfBlock.getUnit(ss.getTypeOfWeather());
                }

            }
        }
        for (graphData.yaxisResponse ss : listSave) {
            String tam = searchItem((int) e.getX(), (int) e.getY(), ss.getDataOfItemYaxisList(), unit);
            Log.e("string tam", tam);
            Log.e("string tam", tam);
            if (!tam.isEmpty()) {
                tvContent.setText(tam);
                return;
            }
        }
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF((-getWidth() / 2), -getHeight());
    }

    public String searchItem(int x, int y, List<graphData.dataOfItemYaxis> graph, String unit) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).getY() == y && i == x) {
                return DateTimeNow.parseMillisecondToFormatDate(graph.get(i).getTime(), App.getsInstance().getString(R.string.format_date_time)) + " " + graph.get(i).getY() + " " + unit;
            }
        }
        return "";
    }
}
