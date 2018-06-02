package com.ekakashi.greenhouse.features.setting;


import com.ekakashi.greenhouse.database.dao.AccountDao;

public interface SettingInterface {
    interface View {
        AccountDao getAccountDao();

        void onLogoutSuccess();

        void onLogoutFailed(String errorMessage);
    }

    interface Presenter {
        void logout();
    }
}
