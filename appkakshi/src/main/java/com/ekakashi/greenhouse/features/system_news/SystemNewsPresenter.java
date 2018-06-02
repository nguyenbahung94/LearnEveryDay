package com.ekakashi.greenhouse.features.system_news;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 1/25/2018.
 */

public class SystemNewsPresenter extends BasePresenter implements SystemNewsInterface.Presenter {

    private SystemNewsInterface.View mView;

    public SystemNewsPresenter(SystemNewsInterface.View mView) {
        this.mView = mView;
    }

    @Override
    public void getNotification(int currentPage, int recordPerPage) {
        APIManager.getNotification(currentPage, recordPerPage, new Callback<SystemNews>() {
            @Override
            public void onResponse(@NonNull Call<SystemNews> call, @NonNull Response<SystemNews> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        SystemNews systemNews = response.body();
                        if (systemNews != null) {
                            mView.onNotificationSuccess(systemNews.getData());
                        } else {
                            mView.onNotificationSuccess(null);
                        }
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mView.onNotificationFail(errorMessage);
                        } else {
                            mView.onNotificationFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SystemNews> call, @NonNull Throwable t) {
                if (mView != null) {
                    mView.onNotificationFail(t.getMessage());
                }
            }
        });
    }


    @Override
    public void updateSystemNews() {
        APIManager.updateSystemNews(Prefs.getInstance(App.getsInstance()).getUserId(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.onUpdateSuccess();
                    } else {
                        mView.onUpdateFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mView != null) {
                    mView.onUpdateFail();
                }
            }
        });
    }
}
