package com.ekakashi.greenhouse.features.edit_place_name;

import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;

/**
 * Created by nbhung on 2/7/2018.
 */

public interface EKEditNameInterface {
    interface View {
        void success();
        void tokenExpired();
        void failed(String message);
    }

    interface presenter {
        void updateName(ObjectCreateField createField);
    }
}
