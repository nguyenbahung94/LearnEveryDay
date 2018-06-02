package com.ekakashi.greenhouse.features.view_information_field;

/**
 * Created by nbhung on 1/16/2018.
 */

public interface EKViewFieldInformationInterface {
    interface View {
        void failed(String str);

        void tokenExpired();

        void deleteSuccess();

        void deleteFailed();
    }


    interface presenter {
        void deleteField(int id);
    }
}
