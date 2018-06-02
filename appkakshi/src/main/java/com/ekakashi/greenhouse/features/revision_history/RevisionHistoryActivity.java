package com.ekakashi.greenhouse.features.revision_history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectEkUser;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;
import com.ekakashi.greenhouse.database.model_server_response.RevisionHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 1/25/2018.
 */

public class RevisionHistoryActivity extends BaseActivity {
    private RecyclerView rvHistory;
    private List<RevisionHistory> mHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_history);
        mHistoryList.clear();
        initView();
        addToolbar();
        if (getIntent() != null) {
            ObjectRecipe objectRecipe = App.appRecipe;
            if (objectRecipe != null && objectRecipe.getRevisionHistoryList() != null) {
                mHistoryList.addAll(objectRecipe.getRevisionHistoryList());
                mHistoryList.add(new RevisionHistory(new ObjectEkUser(objectRecipe.getEkUser().getNickName())));
            }
        }


        HistoryAdapter mHistoryAdapter = new HistoryAdapter(this, mHistoryList);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(mHistoryAdapter);
    }

    private void initView() {
        rvHistory = findViewById(R.id.rvHistory);
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.revision_history), "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() { onBackPressed();
            }

            @Override
            public void onToolbarRightListener() {
                onBackPressed();
            }
        });
    }
}
