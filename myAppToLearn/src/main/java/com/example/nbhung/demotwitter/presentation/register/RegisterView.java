package com.example.nbhung.demotwitter.presentation.register;

import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.presentation.base.BaseView;

public interface RegisterView extends BaseView {
    void registerSuccess(String message);

    void registerFailed(ErrorType errorType);
}
