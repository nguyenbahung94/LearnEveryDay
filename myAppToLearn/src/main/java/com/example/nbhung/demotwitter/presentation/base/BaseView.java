package com.example.nbhung.demotwitter.presentation.base;

import com.example.nbhung.demotwitter.common.ErrorType;

public interface BaseView {
    void hideLoading();

    void showLoading();

    void showErrorDialog(ErrorType errorType);

    BasePresenter getPresenter();
}
