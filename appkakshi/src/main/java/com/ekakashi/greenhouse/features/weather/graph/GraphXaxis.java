package com.ekakashi.greenhouse.features.weather.graph;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.timeline_post.model.InputFilterFertilizer;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumAggregateMethod;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumAggregateUnit;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumMeasurementEquipment;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumMeasurementItem;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectGraph;

import java.util.Arrays;

/**
 * Created by nquochuy on 3/12/2018.
 */

public class GraphXaxis implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvMeasurementEquipment;
    private TextView tvMeasurementItem;
    private TextView tvAggregateUnit;
    private TextView tvAggregateMethod;
    private TextView tvRange;

    private EditText edRange;
    private EditText edStandardValue;

    private RelativeLayout relIntegration;
    private RelativeLayout relRange;
    private RelativeLayout relMeasurementEquipment;
    private RelativeLayout relStandardValue;
    private RelativeLayout relAggregateMethod;

    private SwitchCompat switchMovingAverage;
    private SwitchCompat switchIntegration;

    private AddGraphActivity mActivity;
    private DummyGraph mTemplate;
    private ObjectGraph mGraph;
    private LinearLayout layout;

    private final int MEASUREMENT_ITEM = 1;
    private final int MEASUREMENT_EQUIPMENT = 2;
    private final int AGGREGATE_UNIT = 3;
    private final int AGGREGATE_METHOD = 4;

    private final int MEASUREMENT_DATETIME = 1;
    private final int AGGREGATE_METHOD_DIFFERENCE = 6;
    private final int AGGREGATE_UNIT_RAWDATA = 1;

    private String[] arrMeasurementItem = parseEnumToArray(EnumMeasurementItem.values());
    private String[] arrMeasurementEquipment = parseEnumToArray(EnumMeasurementEquipment.values());
    private String[] arrAggregateUnit = parseEnumToArray(EnumAggregateUnit.values());
    private String[] arrAggregateMethod = parseEnumToArray(EnumAggregateMethod.values());

    private int measurementEquipment;
    private int aggregateMethod;
    private int standardValue;
    private int range;


    public GraphXaxis(AddGraphActivity mActivity, LinearLayout layout, DummyGraph template) {
        this.mTemplate = template;
        this.mActivity = mActivity;
        this.layout = layout;
        addControls();
        addEvents();
        initObject();
        applyRule();
    }

    private void addControls() {
        tvMeasurementEquipment = layout.findViewById(R.id.tvMeasurementEquipment);
        tvMeasurementItem = layout.findViewById(R.id.tvMeasurementItem);
        tvAggregateUnit = layout.findViewById(R.id.tvAggregateUnit);
        tvAggregateMethod = layout.findViewById(R.id.tvAggregateMethod);
        tvRange = layout.findViewById(R.id.tvRange);

        edRange = layout.findViewById(R.id.edRange);
        edRange.setFilters(new InputFilter[]{new InputFilterFertilizer(0, 99)});
        edStandardValue = layout.findViewById(R.id.edStandardValue);

        relAggregateMethod = layout.findViewById(R.id.relAggregateMethod);
        relMeasurementEquipment = layout.findViewById(R.id.relMeasurementEquipment);
        relIntegration = layout.findViewById(R.id.relIntegration);
        relStandardValue = layout.findViewById(R.id.relStandardValue);
        relRange = layout.findViewById(R.id.relRange);

        switchMovingAverage = layout.findViewById(R.id.switchMovingAverage);
        switchIntegration = layout.findViewById(R.id.switchIntegration);
    }

    private void addEvents() {
        layout.findViewById(R.id.relMeasurementItem).setOnClickListener(this);
        layout.findViewById(R.id.relMeasurementEquipment).setOnClickListener(this);
        layout.findViewById(R.id.relAggregateUnit).setOnClickListener(this);
        layout.findViewById(R.id.relAggregateMethod).setOnClickListener(this);

        switchIntegration.setOnCheckedChangeListener(this);
        switchMovingAverage.setOnCheckedChangeListener(this);
    }

    private void initObject() {
        if (mTemplate != null) {
            if (mTemplate.getGraph() != null) {
                mGraph = mTemplate.getGraph();
                if (mGraph.getXaxisAggregateUnit() == null) {
                    mGraph.setXaxisAggregateUnit(1);
                }
                if (mGraph.getXaxisRange() == null) {
                    mGraph.setXaxisRange(0);
                }
                if (mGraph.getXaxisAggregationMethod() == null) {
                    mGraph.setXaxisAggregationMethod(1);
                }
                if (mGraph.getXaxisBasisValue() == null) {
                    mGraph.setXaxisBasisValue(0);
                }
                tvMeasurementItem.setText(arrMeasurementItem[mGraph.getXgraphMeasurementItem() - 1]);
                tvMeasurementEquipment.setText(arrMeasurementEquipment[mGraph.getXaxisSenorMesureItemEquipment() - 1]);
                tvAggregateUnit.setText(arrAggregateUnit[mGraph.getXaxisAggregateUnit() - 1]);
                tvAggregateMethod.setText(arrAggregateMethod[mGraph.getXaxisAggregationMethod() - 1]);
                edRange.setText(String.valueOf(mGraph.getXaxisRange()));
                edStandardValue.setText(String.valueOf(mGraph.getXaxisBasisValue()));
                tvRange.setText(arrAggregateUnit[mGraph.getXaxisAggregateUnit() - 1]);

                switchMovingAverage.setChecked(mGraph.isXaxisMovingAverage());
                switchIntegration.setChecked(mGraph.isXaxisIntegration());
                layout.findViewById(R.id.relRange).setVisibility(mGraph.isXaxisMovingAverage() ? View.VISIBLE : View.GONE);
                layout.findViewById(R.id.relStandardValue).setVisibility(mGraph.isXaxisMovingAverage() ? View.VISIBLE : View.GONE);

                measurementEquipment = mGraph.getXaxisSenorMesureItemEquipment();
                aggregateMethod = mGraph.getXaxisAggregationMethod();
                standardValue = mGraph.getXaxisBasisValue();
                range = mGraph.getXaxisRange();
            } else {
                mGraph = new ObjectGraph();
            }
        }
    }

    private void applyRule() {
        ruleMeasurementItem();
        ruleAggregateUnit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relMeasurementItem:
                showDialogListStage(mActivity.getString(R.string.graph_measurement_item), arrMeasurementItem,
                        tvMeasurementItem, MEASUREMENT_ITEM);
                break;
            case R.id.relMeasurementEquipment:
                showDialogListStage(mActivity.getString(R.string.graph_measuring_equipment), arrMeasurementEquipment,
                        tvMeasurementEquipment, MEASUREMENT_EQUIPMENT);
                break;
            case R.id.relAggregateUnit:
                showDialogListStage(mActivity.getString(R.string.graph_aggregate_unit), arrAggregateUnit,
                        tvAggregateUnit, AGGREGATE_UNIT);
                break;
            case R.id.relAggregateMethod:
                showDialogListStage(mActivity.getString(R.string.graph_aggregation_method), arrAggregateMethod,
                        tvAggregateMethod, AGGREGATE_METHOD);
                break;
            default:
                break;
        }
    }


    private void showDialogListStage(final String title, final String[] itemList, final TextView textView, final int type) {
        final BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_bottom_sheet_graph, null);
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);

        tvTitle.setText(title);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(itemList.length - 1);
        numberPicker.setDisplayedValues(itemList);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i].equalsIgnoreCase(textView.getText().toString())) {
                numberPicker.setValue(i);
            }
        }

        dialogView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialogView.findViewById(R.id.tvDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(itemList[numberPicker.getValue()]);
                onXaxisItemChangeListener(type, numberPicker.getValue());
                dialog.cancel();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    private String[] parseEnumToArray(Object[] enumClass) {
        return Arrays.toString(enumClass)
                .replaceAll("^.|.$", "").split(", ");
    }

    private void onXaxisItemChangeListener(int type, int value) {
        switch (type) {
            case MEASUREMENT_ITEM:
                mGraph.setXgraphMeasurementItem(value + 1);
                ruleMeasurementItem();
                break;
            case MEASUREMENT_EQUIPMENT:
                mGraph.setXaxisSenorMesureItemEquipment(value + 1);
                break;
            case AGGREGATE_UNIT:
                mGraph.setXaxisAggregateUnit(value + 1);
                tvRange.setText(tvAggregateUnit.getText().toString());
                ruleAggregateUnit();
                break;
            case AGGREGATE_METHOD:
                mGraph.setXaxisAggregationMethod(value + 1);
                aggregateMethod = value + 1;
                ruleAggregateMethod(true);
                break;
            default:
                break;
        }
    }

    private void ruleMeasurementItem() {
        if ((mGraph.getXgraphMeasurementItem() == MEASUREMENT_DATETIME)) {
            //show 11
            //hide 7
            relAggregateMethod.setVisibility(View.VISIBLE);
            relMeasurementEquipment.setVisibility(View.GONE);
//            mGraph.setXaxisAggregationMethod(aggregateMethod);
//            mGraph.setXgraphMeasurementItem(0);
            ruleAggregateMethod(true);
        } else {
            //show 7
            //hide 11
            if (mGraph.getXaxisAggregateUnit() != AGGREGATE_UNIT_RAWDATA) {
                relAggregateMethod.setVisibility(View.VISIBLE);
//                mGraph.setXaxisAggregationMethod(aggregateMethod);
                ruleAggregateMethod(true);
            } else {
                relAggregateMethod.setVisibility(View.GONE);
                ruleAggregateMethod(false);
//                mGraph.setXaxisAggregationMethod(0);
            }
            relMeasurementEquipment.setVisibility(View.VISIBLE);
//            mGraph.setXaxisSenorMesureItemEquipment(measurementEquipment);
        }
    }

    private void ruleAggregateMethod(boolean isMeasurementItemDatetime) {
        if ((mGraph.getXaxisAggregationMethod() != AGGREGATE_METHOD_DIFFERENCE) && isMeasurementItemDatetime) {
            //show 12
            relIntegration.setVisibility(View.VISIBLE);
        } else {
            //hide 12
            relIntegration.setVisibility(View.GONE);
            switchIntegration.setChecked(false);
//            mGraph.setXaxisIntegration(false);
//            mGraph.setXaxisBasisValue(null);
        }
    }

    private void ruleAggregateUnit() {
        if ((mGraph.getXgraphMeasurementItem() == MEASUREMENT_DATETIME) || (mGraph.getXaxisAggregateUnit() != AGGREGATE_UNIT_RAWDATA)) {
            //show 11
            relAggregateMethod.setVisibility(View.VISIBLE);
            ruleAggregateMethod(true);
        } else {
            //hide 11
            relAggregateMethod.setVisibility(View.GONE);
            ruleAggregateMethod(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchIntegration:
                layout.findViewById(R.id.relStandardValue).setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                mGraph.setXaxisBasisValue(standardValue);
                mGraph.setXaxisIntegration(isChecked);
                break;
            case R.id.switchMovingAverage:
                layout.findViewById(R.id.relRange).setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                mGraph.setXaxisRange(range);
                mGraph.setXaxisMovingAverage(isChecked);
                break;
            default:
                break;
        }
    }

    public void onSaveGraph() {
        if (relMeasurementEquipment.getVisibility() == View.GONE) {
            mGraph.setXaxisSenorMesureItemEquipment(0);
        }
        if (relAggregateMethod.getVisibility() == View.GONE) {
            mGraph.setXaxisAggregationMethod(0);
        }
        if (relRange.getVisibility() == View.VISIBLE) {
            mGraph.setXaxisRange(Integer.valueOf(edRange.getText().toString()));
        } else {
            mGraph.setXaxisRange(0);
        }
        if (relStandardValue.getVisibility() == View.VISIBLE) {
            mGraph.setXaxisBasisValue(Integer.valueOf(edStandardValue.getText().toString()));
        } else {
            mGraph.setXaxisBasisValue(0);
        }
    }
}
