package com.ekakashi.greenhouse.features.devices_detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecord;

import java.util.ArrayList;
import java.util.List;

public class OperationRecordActivity extends BaseActivity {
    private MyToolbar myToolbar;
    private RecyclerView mRecycleView;
    private List<ObjectRecord> objectRecordList = new ArrayList<>();
    private RecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_record);
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON, "");
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_BOTTOM, "Operation Record", "ventilation window open/close motor");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {
                onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {

            }
        });
        addData();
        mRecycleView = findViewById(R.id.mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(OperationRecordActivity.this));
        recordAdapter = new RecordAdapter(OperationRecordActivity.this, objectRecordList);
        mRecycleView.setAdapter(recordAdapter);
    }

    private void addData() {
        objectRecordList.add(new ObjectRecord("", "1234", "123456789", "OPEN", 50));
        objectRecordList.add(new ObjectRecord("", "125", "123456789", "OPEN", 50));
        objectRecordList.add(new ObjectRecord("", "1236", "123456789", "OPEN", 50));
        objectRecordList.add(new ObjectRecord("", "1237", "123456789", "OPEN", 50));
        objectRecordList.add(new ObjectRecord("", "1238", "123456789", "OPEN", 50));
        objectRecordList.add(new ObjectRecord("", "1239", "123456789", "OPEN", 50));
    }
}
