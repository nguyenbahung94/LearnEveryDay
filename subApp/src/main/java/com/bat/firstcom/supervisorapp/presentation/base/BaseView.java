package com.bat.firstcom.supervisorapp.presentation.base;

import com.bat.firstcom.supervisorapp.common.ErrorType;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public interface BaseView {

    void hideLoading();

    void showLoading();

    void showErrorDialog(ErrorType errorType);

    BasePresenter getPresenter();
}
