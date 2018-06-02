package com.ekakashi.greenhouse.features.setting;


import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingPresenter extends BasePresenter implements SettingInterface.Presenter {
    private SettingInterface.View mView;

    SettingPresenter(SettingInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void logout() {
        APIManager.logout(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onLogoutSuccess();
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            mView.onLogoutSuccess();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mView.onLogoutFailed(errorMessage);
                            } else {
                                mView.onLogoutFailed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mView != null) {
                    mView.onLogoutFailed(t.getMessage());
                }
            }
        });
    }
}
