package com.ekakashi.greenhouse.features.add_field_greenhouse.step4;

import com.ekakashi.greenhouse.database.model_server_response.CloneRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectCreateField;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRecipe;

public interface EKAddFieldStepFourInterface {
    interface View {
        void success();

        void failed(String failed);

        void tokenExpired();

    }


    interface Presenter {
        void onDestroy();

        void addField(ObjectCreateField createField);

    }
}
