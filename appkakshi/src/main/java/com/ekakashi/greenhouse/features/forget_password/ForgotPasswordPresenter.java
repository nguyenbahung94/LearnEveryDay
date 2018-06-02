package com.ekakashi.greenhouse.features.forget_password;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordPresenter extends BasePresenter implements ForgotPasswordInterface.Presenter {

    private ForgotPasswordInterface.View mView;

    ForgotPasswordPresenter(ForgotPasswordInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void doSendRequest(final String email) {
        APIManager.resetPassword(email, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.showDialog(email, false);
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.showDialog(errorMessage, true);
                        } else {
                            mView.showDialog(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()), true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.showDialog(t.getMessage(), true);
                }
            }
        });

    }
}
