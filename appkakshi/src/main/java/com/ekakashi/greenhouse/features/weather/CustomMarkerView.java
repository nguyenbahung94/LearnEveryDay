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
import com.tooltip.Tooltip;

import java.util.List;

public class CustomMarkerView extends MarkerView {
    private TextView tvItem;
    private List<Data> list;
    private graphData graph;
    private List<graphData.yaxisResponse> listSave;
    private Context context;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkerView(Context context, int layoutResource, List<Data> list, graphData graph, List<graphData.yaxisResponse> listSave) {
        super(context, layoutResource);
        this.context = context;
        tvItem = (TextView) findViewById(R.id.tvItem);
        this.list = list;
        this.graph = graph;
        this.listSave = listSave;
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF((-getWidth() / 2), -getHeight());
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

    }


}
