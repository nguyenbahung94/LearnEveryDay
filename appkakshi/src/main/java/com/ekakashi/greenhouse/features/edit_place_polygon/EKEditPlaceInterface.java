package com.ekakashi.greenhouse.features.edit_place_polygon;

import com.ekakashi.greenhouse.database.model_server_request.EditFieldRequest;

public interface EKEditPlaceInterface {
    interface View {
        void success();

        void failed(String message);

        void tokenExpired();
    }

    interface Presenter {
        void updatePolygon(EditFieldRequest editFieldRequest);

        void onDestroy();
    }
}
