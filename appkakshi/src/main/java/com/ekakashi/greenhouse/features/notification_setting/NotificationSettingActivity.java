package com.ekakashi.greenhouse.features.notification_setting;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.BaseActivity;
import com.ekakashi.greenhouse.common.MyToolbar;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.NotificationSettingRequest;
import com.ekakashi.greenhouse.database.model_server_response.NotificationSettingReponse;

public class NotificationSettingActivity extends BaseActivity implements NotificationSettingInterface.View,
        CompoundButton.OnCheckedChangeListener {
    private SwitchCompat swBySomeone;
    private SwitchCompat swAtDevice;
    private SwitchCompat swNewFunction;

    private boolean isFirstChange = true;
    private NotificationSettingInterface.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofication_setting);

        addToolbar();
        addControls();
        addEvents();
        initPresenter();
    }

    private void addToolbar() {
        MyToolbar myToolbar = new MyToolbar(mToolbar);
        myToolbar.setUpToolbarLeft(Utils.Name.TOOLBAR_LEFT_BACK_BUTTON);
        myToolbar.setUpTextCenter(Utils.Name.TOOLBAR_CENTER_TEXT_ONLY, getString(R.string.settings_ui_notification), "");
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
        swBySomeone = findViewById(R.id.switchBySomeone);
        swAtDevice = findViewById(R.id.switchAtDevice);
        swNewFunction = findViewById(R.id.switchNewFunctions);
    }

    private void addEvents() {
        swBySomeone.setOnCheckedChangeListener(this);
        swAtDevice.setOnCheckedChangeListener(this);
        swNewFunction.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isFirstChange) {
            pushNotificationSetting();
        }
    }

    @Override
    public void initPresenter() {
        mPresenter = new NotificationSettingPresenter(this, this);
        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.getNotificationSetting();
    }

    private void pushNotificationSetting() {
        NotificationSettingRequest settingRequest = new NotificationSettingRequest();
        settingRequest.setAdminActive(swNewFunction.isChecked());
        settingRequest.setDiaryActive(swBySomeone.isChecked());
        settingRequest.setIoTAActive(swAtDevice.isChecked());

        showLoadingDialog(getString(R.string.message_please_wait));
        mPresenter.postNotificationSetting(settingRequest);
    }

    @Override
    public void getNotificationSettingSuccess(NotificationSettingReponse.Data data) {
        hideLoadingDialog();
        if (data != null) {
            swBySomeone.setChecked(data.isDiaryActive());
            swAtDevice.setChecked(data.isIoTAActive());
            swNewFunction.setChecked(data.isAdminActive());
        }
        isFirstChange = false;
    }

    @Override
    public void getNotificationSettingFail(String error) {
        hideLoadingDialog();
        if (error != null) {
            showSimpleMessage(error);
        }
        isFirstChange = false;
    }

    @Override
    public void postNotificationSettingSuccess() {
        hideLoadingDialog();
        Toast.makeText(this, getString(R.string.toast_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postNotificationSettingFail(String error) {
        hideLoadingDialog();
        showSimpleMessage(error);
    }

    @Override
    public void tokenExpired() {
        hideLoadingDialog();
        Utils.tokenExpired(this);
    }
}
