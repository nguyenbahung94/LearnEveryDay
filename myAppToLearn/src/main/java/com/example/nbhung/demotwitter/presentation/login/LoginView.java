package com.example.nbhung.demotwitter.presentation.login;

import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.presentation.base.BaseView;

public interface LoginView extends BaseView {
    void loginSuccess(String message);

    void loginFailed(ErrorType error);
}
