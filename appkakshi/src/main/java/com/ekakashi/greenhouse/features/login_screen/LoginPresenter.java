package com.ekakashi.greenhouse.features.login_screen;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.model_server_response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ekakashi.greenhouse.common.BaseResponse.messageResponse;

public class LoginPresenter extends BasePresenter implements LoginInterface.Presenter {

    private LoginInterface.View mLoginActivityCallback;

    LoginPresenter(LoginInterface.View callback) {
        mLoginActivityCallback = callback;
    }

    @Override
    public void onDestroy() {
        mLoginActivityCallback = null;
    }

    @Override
    public boolean isEmailValid(String email) {
        String emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        return email.matches(emailPattern);
    }

    @Override
    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void doLoginByEmail(String email, String password) {
        APIManager.login(email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (mLoginActivityCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null) {
                            mLoginActivityCallback.loginSuccessful(response.body());
                        } else {
                            mLoginActivityCallback.loginFail(messageResponse(0));
                        }
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mLoginActivityCallback.loginFail(errorMessage);
                        } else {
                            mLoginActivityCallback.loginFail(messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                if (mLoginActivityCallback != null) {
                    mLoginActivityCallback.loginFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getAccountUser() {
        APIManager.getUserAccount( new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                if (mLoginActivityCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        Account user = response.body();
                        if (user != null) {
                            mLoginActivityCallback.getAccountDao().insertOrUpdate(user);
                            mLoginActivityCallback.onGetAccountSuccess();
                        } else {
                            mLoginActivityCallback.loginFail(messageResponse(0));
                        }
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mLoginActivityCallback.loginFail(errorMessage);
                        } else {
                            mLoginActivityCallback.loginFail(messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                if (mLoginActivityCallback != null) {
                    mLoginActivityCallback.loginFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void sendDeviceToken(Context applicationContext, String deviceToken) {
        APIManager.sendRegistrationToServer( deviceToken, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mLoginActivityCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mLoginActivityCallback.onSendDeviceTokenSuccess();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mLoginActivityCallback.onSendDeviceTokenFail(errorMessage);
                        } else {
                            mLoginActivityCallback.onSendDeviceTokenFail(messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mLoginActivityCallback != null) {
                    mLoginActivityCallback.onSendDeviceTokenFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void doLoginByTwitter() {

    }

    @Override
    public void doLoginByFacebook() {

    }

    @Override
    public void doLoginByGoogle() {

    }
}
