package com.bat.firstcom.supervisorapp.presentation.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Strings;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.ChangePasswordRequest;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements ChangePasswordView {

    @BindView(R.id.edtNewPassword)
    AppCompatEditText edtNewPassword;
    @BindView(R.id.edtRetypeNewPassword)
    AppCompatEditText edtRetypeNewPassword;
    @BindView(R.id.parentLayout)
    ConstraintLayout parentLayout;
    @Inject
    SupAppDataRepository dataRepository;
    private String token;
    private int brand;
    private Snackbar.Callback snackBarCallback = new Snackbar.Callback() {

        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            startLoginScreen();
        }

        @Override
        public void onShown(Snackbar snackbar) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new ChangePasswordPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        getPresenter().onTakeView(this);
        hideActionBar();
        getDataFromSharePref();
    }

    private void getDataFromSharePref() {
        token = App.getInstance().getStringFromSharePref(SharePref.USER_TOKEN);
        brand = App.getInstance().getIntFromSharePref(SharePref.BRAND);
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    @OnFocusChange(R.id.edtNewPassword)
    public void edtPhoneFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            edtNewPassword.setHint(Strings.EMPTY);
        } else {
            edtNewPassword.setHint(getString(R.string.hint_retype_new_password));
        }
    }

    @OnFocusChange(R.id.edtRetypeNewPassword)
    public void edtPasswordFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            edtRetypeNewPassword.setHint(Strings.EMPTY);
        } else {
            edtRetypeNewPassword.setHint(getString(R.string.hint_new_password));
        }
    }

    @OnClick(R.id.btnLogin)
    public void clickBtnLogin(View view) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setPassword(edtNewPassword.getText().toString());
        changePasswordRequest.setConfirmedPassword(edtRetypeNewPassword.getText().toString());
        getPresenter().postChangePassword(token, brand, changePasswordRequest);
    }

    @OnClick(R.id.parentLayout)
    public void clickParentLayout(ConstraintLayout constraintLayout) {
        App.getInstance().hideKeyboard(constraintLayout);
        edtNewPassword.clearFocus();
        edtRetypeNewPassword.clearFocus();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showLoading() {
        showLoadingDialog(getString(R.string.loading_in_progress));
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        snackbarHelper.show(parentLayout, Utils.getErrorMessageFrom(errorType));
    }

    @Override
    public void changePasswordSuccessfully() {
        clearUserDataInSharePref();
        //start login screen and clear activity top
        snackbarHelper.show(parentLayout
                , R.string.notify_change_password_succesfully
                , snackBarCallback);
    }

    private void startLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
