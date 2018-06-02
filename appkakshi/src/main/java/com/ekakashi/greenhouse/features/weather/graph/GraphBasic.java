package com.ekakashi.greenhouse.features.weather.graph;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumPeriod;
import com.ekakashi.greenhouse.features.weather.object_weather.EnumTemplateType;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectGraph;
import com.ekakashi.greenhouse.features.weather.period.PeriodActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nquochuy on 3/9/2018.
 */

public class GraphBasic implements View.OnClickListener {

    private LinearLayout layout;
    private EditText edAddTag;
    private EditText edGraphName;
    private TextView tvStartStage;
    private TextView tvEndStage;
    private TextView tvStartDay;
    private TextView tvEndDay;
    private TextView tvPeriod;
    private SwitchCompat switchLimitStage;
    private RelativeLayout relStartStage;
    private RelativeLayout relEndStage;
    private RelativeLayout relStartDay;
    private RelativeLayout relEndDay;

    private final int START_STAGE = 1;
    private final int END_STAGE = 2;
    private final int START_DAY = 3;
    private final int END_DAY = 4;

    private AddGraphActivity mActivity;
    private DummyGraph mTemplate;
    private Calendar mCalendar;

    public GraphBasic(AddGraphActivity mActivity, LinearLayout layout, DummyGraph template) {
        this.mActivity = mActivity;
        this.layout = layout;
        this.mTemplate = template;
        addControls();
        addEvents();
        initObject();
    }

    private void addControls() {
        tvStartStage = layout.findViewById(R.id.tvStartStage);
        tvEndStage = layout.findViewById(R.id.tvEndStage);
        tvStartDay = layout.findViewById(R.id.tvStartDay);
        tvEndDay = layout.findViewById(R.id.tvEndDay);
        tvPeriod = layout.findViewById(R.id.tvPeriod);
        edAddTag = layout.findViewById(R.id.edAddTag);
        edGraphName = layout.findViewById(R.id.edGraphName);
        switchLimitStage = layout.findViewById(R.id.switchLimitStage);
        relStartStage = layout.findViewById(R.id.relStartStage);
        relEndStage = layout.findViewById(R.id.relEndStage);
        relStartDay = layout.findViewById(R.id.relStartDay);
        relEndDay = layout.findViewById(R.id.relEndDay);

        mCalendar = Calendar.getInstance();
        tvStartDay.setTag(START_DAY);
        tvEndDay.setTag(END_DAY);
    }

    private void addEvents() {
        layout.findViewById(R.id.layoutGraphName).setOnClickListener(this);
        layout.findViewById(R.id.layoutPeriod).setOnClickListener(this);
        relStartStage.setOnClickListener(this);
        relEndStage.setOnClickListener(this);
        relStartDay.setOnClickListener(this);
        relEndDay.setOnClickListener(this);

        edAddTag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(edAddTag.getText().toString())) {
                    addTag(edAddTag.getText().toString());
                    edAddTag.setText("");
                }
                return false;
            }
        });
        switchLimitStage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relStartStage.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                relEndStage.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initObject() {
        if (mTemplate != null) {
            edGraphName.setText(mTemplate.getName());
            if (mTemplate.getTags() != null) {
                for (String tag : mTemplate.getTags()) {
                    addTag(tag);
                }
            }
            if (mTemplate.getGraph() != null) {
                ObjectGraph graph = mTemplate.getGraph();
                tvStartDay.setText(DateTimeNow.parseMillisecondToFormatDate(graph.getStartDate(), mActivity.getString(R.string.format_date_time)));
                tvEndDay.setText(DateTimeNow.parseMillisecondToFormatDate(graph.getEndDate(), mActivity.getString(R.string.format_date_time)));
                tvPeriod.setText(parseEnumToArray(EnumPeriod.values())[graph.getPreriod()]);
                switchLimitStage.setChecked(graph.isDisplayStage());
            }
        }
    }

    private void addTag(String tag) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_graph_basic_tag, null);
        final ViewGroup vgTag = layout.findViewById(R.id.layoutTag);

        TextView tvGraphTag = view.findViewById(R.id.tvGraphTag);
        tvGraphTag.setText(tag);

        ImageView imgTagClear = view.findViewById(R.id.imgTagClear);
        imgTagClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vgTag.removeView(view);
            }
        });

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, dp2px(10), 0);
        vgTag.addView(view, params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutPeriod:
                mActivity.startActivity(new Intent(mActivity, PeriodActivity.class));
                break;
            case R.id.relStartStage:
                showDialogListStage(START_STAGE);
                break;
            case R.id.relEndStage:
                showDialogListStage(END_STAGE);
                break;
            case R.id.relStartDay:
                showDatePickerDialog(tvStartDay);
                break;
            case R.id.relEndDay:
                showDatePickerDialog(tvEndDay);
                break;
            default:
                break;
        }
    }

    private void showDialogListStage(final int title) {
        final String[] itemList = Arrays.toString(EnumTemplateType.values())
                .replaceAll("^.|.$", "").split(", ");
        final BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_bottom_sheet_graph, null);
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);

        tvTitle.setText(title == START_STAGE ? mActivity.getString(R.string.graph_start_stage) : mActivity.getString(R.string.graph_end_stage));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(itemList.length - 1);
        numberPicker.setDisplayedValues(itemList);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i].equalsIgnoreCase(title == START_STAGE ?
                    tvStartStage.getText().toString() : tvEndStage.getText().toString())) {
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
                if (title == START_STAGE) {
                    tvStartStage.setText(itemList[numberPicker.getValue()]);
                } else {
                    tvEndStage.setText(itemList[numberPicker.getValue()]);
                }
                dialog.cancel();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void showDatePickerDialog(final TextView textView) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateStringUTC = DateTimeNow.parseTimeToUTCDateString(year, month, dayOfMonth,
                        0, 0, 0);
                Date dateUTC = DateTimeNow.parseStringToDateLocal(dateStringUTC, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ);
                String currentDate = DateTimeNow.parseDateLocalToString(dateUTC, mActivity.getString(R.string.format_date_time));

                textView.setText(currentDate);
/*                int tag = (int) textView.getTag();
                if (tag == START_DAY) {
                    //TODO: Handle set object
                    filterModel.setFromDay(currentDate);
                } else if (tag == END_DAY) {
                    filterModel.setToDay(currentDate);
                }*/
                mCalendar.set(year, month, dayOfMonth);
            }
        };
        Utils.createDatePicker(mActivity, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH)
                , mCalendar.get(Calendar.DAY_OF_MONTH), listener).show();
    }

    private int dp2px(int dp) {
        float scaleValue = mActivity.getResources().getDisplayMetrics().density;
        return (int) (dp * scaleValue + 0.5f);
    }

    private String[] parseEnumToArray(Object[] enumClass) {
        return Arrays.toString(enumClass)
                .replaceAll("^.|.$", "").split(", ");
    }
}
