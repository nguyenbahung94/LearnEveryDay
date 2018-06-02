package com.ekakashi.greenhouse.features.weather.graph;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumAggregateMethod;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumGraphType;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumMeasurementEquipment;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumMeasurementItem;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectYaxis;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nquochuy on 3/21/2018.
 */

public class GraphYaxis implements View.OnClickListener {

    private AddGraphActivity mActivity;
    private DummyGraph mTemplate;
    private ArrayList<ObjectYaxis> listYaxis;
    private LinearLayout layout;

    private boolean isFirstXaxis = true;

    private final int MEASUREMENT_INSTRUMENT = 1;
    private final int MEASUREMENT_ITEM = 2;
    private final int CALCULATION_METHOD = 3;
    private final int GRAPH_TYPE = 4;

    private final int YAXIS_LEFT = 1;
    private final int YAXIS_RIGHT = 2;

    private final int AGGREGATE_METHOD_DIFFERENCE = 6;

    private String[] arrMeasurementInstrument = parseEnumToArray(EnumMeasurementEquipment.values());
    private String[] arrMeasurementItem = parseEnumToArray(EnumMeasurementItem.values());
    private String[] arrCalculationMethod = parseEnumToArray(EnumAggregateMethod.values());
    private String[] arrGraphType = parseEnumToArray(EnumGraphType.values());

    public GraphYaxis(AddGraphActivity mActivity, LinearLayout layout, DummyGraph template) {
        this.mActivity = mActivity;
        this.mTemplate = template;
        this.layout = layout;
        addControls();
        addEvents();
        initObject();
    }

    private void addControls() {

    }

    private void addEvents() {
        layout.findViewById(R.id.relAddYaxisLeft).setOnClickListener(this);
        layout.findViewById(R.id.relAddYaxisRight).setOnClickListener(this);
    }

