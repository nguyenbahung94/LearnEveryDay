package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCondition;
import com.ekakashi.greenhouse.database.model_server_response.ObjectGrowth;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.features.recipe.rule.add_rule.constant.EnumRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;

public class AddConditionActivity extends BaseActivity implements View.OnClickListener, AddConditionInterface.View {

    private RelativeLayout layoutTo;
    private RelativeLayout layoutCounting;
    private RelativeLayout layoutMeasurementItem;
    private RelativeLayout layoutDeterminationValue;
    private RelativeLayout layoutDeterminationStandard;
    private TextView tvDevice;
    private TextView tvStartStage;
    private TextView tvItem;
    private TextView tvCounting;
    private TextView tvUnit;
    private EditText edtDeterminationValue;
    private TextView tvCalculator;
    private TextView tvAddInformation;
    private TextView counting;
    private TextView tvDeterminationValue;

    private ObjectRecipe mRecipe;
    private int ruleType;
    private int countingMethod = 0;
    private int deviceValue = 0;//device value
    private int determinationMethod = 0;//determination standard
    private int determinationTarget = 0;//Stage Id
    private int deviceType = 1;//measurement device
    private int determinationValue;
    private String[] stageList;
    private AddConditionPresenter addConditionPresenter;
    private List<ObjectGrowth> mStages = new ArrayList<>();
    private String stageName;
    private String aggregationMethod;
    private String measurementItem;
    private String determinationStandard;
    private ObjectCondition mConditionDetail;
    private boolean viewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_condition);
        initView();
        addToolbar();
        setUpUI();
    }

    private void initView() {
        tvDevice = findViewById(R.id.tvDevice);
        tvStartStage = findViewById(R.id.startStage);
        tvItem = findViewById(R.id.tvItem);
        tvCounting = findViewById(R.id.tvCounting);
        edtDeterminationValue = findViewById(R.id.edtDeterminationValue);
        tvCalculator = findViewById(R.id.tvCalculator);
        tvUnit = findViewById(R.id.tvUnit);
        tvAddInformation = findViewById(R.id.tvTo);
        tvDeterminationValue = findViewById(R.id.tvDeterminationValue);
        counting = findViewById(R.id.counting);
        layoutCounting = findViewById(R.id.layoutCounting);
        layoutTo = findViewById(R.id.layoutTo);
        RelativeLayout layoutDevice = findViewById(R.id.layoutDevice);
        layoutMeasurementItem = findViewById(R.id.layoutItem);
        layoutDeterminationValue = findViewById(R.id.layoutFrom);
        layoutDeterminationStandard = findViewById(R.id.layoutCalculator);

        layoutCounting.setOnClickListener(this);
        layoutTo.setOnClickListener(this);
        layoutDevice.setOnClickListener(this);
        layoutMeasurementItem.setOnClickListener(this);
        layoutDeterminationValue.setOnClickListener(this);
        layoutDeterminationStandard.setOnClickListener(this);

        measurementItem = Utils.getMeasurementItem(this, ruleType, EnumRule.ONE_KM_MESH)[0];
        tvUnit.setText(Utils.getUnitOfMeasurementItem(this, measurementItem));
        tvItem.setText(measurementItem);

        if (getIntent() != null) {
            ruleType = getIntent().getIntExtra(Utils.Name.TYPE, 0);
            mRecipe = App.appRecipe;
            viewDetail = getIntent().getBooleanExtra(Utils.Name.VIEW_DETAIL, false);
            mConditionDetail = App.appCondition;
        }

        if (ruleType == EnumRule.MOMENTARY) {
            countingMethod = -1;
            determinationStandard = Utils.getDeterminationStandardList(this)[determinationMethod];
            tvCalculator.setText(determinationStandard);
        } else {
            countingMethod = 0;
            determinationMethod = 0;
            aggregationMethod = Utils.getCountingMethod(this)[countingMethod];
            tvCounting.setText(aggregationMethod);
            determinationStandard = Utils.getDeterStandardStringForAggregation(this, countingMethod);
            tvCalculator.setText(determinationStandard);
            if (mRecipe != null) {
                if (mRecipe.getEkFields() != null && !mRecipe.getEkFields().isEmpty()) {
                    if (mRecipe.getEkFields().get(0) != null) {
                        determinationTarget = mRecipe.getEkFields().get(0).getCurrentStage().getId();
                        stageName = mRecipe.getEkFields().get(0).getCurrentStage().getName();
                        tvAddInformation.setText(stageName);
                    }
                }

            }
        }
        edtDeterminationValue.setFilters(new InputFilter[]{new InputFilterMinMax(deviceValue)});
        addConditionPresenter = new AddConditionPresenter(this);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        if (viewDetail) {
            ruleType = getIntent().getIntExtra(Utils.Name.RULE, 0);
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.view_condition_information), Utils.getRuleType(this, ruleType));
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, null);
        } else {
            myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CANCEL);
            myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, getString(R.string.add_condition), Utils.getRuleType(this, ruleType));
            myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, getString(R.string.toolbar_save));
        }

        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                if (edtDeterminationValue.getText().toString().isEmpty()) {
                    edtDeterminationValue.setError(getString(R.string.error_field_required));
                    return;
                }
                determinationValue = Integer.parseInt(edtDeterminationValue.getText().toString());
                ObjectCondition condition = new ObjectCondition();
                condition.setName("Condition");
                if (ruleType == EnumRule.AGGREGATION) {
                    condition.setAdditionalInformation(determinationTarget);
                    condition.setStageName(stageName);
                    condition.setAggregationMethod(aggregationMethod);
                    condition.setCountingMethod(countingMethod + 1);
                }
                condition.setDeterminationStandard(determinationMethod + 2);//2: greater than; 3: less than
                condition.setDeterminationStandardString(determinationStandard);
                condition.setDeterminationValue(determinationValue);
                condition.setDeviceType(deviceType);
                condition.setMeasurementItem(deviceValue + 1);
                condition.setMeasurementItemString(measurementItem);
                condition.setContent(Utils.createContentFromAddCondition(AddConditionActivity.this, ruleType, condition));
