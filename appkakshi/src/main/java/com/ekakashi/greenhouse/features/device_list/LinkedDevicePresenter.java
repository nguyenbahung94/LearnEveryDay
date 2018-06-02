package com.ekakashi.greenhouse.features.device_list;


import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.database.dao.DeviceObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinkedDevicePresenter extends BasePresenter implements LinkedDeviceInterface.Presenter {
    private LinkedDeviceInterface.View view;

    LinkedDevicePresenter(LinkedDeviceInterface.View view) {
        this.view = view;
    }

    @Override
    public void getList(int id) {
        APIManager.getDevice( id, new Callback<List<DeviceObject>>() {
            @Override
            public void onResponse(@NonNull Call<List<DeviceObject>> call, @NonNull Response<List<DeviceObject>> response) {
                ResponseBody error = response.errorBody();
                if (error == null) {
                    view.onGetListSuccess(response.body());
                } else {
                    view.onGetListError(getErrorMessage(error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DeviceObject>> call, @NonNull Throwable t) {
                view.onGetListError(t.getMessage());
            }
        });
    }
}
