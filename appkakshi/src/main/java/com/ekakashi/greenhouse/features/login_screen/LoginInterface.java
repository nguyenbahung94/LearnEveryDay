package com.ekakashi.greenhouse.features.login_screen;

import android.content.Context;

import com.ekakashi.greenhouse.database.dao.AccountDao;
import com.ekakashi.greenhouse.database.model_server_response.LoginResponse;

/**
 * Created by paduy on 11/7/2017.
 */

public interface LoginInterface {
    interface View {

        AccountDao getAccountDao();

        void loginSuccessful(LoginResponse response);

        void loginFail(String error);

        void onGetAccountSuccess();

        void initPresenter();

        void showRegisterScreen();

        void showForgotPasswordScreen();

        void onSendDeviceTokenSuccess();

        void onSendDeviceTokenFail(String error);
    }

    interface Presenter {
        void onDestroy();

        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);

        void doLoginByEmail(String email, String password);

        void getAccountUser();

        void sendDeviceToken(Context applicationContext, String deviceToken);

        void doLoginByTwitter();

        void doLoginByFacebook();

        void doLoginByGoogle();
    }
}
