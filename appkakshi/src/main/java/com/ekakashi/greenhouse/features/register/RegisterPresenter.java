package com.ekakashi.greenhouse.features.register;

/**
 * Created by nquochuy on 11/29/2017.
 */

public class RegisterPresenter implements RegisterInterface.Presenter {

    private RegisterInterface.View mView;

    public RegisterPresenter(RegisterInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    public void doSendRequest(String email) {
        mView.showDialog(email);
    }
}
