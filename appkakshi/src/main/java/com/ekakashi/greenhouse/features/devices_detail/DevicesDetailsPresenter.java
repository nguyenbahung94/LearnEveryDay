package com.ekakashi.greenhouse.features.devices_detail;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.database.model_server_request.DeviceDetailRequest;
import com.ekakashi.greenhouse.database.model_server_response.DeviceDetaildResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 1/30/2018.
 */

public class DevicesDetailsPresenter implements DevicesDetailsView.Presenter {
    private DevicesDetailsView.View view;

    public DevicesDetailsPresenter(DevicesDetailsView.View view) {
        this.view = view;
    }

    public void ondetroy() {
        view = null;
    }

    @Override
    public void getDetailById(String id) {
        APIManager.getDeviceDetail(id, new Callback<DeviceDetaildResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeviceDetaildResponse> call, @NonNull Response<DeviceDetaildResponse> response) {
                if (view != null) {
                    if (response.code() == 200) {
                        view.getDetailById(response.body());
                    } else {
                        if (response.code() == 500) {
                            view.tokenExpired();
                        } else {
                            view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeviceDetaildResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });

    }

    @Override
    public void editDeviceDetail(DeviceDetailRequest deviceDetailRequest) {
        APIManager.editDeviceDetail(deviceDetailRequest, new Callback<DeviceDetaildResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeviceDetaildResponse> call, @NonNull Response<DeviceDetaildResponse> response) {
                if (view != null) {
                    if (response.code() == 200) {
                        view.editDeviceSuccess(response.body());
                    } else {
                        if (response.code() == 500) {
                            view.tokenExpired();
                        } else
                            view.failed(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<DeviceDetaildResponse> call, @NonNull Throwable t) {
                if (view != null) {
                    view.failed(t.getMessage());
                }
            }
        });
    }
}
