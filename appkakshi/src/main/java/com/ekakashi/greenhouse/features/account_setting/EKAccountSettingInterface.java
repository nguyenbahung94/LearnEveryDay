package com.ekakashi.greenhouse.features.account_setting;


import com.ekakashi.greenhouse.database.dao.Account;
import com.ekakashi.greenhouse.database.dao.AccountDao;

import java.io.File;

public interface EKAccountSettingInterface {
    interface View {

        AccountDao getAccountDao();

        Account getUser();

        void getAccountSuccess(Account account);

        void getAccountFail();

        void onUpdateSuccess();

        void onExpired();

        void onUpdateFail(String errorMessage);
    }

    interface Presenter {
        void onDestroy();

        boolean isEmailValid(String email);

        void doSendRequest(String firstName, String email, String nickName, File fileURL);

        void getAccountUser();
    }
}
