package com.ekakashi.greenhouse.features.register;

/**
 * Created by nquochuy on 11/29/2017.
 */

public interface RegisterInterface {
    interface View{
        void initPresenter();

        void showDialog(String email);
    }

    interface Presenter{
        void onDestroy();

        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);

        void doSendRequest(String email);
    }
}
