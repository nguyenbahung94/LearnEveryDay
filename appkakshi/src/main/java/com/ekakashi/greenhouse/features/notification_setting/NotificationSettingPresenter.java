package com.ekakashi.greenhouse.features.notification_setting;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_request.NotificationSettingRequest;
import com.ekakashi.greenhouse.database.model_server_response.NotificationSettingReponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 2/26/2018.
 */

public class NotificationSettingPresenter extends BasePresenter implements NotificationSettingInterface.Presenter {

    private NotificationSettingInterface.View mView;
    private NotificationSettingActivity mActivity;

    public NotificationSettingPresenter(NotificationSettingActivity activity, NotificationSettingInterface.View mView) {
        this.mView = mView;
        this.mActivity = activity;
    }

    @Override
    public void getNotificationSetting() {
        if (mActivity == null) {
            return;
        }
        if (mActivity.isNetworkOffline()) {
            if (mView != null) {
                mView.getNotificationSettingFail(null);
            }
            return;
        }

        APIManager.getNotificationSetting( new Callback<NotificationSettingReponse>() {
            @Override
            public void onResponse(Call<NotificationSettingReponse> call, Response<NotificationSettingReponse> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        NotificationSettingReponse settingReponse = response.body();
                        if (settingReponse != null) {
                            NotificationSettingReponse.Data data = settingReponse.getData();
                            if (data != null) {
                                mView.getNotificationSettingSuccess(data);
                            } else {
                                if (settingReponse.getMessage() != null) {
                                    mView.getNotificationSettingFail(settingReponse.getMessage());
                                } else {
                                    mView.getNotificationSettingFail(getErrorMessage(response.errorBody()));
                                }
                            }
                        } else {
                            mView.getNotificationSettingFail(getErrorMessage(response.errorBody()));
                        }
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mView.tokenExpired();
                    } else
                        mView.getNotificationSettingFail(getErrorMessage(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<NotificationSettingReponse> call, Throwable t) {
                if (mView != null) {
                    mView.getNotificationSettingFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void postNotificationSetting(NotificationSettingRequest settingRequest) {
        if (mActivity == null) {
            return;
        }
        if (mActivity.isNetworkOffline()) {
            if (mView != null) {
                mView.getNotificationSettingFail(null);
            }
            return;
        }

        APIManager.postNotificationSetting( settingRequest, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (mView != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mView.postNotificationSettingSuccess();
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mView.tokenExpired();
                    } else {
                        mView.postNotificationSettingFail(getErrorMessage(response.errorBody()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mView != null) {
                    mView.postNotificationSettingFail(t.getMessage());
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
