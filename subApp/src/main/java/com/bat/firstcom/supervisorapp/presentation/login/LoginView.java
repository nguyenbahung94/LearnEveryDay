package com.bat.firstcom.supervisorapp.presentation.login;

import com.bat.firstcom.supervisorapp.datalayer.model.LoginData;
import com.bat.firstcom.supervisorapp.presentation.base.BaseView;

import java.util.List;

/**
 * Created by Tung Phan on 5/27/2017.
 */
//TODO: thinking about reuse hideLoading, showLoading and showErrorDialog
public interface LoginView extends BaseView {

    void updateBrandSpinner(List<String> brandList);

    void startFeaturesActivity();

    void saveUserInfoToSharePref(LoginData loginData);

    void startChangePasswordActivity();

}
