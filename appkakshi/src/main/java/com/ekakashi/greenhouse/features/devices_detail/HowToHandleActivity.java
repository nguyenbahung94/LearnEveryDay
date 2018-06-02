package com.ekakashi.greenhouse.features.devices_detail;

import android.os.Bundle;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;

public class HowToHandleActivity extends BaseActivity {
    private MyToolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_handle);
        myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarRight(Utils.Name.TOOLBAR_RIGHT_TEXT, "Close");
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, "How To Handle", "");
        myToolbar.setToolbarListener(new MyToolbar.ToolbarListener() {
            @Override
            public void onToolbarLeftListener() {

            }

            @Override
            public void onToolbarRightListener() {
                onBackPressed();
            }
        });
    }
}
