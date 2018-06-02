package com.ekakashi.greenhouse.features.timeline_filter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FilterActivity extends BaseActivity implements View.OnClickListener {

    public static final String FILTER_MODEL = "FILTER_MODEL";
    public static final String FILTER_PLACE = "FILTER_PLACE";
    public static final String FILTER_MEMBER = "FILTER_MEMBER";

    private TextView tvFrom;
    private TextView tvTo;
    private TextView tvPlaceNumberSelect;
    private TextView tvCropNumberSelect;
    private TextView tvMemberNumberSelect;

    private ImageView imgFilterNotification;
    private ImageView imgFilterAdvice;
    private ImageView imgFilterDiary;
    private ImageView imgFilterRemote;

    private Calendar calendar;
    private FilterModel filterModel = new FilterModel();
    private ArrayList<Integer> listQuickFilter = new ArrayList<>();

    private int REQUEST_PLACE = 11;
    private int REQUEST_MEMBER = 22;
    private int REQUEST_CROP = 33;
    private int FROM = 0;
    private int TO = 1;

    private boolean filterNotification = false;
    private boolean filterAdvice = false;
    private boolean filterDiary = false;
    private boolean filterRemote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        addToolbar();
        addControls();

        FilterModel model = this.getIntent().getParcelableExtra(FILTER_MODEL);
        if (model != null) {
            filterModel = model;
            setPlaceText();
            setMemberText();
            setFromToDate();
            setFilterType();

        }
    }

    private void addControls() {
        tvFrom = findViewById(R.id.tvFilterFrom);
        tvFrom.setTag(FROM);
        tvTo = findViewById(R.id.tvFilterTo);
        tvTo.setTag(TO);
        tvPlaceNumberSelect = findViewById(R.id.tvPlaceNumberSelect);
        tvCropNumberSelect = findViewById(R.id.tvCropNumberSelect);
        tvMemberNumberSelect = findViewById(R.id.tvMemberNumberSelect);

        imgFilterNotification = findViewById(R.id.imgFilterNotification);
        imgFilterAdvice = findViewById(R.id.imgFilterAdvice);
        imgFilterDiary = findViewById(R.id.imgFilterDiary);
        imgFilterRemote = findViewById(R.id.imgFilterRemote);

        calendar = Calendar.getInstance();

        findViewById(R.id.layoutNotification).setOnClickListener(this);
        findViewById(R.id.layoutAdvice).setOnClickListener(this);
        findViewById(R.id.layoutDiary).setOnClickListener(this);
        findViewById(R.id.layoutRemote).setOnClickListener(this);
        findViewById(R.id.layoutPlace).setOnClickListener(this);
        findViewById(R.id.layoutCrop).setOnClickListener(this);
        findViewById(R.id.layoutMember).setOnClickListener(this);
        findViewById(R.id.layoutFrom).setOnClickListener(this);
        findViewById(R.id.layoutTo).setOnClickListener(this);
        findViewById(R.id.btnFilterApply).setOnClickListener(this);
        findViewById(R.id.btnFilterReset).setOnClickListener(this);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_TEXT_CLOSE);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.filter), "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FilterActivity.this, FilterSelectActivity.class);
        switch (v.getId()) {
            case R.id.layoutPlace:
                intent.putExtra(Utils.Constant.TIMELINE_FILTER_TYPE, FILTER_PLACE);
                intent.putExtra(Utils.Constant.TIMELINE_FILTER_IDS, filterModel.getSelectedPlaceIds());
                startActivityForResult(intent, REQUEST_PLACE);
                break;
            case R.id.layoutCrop:
                /*intent.putExtra(Utils.Constant.TIMELINE_FILTER_TYPE, FILTER_PLACE);
                startActivityForResult(intent, REQUEST_CROP);*/
                break;
            case R.id.layoutMember:
                intent.putExtra(Utils.Constant.TIMELINE_FILTER_TYPE, FILTER_MEMBER);
                intent.putExtra(Utils.Constant.TIMELINE_FILTER_IDS, filterModel.getSelectedMemberIds());
                startActivityForResult(intent, REQUEST_MEMBER);
                break;
            case R.id.layoutNotification:
                if (!filterNotification) {
                    filterNotification = true;
                    imgFilterNotification.setImageResource(R.drawable.ic_checked);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_NOTIFICATION, true);
                } else {
                    filterNotification = false;
                    imgFilterNotification.setImageResource(R.drawable.ic_uncheck);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_NOTIFICATION, false);
                }
                break;
            case R.id.layoutAdvice:
                if (!filterAdvice) {
                    filterAdvice = true;
                    imgFilterAdvice.setImageResource(R.drawable.ic_checked);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_ADVICE, true);
                } else {
                    filterAdvice = false;
                    imgFilterAdvice.setImageResource(R.drawable.ic_uncheck);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_ADVICE, false);
                }
                break;
            case R.id.layoutDiary:
                if (!filterDiary) {
                    filterDiary = true;
                    imgFilterDiary.setImageResource(R.drawable.ic_checked);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_DIARY, true);
                } else {
                    filterDiary = false;
                    imgFilterDiary.setImageResource(R.drawable.ic_uncheck);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_DIARY, false);
                }
                break;
            case R.id.layoutRemote:
                if (!filterRemote) {
                    filterRemote = true;
                    imgFilterRemote.setImageResource(R.drawable.ic_checked);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_REMOTE_CONTROL, true);
                } else {
                    filterRemote = false;
                    imgFilterRemote.setImageResource(R.drawable.ic_uncheck);
                    quickFilter(Utils.Constant.TIMELINE_TYPE_REMOTE_CONTROL, false);
                }
                break;
            case R.id.layoutFrom:
                showDatePickerDialog(tvFrom);
                break;
            case R.id.layoutTo:
                showDatePickerDialog(tvTo);
                break;
            case R.id.btnFilterApply:
                applyFilter();
                break;
            case R.id.btnFilterReset:
                filterModel = new FilterModel();

                imgFilterNotification.setImageResource(R.drawable.ic_uncheck);
                imgFilterAdvice.setImageResource(R.drawable.ic_uncheck);
                imgFilterDiary.setImageResource(R.drawable.ic_uncheck);
                imgFilterRemote.setImageResource(R.drawable.ic_uncheck);
                tvMemberNumberSelect.setText("");
                tvPlaceNumberSelect.setText("");

                tvFrom.setText(getResources().getString(R.string.format_date_time));
                tvTo.setText(getResources().getString(R.string.format_date_time));
                break;
            default:
                break;
        }
    }


    private void quickFilter(int type, boolean flag) {
        if (flag) {
            listQuickFilter.add(type);
        } else {
            for (int i = 0; i < listQuickFilter.size(); i++) {
                if (listQuickFilter.get(i) == type)
                    listQuickFilter.remove(i);
            }
        }
        filterModel.setTypeList(listQuickFilter);
    }

    private void applyFilter() {
        if (isToDateGreaterFromDate(filterModel.getFromDay(), filterModel.getToDay())) {
            Intent intent = new Intent();
            intent.putExtra(Utils.Constant.TIMELINE_FILTER_MODEL, filterModel);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showSimpleMessage(getString(R.string.validate_date));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.putExtra(Utils.Constant.TIMELINE_FILTER_MODEL, filterModel);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FilterModel result = data.getParcelableExtra(Utils.Constant.TIMELINE_FILTER_MODEL);
            if (result != null) {
                if (requestCode == REQUEST_PLACE) {
                    filterModel.setPlaceList(result.getPlaceList());
                    setPlaceText();
                }
                if (requestCode == REQUEST_MEMBER) {
                    filterModel.setMemberList(result.getMemberList());
                    setMemberText();
                }
            }
        }
    }

    private void setPlaceText() {
        ArrayList<FilterField> placeList = filterModel.getPlaceList();
        if (placeList == null || placeList.isEmpty()) {
            tvPlaceNumberSelect.setText("");
        } else {
            tvPlaceNumberSelect.setText(getQuantitySelectedItems(placeList.size()));
        }
    }

    private void setMemberText() {
        ArrayList<FilterMember> memberList = filterModel.getMemberList();
        if (memberList == null || memberList.isEmpty()) {
            tvMemberNumberSelect.setText("");
        } else {
            tvMemberNumberSelect.setText(getQuantitySelectedItems(memberList.size()));
        }
    }

    private void setFromToDate() {
        if (filterModel.getFromDay() != null && !filterModel.getFromDay().isEmpty()) {
            tvFrom.setText(filterModel.getFromDay());
        }
        if (filterModel.getToDay() != null && !filterModel.getToDay().isEmpty()) {
            tvTo.setText(filterModel.getToDay());
        }
    }

    private void setFilterType() {
        listQuickFilter = filterModel.getTypeList();
        for (int type : listQuickFilter) {
            switch (type) {
                case Utils.Constant.TIMELINE_TYPE_NOTIFICATION:
                    filterNotification = true;
                    imgFilterNotification.setImageResource(R.drawable.ic_checked);
                    break;
                case Utils.Constant.TIMELINE_TYPE_ADVICE:
                    filterAdvice = true;
                    imgFilterAdvice.setImageResource(R.drawable.ic_checked);
                    break;
                case Utils.Constant.TIMELINE_TYPE_DIARY:
                    filterDiary = true;
                    imgFilterDiary.setImageResource(R.drawable.ic_checked);
                    break;
                case Utils.Constant.TIMELINE_TYPE_REMOTE_CONTROL:
                    filterRemote = true;
                    imgFilterRemote.setImageResource(R.drawable.ic_checked);
                    break;
                default:
                    break;
            }
        }
    }

    private String getQuantitySelectedItems(int count) {
        return count > 1 ? count + " items selected" : count + " item selected";
    }

    private void showDatePickerDialog(final TextView textView) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String dateStringUTC = DateTimeNow.parseTimeToUTCDateString(year, month, dayOfMonth,
                        0, 0, 0);
                Date dateUTC = DateTimeNow.parseStringToDateLocal(dateStringUTC, DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ);
                String currentDate = DateTimeNow.parseDateLocalToString(dateUTC, getString(R.string.format_date_time));

                textView.setText(currentDate);
                int tag = (int) textView.getTag();
                if (tag == FROM) {
                    filterModel.setFromDay(currentDate);
                } else if (tag == TO) {
                    filterModel.setToDay(currentDate);

                }
                calendar.set(year, month, dayOfMonth);
            }
        };
        Utils.createDatePicker(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH), listener).show();
    }

    private boolean isToDateGreaterFromDate(String fromDate, String toDate) {
        if (fromDate != null && toDate != null) {
            Date from = DateTimeNow.parseStringToDateLocal(fromDate, getString(R.string.format_date_time));
            Date to = DateTimeNow.parseStringToDateLocal(toDate, getString(R.string.format_date_time));
            if (from != null && to != null) {
                return !from.after(to);
            }
        }
        return true;
    }
}


