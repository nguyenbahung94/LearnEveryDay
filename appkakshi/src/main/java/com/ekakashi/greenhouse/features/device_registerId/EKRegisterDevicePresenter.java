package com.ekakashi.greenhouse.features.device_registerId;


import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.database.dao.DeviceObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EKRegisterDevicePresenter extends BasePresenter implements EKRegisterDeviceInterface.Presenter {
    private EKRegisterDeviceInterface.View view;

    public EKRegisterDevicePresenter(EKRegisterDeviceInterface.View view) {
        this.view = view;
    }

    @Override
    public void onRegisterDevice( DeviceObject sensor) {
        APIManager.registerDevice( sensor, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                ResponseBody error = response.errorBody();
                if (error != null) {
                    view.onRegisterFail(getErrorMessage(error));
                    return;
                }
                view.onRegisterSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                view.onRegisterFail(t.getMessage());
            }
        });
    }
}
