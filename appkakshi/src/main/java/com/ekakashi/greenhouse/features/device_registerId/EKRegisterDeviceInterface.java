package com.ekakashi.greenhouse.features.device_registerId;


import com.ekakashi.greenhouse.database.dao.DeviceObject;

public interface EKRegisterDeviceInterface {

    interface View{
        void onRegisterSuccess();
        void onRegisterFail(String message);
    }

    interface Presenter{
        void onRegisterDevice(DeviceObject sensor);
    }
}
