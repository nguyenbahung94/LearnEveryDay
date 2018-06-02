package com.ekakashi.greenhouse.features.forget_password;

/**
 * Created by nquochuy on 11/14/2017.
 */

public interface ForgotPasswordInterface {
    interface View {
        void initPresenter();

        void showDialog(String email, Boolean isError);
    }

    interface Presenter {
        void onDestroy();

        void doSendRequest(String email);
    }
}
