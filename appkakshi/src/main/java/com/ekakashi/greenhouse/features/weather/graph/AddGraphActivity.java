package com.ekakashi.greenhouse.features.weather.graph;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.google.gson.Gson;

public class AddGraphActivity extends BaseActivity implements View.OnClickListener {

    private Button btnBasic;
    private Button btnXaxis;
    private Button btnYaxis;
    private LinearLayout layout_graph_basic;
    private LinearLayout layout_graph_xaxis;
    private LinearLayout layout_graph_yaxis;

    private final int BASIC = 1;
    private final int XAXIS = 2;
    private final int YAXIS = 3;

    private DummyGraph template;

    private GraphBasic graphBasic;
    private GraphXaxis graphXaxis;
    private GraphYaxis graphYaxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_graph);

        addControls();
        addToolbar();
        addEvents();
        callAPI();
        graphBasic = new GraphBasic(this, layout_graph_basic, template);
        graphXaxis = new GraphXaxis(this, layout_graph_xaxis, template);
        graphYaxis = new GraphYaxis(this, layout_graph_yaxis, template);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT,getString(R.string.toolbar_save));
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                saveTemplate();
            }
        });
    }

    private void saveTemplate() {
        graphXaxis.onSaveGraph();
        showSimpleMessage(template.toString());
    }

    private void callAPI() {
        String json = "{ \"id\": 5, \"graph\": { \"id\": 4, \"templateConfigId\": 1, \"preriod\": 1, \"startDate\": 1531199450000, \"endDate\": 1531199450000, \"isDisplayStage\": false, \"startStage\": null, \"endStage\": null, \"sortNo\": 1, \"graphV3YAxis\": [ { \"id\": 5, \"graphV3Id\": 4, \"axisSide\": 1, \"measuringInstrument\": 5, \"aggregateType\": 1, \"isIntegration\": true, \"basisValue\": 4, \"graphType\": 1, \"ygraphMeasurementItemId\": 1 }, { \"id\": 6, \"graphV3Id\": 4, \"axisSide\": 2, \"measuringInstrument\": 6, \"aggregateType\": 1, \"isIntegration\": true, \"basisValue\": 5, \"graphType\": 1, \"ygraphMeasurementItemId\": 1 }, { \"id\": 11, \"graphV3Id\": 4, \"axisSide\": 1, \"measuringInstrument\": 5, \"aggregateType\": 1, \"isIntegration\": true, \"basisValue\": 4, \"graphType\": 1, \"ygraphMeasurementItemId\": 5 }, { \"id\": 12, \"graphV3Id\": 4, \"axisSide\": 2, \"measuringInstrument\": 6, \"aggregateType\": 1, \"isIntegration\": true, \"basisValue\": 5, \"graphType\": 1, \"ygraphMeasurementItemId\": 6 } ], \"xaxisMovingAverage\": false, \"xaxisDatetime\": true, \"xaxisIntegration\": false, \"xaxisSenorMesureItemEquipment\": 1, \"xaxisAggregateUnit\": null, \"xaxisRange\": null, \"xaxisAggregationmethod\": null, \"xaxisBasisValue\": null, \"xgraphMeasurementItem\": 1 }, \"widget\": null, \"iconUrl\": null, \"templateType\": 37, \"tags\": [ \"Temperature\\tGraph Test 2\" ], \"name\": \"Template test 2\", \"sortNo\": 1, \"numberOfColumn\": 2, \"isDefault\": true, \"recipeId\": 2837 }";
        template = new Gson().fromJson(json, DummyGraph.class);
    }

    private void addControls() {
        btnBasic = findViewById(R.id.btnBasic);
        btnXaxis = findViewById(R.id.btnXaxis);
        btnYaxis = findViewById(R.id.btnYaxis);
        layout_graph_basic = findViewById(R.id.layout_graph_basic);
        layout_graph_xaxis = findViewById(R.id.layout_graph_xaxis);
        layout_graph_yaxis = findViewById(R.id.layout_graph_yaxis);

        layout_graph_basic.setVisibility(View.VISIBLE);
        changeButtonTitle(BASIC);
    }

    private void addEvents() {
        btnBasic.setOnClickListener(this);
        btnXaxis.setOnClickListener(this);
        btnYaxis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBasic:
                changeButtonTitle(BASIC);
                break;
            case R.id.btnXaxis:
                changeButtonTitle(XAXIS);
                break;
            case R.id.btnYaxis:
                changeButtonTitle(YAXIS);
                break;
            default:
                break;
        }
    }

    private void changeButtonTitle(int flag) {
        layout_graph_basic.setVisibility(flag == BASIC ? View.VISIBLE : View.GONE);
        layout_graph_xaxis.setVisibility(flag == XAXIS ? View.VISIBLE : View.GONE);
        layout_graph_yaxis.setVisibility(flag == YAXIS ? View.VISIBLE : View.GONE);

        btnBasic.setBackground(getResources()
                .getDrawable(flag == BASIC ? R.drawable.graph_title_basic_pressed : R.drawable.graph_title_basic_unpressed));
        btnXaxis.setBackground(getResources()
                .getDrawable(flag == XAXIS ? R.drawable.graph_title_xaxis_pressed : R.drawable.graph_title_xaxis_unpressed));
        btnYaxis.setBackground(getResources()
                .getDrawable(flag == YAXIS ? R.drawable.graph_title_yaxis_pressed : R.drawable.graph_title_yaxis_unpressed));

        btnBasic.setTextColor(getResources().getColor(flag == BASIC ? R.color.white : R.color.tv_graph_title));
        btnXaxis.setTextColor(getResources().getColor(flag == XAXIS ? R.color.white : R.color.tv_graph_title));
        btnYaxis.setTextColor(getResources().getColor(flag == YAXIS ? R.color.white : R.color.tv_graph_title));

    }
}
