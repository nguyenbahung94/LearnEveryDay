package com.ekakashi.greenhouse.features.device_confirmation;


import com.ekakashi.greenhouse.common.BasePresenter;

public class EKDeviceConfirmationPresenter extends BasePresenter implements EKDeviceConfirmationInterface.Presenter {
    private EKDeviceConfirmationInterface.View view;

    EKDeviceConfirmationPresenter(EKDeviceConfirmationInterface.View view) {
        this.view = view;
    }

    @Override
    public void onRegisterDevice(String token, String id) {

    }
}
