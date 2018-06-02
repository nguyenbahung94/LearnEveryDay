package com.ekakashi.greenhouse.features.system_news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;

public class NotificationDetailActivity extends BaseActivity {

    private TextView tvDate;
    private TextView tvTitle;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        addToolbar();
        addControls();
    }

    private void addControls() {
        tvDate = findViewById(R.id.tvDate);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);

        Intent intent = getIntent();
        SystemNews.Data systemNews = intent.getParcelableExtra(SystemNewsActivity.SYSTEM_NEWS);
        if (systemNews != null) {
            tvDate.setText(DateTimeNow.parseDateStringToLocalDateString(systemNews.getCreatedAt(),
                    DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, getString(R.string.format_date_time)));
            tvTitle.setText(systemNews.getOutline());
            tvContent.setText(systemNews.getDetail());
        }
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.notification_detail), "");
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
}
