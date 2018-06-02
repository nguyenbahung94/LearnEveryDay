package com.ekakashi.greenhouse.features.weather.period;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;

import java.util.ArrayList;

public class PeriodActivity extends BaseActivity {

    private RecyclerView rvPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);
        addControls();
        addToolbar();
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY,"Period","");
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

    private void addControls() {
        rvPeriod = findViewById(R.id.rvPeriod);

        ArrayList<Period> list = new ArrayList<>();
        list.add(new Period(getString(R.string.period_latest_title),getString(R.string.period_latest_content)));
        list.add(new Period(getString(R.string.period_today_title),getString(R.string.period_today_content)));
        list.add(new Period(getString(R.string.period_yesterday_title),getString(R.string.period_yesterday_content)));
        list.add(new Period(getString(R.string.period_last7days_title),getString(R.string.period_last7days_content)));
        list.add(new Period(getString(R.string.period_last30days_title),getString(R.string.period_last30days_content)));
        list.add(new Period(getString(R.string.period_last_month_title),getString(R.string.period_last_month_content)));
        list.add(new Period(getString(R.string.period_specify_date_title),getString(R.string.period_specify_date_content)));
        list.add(new Period(getString(R.string.period_specify_stage_title),getString(R.string.period_specify_stage_content)));

        PeriodAdapter adapter = new PeriodAdapter(list,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvPeriod.setLayoutManager(layoutManager);
        rvPeriod.setItemAnimator(new DefaultItemAnimator());
        rvPeriod.setAdapter(adapter);
    }
}
