package com.ekakashi.greenhouse.features.devices_detail;

import com.ekakashi.greenhouse.database.model_server_request.DeviceDetailRequest;
import com.ekakashi.greenhouse.database.model_server_response.DeviceDetaildResponse;

/**
 * Created by nbhung on 1/30/2018.
 */

public interface DevicesDetailsView {
    interface View {
        void getDetailById(DeviceDetaildResponse deviceDetaildResponse);

        void failed(String failed);

        void editDeviceSuccess(DeviceDetaildResponse deviceDetaildResponse);

        void tokenExpired();
    }

    interface Presenter {
        void getDetailById(String id);

        void editDeviceDetail(DeviceDetailRequest deviceDetailRequest);
    }
}
