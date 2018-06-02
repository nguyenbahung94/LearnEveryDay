package com.ekakashi.greenhouse.features.device_confirmation;


public interface EKDeviceConfirmationInterface {
    interface View{
        void onRegisterSuccess();
        void onRegisterFail(String error);
    }

    interface Presenter{
        void onRegisterDevice(String token, String id);
    }
}
