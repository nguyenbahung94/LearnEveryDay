package com.bat.firstcom.supervisorapp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.common.InputErrorType;
import com.bat.firstcom.supervisorapp.common.Prefix;
import com.bat.firstcom.supervisorapp.common.SharePref;
import com.bat.firstcom.supervisorapp.common.Strings;
import com.bat.firstcom.supervisorapp.common.Utils;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginData;
import com.bat.firstcom.supervisorapp.datalayer.model.LoginRequest;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.presentation.base.BaseActivity;
import com.bat.firstcom.supervisorapp.presentation.changepassword.ChangePasswordActivity;
import com.bat.firstcom.supervisorapp.presentation.features.FeaturesActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.spinnerProduct)
    AppCompatSpinner spinnerProduct;
    @BindView(R.id.edtPhone)
    AppCompatEditText edtPhone;
    @BindView(R.id.edtPassword)
    AppCompatEditText edtPassword;
    @BindView(R.id.parentLayout)
    ConstraintLayout parentLayout;
    @Inject
    SupAppDataRepository dataRepository;
    private List<String> brandList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInjector();
        setPresenter(new LoginPresenter(dataRepository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getPresenter().onTakeView(this);
        hideActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(brandList.size()==0) {
            getPresenter().getBrands();
        }
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    private void initProductSpinner(String[] brandList) {
        NonLastItemSpinnerAdapter adapter = new NonLastItemSpinnerAdapter(this
                , brandList, R.layout.product_spinner_row);
        adapter.setDropDownViewResource(R.layout.product_spinner_dropdown_row);
        spinnerProduct.setAdapter(adapter);
        spinnerProduct.setSelection(adapter.getCount());
    }

    @OnFocusChange(R.id.edtPhone)
    public void edtPhoneFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            edtPhone.setHint(Strings.EMPTY);
        } else {
            edtPhone.setHint(getString(R.string.mobile_or_email));
        }
    }

    @OnFocusChange(R.id.edtPassword)
    public void edtPasswordFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            edtPassword.setHint(Strings.EMPTY);
        } else {
            edtPassword.setHint(getString(R.string.password));
        }
        String restoredText = App.getInstance().sharedPreference()
                .getString(SharePref.USER_LOGIN_NAME, null);
        if (restoredText != null) {

        }
    }

    @OnClick(R.id.btnLogin)
    public void clickBtnLogin(View view) {
        App.getInstance().hideKeyboard(view);
        if(validateInput()) {
            LoginRequest loginRequest = new LoginRequest(edtPhone.getText().toString()
                    , edtPassword.getText().toString());
            getPresenter().login(spinnerProduct.getSelectedItemPosition() + 1, loginRequest);
        }
    }

    @OnClick(R.id.parentLayout)
    public void clickParentLayout(ConstraintLayout constraintLayout) {
        App.getInstance().hideKeyboard(constraintLayout);
        edtPhone.clearFocus();
        edtPassword.clearFocus();
    }

    @Override
    public void startFeaturesActivity() {
        Intent intent = new Intent(this, FeaturesActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveUserInfoToSharePref(LoginData loginData) {
        saveUserDataToSharePref(Prefix.TOKEN + loginData.getToken()
                , spinnerProduct.getSelectedItemPosition() + 1, loginData.getProfile().getName());
    }

    @Override
    public void startChangePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
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
    public void updateBrandSpinner(List<String> brandList) {
        brandList.add(getString(R.string.pickup_product));
        this.brandList = brandList;
        initProductSpinner(brandList.toArray(new String[brandList.size()]));
    }

    @Override
    public void showErrorDialog(ErrorType errorType) {
        snackbarHelper.show(parentLayout,Utils.getErrorMessageFrom(errorType));
    }

    private boolean validateInput() {
        return validateProduct() && validateUserName() && validatePassword();
    }

    private boolean validateUserName() {
        InputErrorType validateUserName = Utils.validateUserName(edtPhone.getText().toString());
        if (validateUserName == InputErrorType.EMPTY) {//username is not valid
            dialogHelper.showDialog(getString(R.string.username_is_empty));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        InputErrorType validatePassword = Utils.validatePassword(edtPassword.getText().toString());
        if (validatePassword == InputErrorType.EMPTY) {//username is not valid
            dialogHelper.showDialog(getString(R.string.password_is_empty));
            return false;
        }
        return true;
    }

    private boolean validateProduct() {
        String product = spinnerProduct.getSelectedItem().toString();
        if (product.equalsIgnoreCase(getString(R.string.pickup_product))) {//brand is not valid
            dialogHelper.showDialog(getString(R.string.product_is_invalid));
            return false;
        }
        return true;
    }

}
