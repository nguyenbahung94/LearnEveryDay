package com.ekakashi.greenhouse.features.system_news;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/25/2018.
 */

public interface SystemNewsInterface {

    interface View {
        void initPresenter();

        void onNotificationSuccess(ArrayList<SystemNews.Data> list);

        void onNotificationFail(String message);

        void onUpdateSuccess();

        void onUpdateFail();

        void tokenExpired();
    }

    interface Presenter {

        void updateSystemNews();

        void getNotification(int currentPage, int recordPerPage);
    }
}
