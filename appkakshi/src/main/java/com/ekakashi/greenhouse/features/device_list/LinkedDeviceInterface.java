package com.ekakashi.greenhouse.features.device_list;


import com.ekakashi.greenhouse.database.dao.DeviceObject;

import java.util.List;

public interface LinkedDeviceInterface {

    interface View{
        void onGetListSuccess(List<DeviceObject> list);
        void onGetListError(String errorMessage);
    }

    interface Presenter{
        void getList( int id);
    }


    interface OnItemClick{
        void onItemClick(android.view.View view, int position);

        void onItemLongClickListener(android.view.View view, int position);

        void clickDeleteOk(int position);

        void clickDeleteCancel(int position);
    }
}
