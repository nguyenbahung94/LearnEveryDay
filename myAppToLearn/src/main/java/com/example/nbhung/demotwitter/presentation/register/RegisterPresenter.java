package com.example.nbhung.demotwitter.presentation.register;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.nbhung.demotwitter.R;
import com.example.nbhung.demotwitter.common.ErrorType;
import com.example.nbhung.demotwitter.common.Strings;
import com.example.nbhung.demotwitter.datalayer.model.RegisterResponse;
import com.example.nbhung.demotwitter.datalayer.repository.TwitterAppDataRepository;
import com.example.nbhung.demotwitter.domain.usercases.Register;
import com.example.nbhung.demotwitter.presentation.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterView> {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    private Register register;

    @Inject
    protected RegisterPresenter(TwitterAppDataRepository twitterAppDataRepository) {
        register = new Register(twitterAppDataRepository);
    }

    void registerAccount(String userName, String email, String passWord, String confirmPassWord) {
        if (validateEmail(email) && validateUsername(userName) && validatePassWord(passWord, confirmPassWord)) {
            getView().showLoading();
            disposable.add(register.param(Register.Params.forRegister(email, userName, passWord, confirmPassWord))
                    .on(Schedulers.io(), AndroidSchedulers.mainThread())
                    .excute(new registerAObserver()));
        }
    }
    private boolean validatePassWord(String password, String confirmPassWord) {
        if (password.isEmpty()) {
            getView().registerFailed(ErrorType.ERROR_PASSWORD_EMPTY);
            return false;
        }
        if (confirmPassWord.isEmpty()) {
            getView().registerFailed(ErrorType.ERROR_CONFIRM_PASSWORD);
            return false;
        }
        if (!password.equals(confirmPassWord)) {
            getView().registerFailed(ErrorType.ERROR_PASSWORD_NOT_SAME);
            return false;
        }
        return true;
    }

    private boolean validateUsername(String userName) {
        if (userName.isEmpty()) {
            getView().registerFailed(ErrorType.ERROR_USERNAME_EMPTY);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            getView().registerFailed(ErrorType.ERROR_EMAIL_EMPTY);
            return false;
        }
        if (!email.contains("@")) {
            getView().registerFailed(ErrorType.ERROR_EMAIL_NOT_CONTAIN_A);
            return false;
        }
        return true;

    }

    private final class registerAObserver extends DisposableObserver<RegisterResponse> {

        @Override
        public void onNext(RegisterResponse registerResponse) {
            if (registerResponse != null) {
                getView().registerSuccess(Strings.success);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REGISTER);
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