    private void initObject() {
        if (mTemplate != null) {
            if (mTemplate.getGraph() != null) {
                if (mTemplate.getGraph().getYaxis() != null) {
                    listYaxis = mTemplate.getGraph().getYaxis();
                    for (ObjectYaxis yaxis : listYaxis) {
                        addMoreLayout(yaxis);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relAddYaxisLeft:
                ObjectYaxis yaxisLeft = new ObjectYaxis();
                yaxisLeft.setAxisSide(YAXIS_LEFT);
                yaxisLeft.setNewYaxis(true);
                addMoreLayout(yaxisLeft);
                break;
            case R.id.relAddYaxisRight:
                ObjectYaxis yaxisRight = new ObjectYaxis();
                yaxisRight.setAxisSide(YAXIS_RIGHT);
                yaxisRight.setNewYaxis(true);
                addMoreLayout(yaxisRight);
                break;
            default:
                break;
        }
    }

    private void addMoreLayout(final ObjectYaxis yaxis) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.item_yaxis, null);
        final ViewGroup vgLeft = layout.findViewById(R.id.layoutYaxisLeft);
        final ViewGroup vgRight = layout.findViewById(R.id.layoutYaxisRight);

        TextView tvYaxis = view.findViewById(R.id.tvYaxis);
        final TextView tvMeasurementInstrument = view.findViewById(R.id.tvMeasurementInstrument);
        final TextView tvMeasurementItem = view.findViewById(R.id.tvMeasurementItem);
        final TextView tvCalculator = view.findViewById(R.id.tvCalculator);
        final TextView tvGraphType = view.findViewById(R.id.tvGraphType);
        TextView edReferenceValue = view.findViewById(R.id.edReferenceValue);
        SwitchCompat switchAccumulation = view.findViewById(R.id.switchAccumulation);
        final RelativeLayout relReferenceValue = view.findViewById(R.id.relReferenceValue);

        ruleCalculationMethod(yaxis, view);

        view.findViewById(R.id.relMeasurementInstrument).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListStage(yaxis, mActivity.getString(R.string.graph_aggregate_unit), arrMeasurementInstrument, tvMeasurementInstrument, view, MEASUREMENT_INSTRUMENT);
            }
        });
        view.findViewById(R.id.relMeasurementItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListStage(yaxis, mActivity.getString(R.string.graph_aggregate_unit), arrMeasurementItem, tvMeasurementItem, view, MEASUREMENT_ITEM);
            }
        });
        view.findViewById(R.id.relCalculationMethod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListStage(yaxis, mActivity.getString(R.string.graph_aggregate_unit), arrCalculationMethod, tvCalculator, view, CALCULATION_METHOD);
            }
        });
        view.findViewById(R.id.relGraphType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListStage(yaxis, mActivity.getString(R.string.graph_aggregate_unit), arrGraphType, tvGraphType, view, GRAPH_TYPE);
            }
        });

        switchAccumulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relReferenceValue.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        if (arrMeasurementInstrument.length >= yaxis.getMeasuringInstrument()) {
            tvMeasurementInstrument.setText(arrMeasurementInstrument[yaxis.getMeasuringInstrument()]);
        }
        if (arrMeasurementItem.length >= yaxis.getYgraphMeasurementItemId()) {
            tvMeasurementItem.setText(arrMeasurementItem[yaxis.getYgraphMeasurementItemId()]);
        }
        if (arrCalculationMethod.length >= yaxis.getAggregateType()) {
            tvCalculator.setText(arrCalculationMethod[yaxis.getAggregateType()]);
        }
        if (arrGraphType.length >= yaxis.getGraphType()) {
            tvGraphType.setText(arrGraphType[yaxis.getGraphType()]);
        }

        if (yaxis.getBasisValue() == null) {
            yaxis.setBasisValue(0);
        }
        edReferenceValue.setText(String.valueOf(yaxis.getBasisValue()));
        switchAccumulation.setChecked(yaxis.isIntegration());

        ImageView imgYaxisDelete = view.findViewById(R.id.imgYaxisDelete);
        imgYaxisDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listYaxis.remove(yaxis);
                if (yaxis.getAxisSide() == YAXIS_LEFT) {
                    vgLeft.removeView(view);
                } else if (yaxis.getAxisSide() == YAXIS_RIGHT) {
                    vgRight.removeView(view);
                }
            }
        });

        if (isFirstXaxis) {
            imgYaxisDelete.setVisibility(View.GONE);
            isFirstXaxis = false;
        }

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 10);

        if (yaxis.getAxisSide() == YAXIS_LEFT) {
            vgLeft.addView(view, params);
            tvYaxis.setText(R.string.graph_yaxis_left);
        } else if (yaxis.getAxisSide() == YAXIS_RIGHT) {
            vgRight.addView(view, params);
            tvYaxis.setText(R.string.graph_yaxis_right);
        }

        //Yaxis from json is false, Yaxis from Add new Yaxis is true
        if (yaxis.isNewYaxis()) {
            listYaxis.add(yaxis);
        }
    }

    private String[] parseEnumToArray(Object[] enumClass) {
        return Arrays.toString(enumClass)
                .replaceAll("^.|.$", "").split(", ");
    }

    private void showDialogListStage(final ObjectYaxis yaxis, final String title, final String[] itemList, final TextView textView, final View layoutYaxis, final int type) {
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
                onYaxisItemChangeListener(yaxis, type, numberPicker.getValue(), layoutYaxis);
                dialog.cancel();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void onYaxisItemChangeListener(ObjectYaxis yaxis, int type, int value, View view) {
        switch (type) {
            case MEASUREMENT_INSTRUMENT:
                yaxis.setMeasuringInstrument(value + 1);
                break;
            case MEASUREMENT_ITEM:
                yaxis.setYgraphMeasurementItemId(value + 1);
                break;
            case CALCULATION_METHOD:
                yaxis.setAggregateType(value + 1);
                ruleCalculationMethod(yaxis, view);
                break;
            case GRAPH_TYPE:
                yaxis.setGraphType(value + 1);
                break;
            default:
                break;
        }
    }

    private void ruleCalculationMethod(ObjectYaxis yaxis, View view) {
        RelativeLayout relAccumulation = view.findViewById(R.id.relAccumulation);
        SwitchCompat switchAccumulation = view.findViewById(R.id.switchAccumulation);
        EditText edReferenceValue = view.findViewById(R.id.edReferenceValue);
        if (yaxis.getAggregateType() != AGGREGATE_METHOD_DIFFERENCE) {
            relAccumulation.setVisibility(View.VISIBLE);
            yaxis.setIntegration(true);
            yaxis.setBasisValue(Integer.parseInt(edReferenceValue.getText().toString()));
        } else {
            relAccumulation.setVisibility(View.GONE);
            switchAccumulation.setChecked(false);
            yaxis.setIntegration(false);
            yaxis.setBasisValue(0);
        }
    }

}
