package com.ekakashi.greenhouse.features.recipe.rule.add_rule;

import com.ekakashi.greenhouse.database.model_server_response.BaseResponse;

import okhttp3.MultipartBody;

/**
 * Created by ptloc on 2/3/2018.
 */

public class AddActionInterface {
    interface View {
        void success(BaseResponse response);

        void failed(String string);

        void tokenExpired();
    }

    interface presenter {
        void uploadImage(MultipartBody.Part imageFile);
    }
}