//                Intent data = new Intent();
//                data.putExtra(Utils.Name.CONDITION, condition);
                App.appCondition = condition;
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void setUpUI() {
        switch (ruleType) {
            case EnumRule.MOMENTARY:
                layoutTo.setVisibility(GONE);
                layoutCounting.setVisibility(GONE);
                counting.setVisibility(GONE);
                tvStartStage.setVisibility(GONE);

                if (viewDetail) {
                    layoutDeterminationStandard.setEnabled(false);
                    layoutMeasurementItem.setEnabled(false);
                    layoutDeterminationValue.setEnabled(false);
                    edtDeterminationValue.setEnabled(false);
                    edtDeterminationValue.setVisibility(View.GONE);

                    tvItem.setText(Utils.getSpecificMeasurementItem(this, ruleType, mConditionDetail.getMeasurementItem() - 1));
                    tvUnit.setText(Utils.getUnitOfMeasurementItem(this, Utils.getSpecificMeasurementItem(this, ruleType, mConditionDetail.getMeasurementItem() - 1)));
                    tvCalculator.setText(Utils.getDeterminationStandardForMomentary(this, mConditionDetail.getDeterminationStandard()));
                    String determinationValue = String.valueOf(mConditionDetail.getDeterminationValue());
//                    edtDeterminationValue.setText(determinationValue);
                    tvDeterminationValue.setText(determinationValue);
                }
                break;
            case EnumRule.AGGREGATION:
                layoutTo.setVisibility(View.VISIBLE);
                layoutCounting.setVisibility(View.VISIBLE);
                counting.setVisibility(View.VISIBLE);

                if (viewDetail) {
                    layoutTo.setEnabled(false);
                    layoutCounting.setEnabled(false);
                    layoutDeterminationStandard.setEnabled(false);
                    layoutMeasurementItem.setEnabled(false);
                    layoutDeterminationValue.setEnabled(false);
                    edtDeterminationValue.setEnabled(false);
                    edtDeterminationValue.setVisibility(View.GONE);

                    tvCounting.setText(Utils.getCountingMethod(this)[0]);
                    tvItem.setText(Utils.getSpecificMeasurementItem(this, ruleType, mConditionDetail.getMeasurementItem() - 1));
                    tvUnit.setText(Utils.getUnitOfMeasurementItem(this, Utils.getSpecificMeasurementItem(this, ruleType, mConditionDetail.getMeasurementItem() - 1)));
                    tvCalculator.setText(Utils.getDeterStandardStringForAggregation(this, mConditionDetail.getCountingMethod() - 1));
                    tvDeterminationValue.setText(String.valueOf(mConditionDetail.getDeterminationValue()));
                    tvAddInformation.setText(getStageInCondition());
                } else {
                    if (isNetworkOffline()) {
                        return;
                    }
                    showLoadingDialog(getString(R.string.message_please_wait));
                    addConditionPresenter.getRecipeById(mRecipe.getId());
                }
                break;
            case EnumRule.CONTROL_DEVICE:
                break;
            default:
                break;
        }
    }

    private String getStageInCondition() {
        if (mRecipe.getStages() != null && !mRecipe.getStages().isEmpty()) {
            for (ObjectGrowth stage : mRecipe.getStages()) {
                if (stage.getId() != null && mConditionDetail.getAdditionalInformation() != null) {
                    if (stage.getId().intValue() == mConditionDetail.getAdditionalInformation().intValue()) {
                        return stage.getName();
                    }
                }
            }
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutDevice:
                //TODO click layoutDevice, call dialog
                break;
            case R.id.layoutItem:
                //TODO show Bottom Dialog
                if (!viewDetail) {
                    openMeasurementItemDialog();
                }
                break;
            case R.id.layoutCounting:
                if (!viewDetail) {
                    openCountingMethodDialog();
                }
                break;
            case R.id.layoutCalculator://Determination Standard
                if (!viewDetail) {
                    if (ruleType == EnumRule.MOMENTARY) {
                        openDeterminationStandardDialog();
                    }
                }
                break;
            case R.id.layoutTo:
                if (!viewDetail) {
                    if (ruleType == EnumRule.AGGREGATION) {
                        openAdditionalInformationDialog();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void openAdditionalInformationDialog() {
        if (stageList != null && stageList.length != 0) {
            final BottomSheetDialog dialog = new BottomSheetDialog(this);
            LayoutInflater inflater = this.getLayoutInflater();
            @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_custom_bottom_picker, null);
            final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(stageList.length - 1);
            numberPicker.setDisplayedValues(stageList);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            //set wrap true or false, try it you will know the difference
            numberPicker.setWrapSelectorWheel(false);
            dialog.setContentView(dialogView);
            dialog.show();

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    for (ObjectGrowth stage : mStages) {
                        if (stage.getName().equalsIgnoreCase(stageList[newVal])) {
                            determinationTarget = stage.getId();
                        }
                    }
                    stageName = stageList[newVal];
                    tvAddInformation.setText(stageList[newVal]);
                }
            });

            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            tvTitle.setText(R.string.additional_information);
            dialogView.findViewById(R.id.tvExecute).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void openDeterminationStandardDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_custom_bottom_picker, null);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(Utils.getDeterminationStandardList(this).length - 1);
        numberPicker.setDisplayedValues(Utils.getDeterminationStandardList(this));
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //set wrap true or false, try it you will know the difference
        numberPicker.setWrapSelectorWheel(false);
        dialog.setContentView(dialogView);
        dialog.show();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                determinationMethod = newVal;
                determinationStandard = Utils.getDeterminationStandardList(AddConditionActivity.this)[newVal];
                tvCalculator.setText(determinationStandard);
            }
        });

        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.determination_standard);
        dialogView.findViewById(R.id.tvExecute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void openCountingMethodDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_custom_bottom_picker, null);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(Utils.getCountingMethod(this).length - 1);
        numberPicker.setDisplayedValues(Utils.getCountingMethod(this));
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //set wrap true or false, try it you will know the difference
        numberPicker.setWrapSelectorWheel(false);
        dialog.setContentView(dialogView);
        dialog.show();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                countingMethod = newVal;
                aggregationMethod = Utils.getCountingMethod(AddConditionActivity.this)[newVal];
                tvCalculator.setText(Utils.getDeterStandardStringForAggregation(AddConditionActivity.this, countingMethod));
                tvCounting.setText(aggregationMethod);
            }
        });

        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.counting_method));
        dialogView.findViewById(R.id.tvExecute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void openMeasurementItemDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_custom_bottom_picker, null);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(Utils.getMeasurementItem(this, ruleType, EnumRule.ONE_KM_MESH).length - 1);
        numberPicker.setDisplayedValues(Utils.getMeasurementItem(this, ruleType, EnumRule.ONE_KM_MESH));
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //set wrap true or false, try it you will know the difference
        numberPicker.setWrapSelectorWheel(false);
        dialog.setContentView(dialogView);
        dialog.show();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                deviceValue = newVal;
                measurementItem = Utils.getMeasurementItem(AddConditionActivity.this, ruleType, EnumRule.ONE_KM_MESH)[newVal];
                edtDeterminationValue.setFilters(new InputFilter[]{new InputFilterMinMax(deviceValue)});
                tvUnit.setText(Utils.getUnitOfMeasurementItem(AddConditionActivity.this, measurementItem));
                tvItem.setText(measurementItem);
            }
        });

        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.measurement_item));
        dialogView.findViewById(R.id.tvExecute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void success(ObjectRecipe recipe) {
        hideLoadingDialog();
        if (recipe != null) {
            int temptStageId = mRecipe.getCurrentStageId();
            mRecipe = recipe;
            if (recipe.getEkFields() != null && !recipe.getEkFields().isEmpty()) {
                if (recipe.getEkFields().get(0) != null) {
                    if (recipe.getEkFields().get(0).getCurrentStage() != null) {
                        mRecipe.setCurrentStageId(recipe.getEkFields().get(0).getCurrentStage().getId());
                    } else {
                        mRecipe.setCurrentStageId(temptStageId);
                    }
                } else {
                    mRecipe.setCurrentStageId(temptStageId);
                }
            } else {
                mRecipe.setCurrentStageId(temptStageId);
            }

            if (recipe.getStages() != null && !recipe.getStages().isEmpty()) {
                //Sort Stages, if BE return random stages
                Collections.sort(mRecipe.getStages(), new Comparator<ObjectGrowth>() {
                    @Override
                    public int compare(ObjectGrowth o1, ObjectGrowth o2) {
                        return o1.getOrderPos() - o2.getOrderPos();
                    }
                });
                //Get previous stage or current stage
                int getCurrentStagePos, j, size = mRecipe.getStages().size();
                for (getCurrentStagePos = 0; getCurrentStagePos < size; getCurrentStagePos++) {
                    if (mRecipe.getCurrentStageId() == mRecipe.getStages().get(getCurrentStagePos).getId()) {
                        stageList = new String[getCurrentStagePos + 1];
                        for (j = 0; j < getCurrentStagePos + 1; j++) {
                            mStages.add(mRecipe.getStages().get(j));
                            stageList[j] = mRecipe.getStages().get(j).getName();
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void failed(String s) {
        hideLoadingDialog();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }

    @Override
    protected void onPause() {
        hideLoadingDialog();
        super.onPause();
    }
}
