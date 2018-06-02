package com.ekakashi.greenhouse.features.notification_setting;

import com.ekakashi.greenhouse.database.model_server_request.NotificationSettingRequest;
import com.ekakashi.greenhouse.database.model_server_response.NotificationSettingReponse;

/**
 * Created by nquochuy on 2/26/2018.
 */

public interface NotificationSettingInterface {

    interface View {
        void initPresenter();

        void getNotificationSettingSuccess(NotificationSettingReponse.Data data);

        void getNotificationSettingFail(String error);

        void postNotificationSettingSuccess();

        void postNotificationSettingFail(String error);

        void tokenExpired();
    }

    interface Presenter {
        void getNotificationSetting();

        void postNotificationSetting(NotificationSettingRequest settingRequest);

        void onDestroy();
    }
}
