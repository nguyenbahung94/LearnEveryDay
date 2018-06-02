package com.bat.firstcom.supervisorapp.presentation.features;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.common.IntentConstant;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.commonwidget.DialogHelper;
import com.bat.firstcom.supervisorapp.presentation.pstlist.PSTListActitivy;
import com.bat.firstcom.supervisorapp.presentation.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class FeaturesActivity extends BaseActivity<FeaturesPresenter> implements FeaturesView
        , DialogHelper.YesNoDialogListener {

    @BindView(R.id.btnCoaching)
    AppCompatButton btnCoaching;
    @BindView(R.id.btnChecking)
    AppCompatButton btnChecking;
    @BindView(R.id.tvLogout)
    AppCompatTextView tvLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPresenter(new FeaturesPresenter());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features_activity);
        getPresenter().onTakeView(this);
        hideActionBar();
        dialogHelper.setYesNoDialogListener(this);
    }

    @OnClick(R.id.btnCoaching)
    public void clickBtnCoaching() {
        startPSTListActivity(FlowType.COACHING);
    }

    @OnClick(R.id.btnChecking)
    public void clickBtnChecking() {
        startPSTListActivity(FlowType.CHECKING);
    }

    @OnClick(R.id.tvLogout)
    public void clickLogOut() {
        dialogHelper.displayYesNoDialog(getString(R.string.logout_description)
                , R.string.yes
                , R.string.no);
    }

    private void backToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void startPSTListActivity(FlowType flowType) {
        Intent intent = new Intent(this, PSTListActitivy.class);
        intent.putExtra(IntentConstant.FLOW_TYPE, flowType);
        startActivity(intent);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showErrorDialog(ErrorType errorType) {

    }

    @Override
    public void onYesButtonClick() {
        backToLoginScreen();
        clearUserDataInSharePref();
    }
}
