package com.ekakashi.greenhouse.features.view_field;

import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.database.model_server_response.ObjectWeatherResponse;
import com.ekakashi.greenhouse.database.model_server_response.ResponseDetailField;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.weather.object_weather.ObjectDataForWidgetGraph;
import com.ekakashi.greenhouse.features.weather.object_weather.TemplateRecipeOnMainField;

import java.util.List;


public interface EKMainFieldFragmentInterface {
    interface View {
        void getListFieldSuccess(List<FilterField> list);

        void success(ObjectWeatherResponse objectWeatherResponse);

        void getListFieldFailed(String error);

        void getDetailFieldSuccess(ResponseDetailField responseDetailField);

        void getDetailFailed(String error);

        void changeStageSuccess(ChangeStageRecipeResponse changeStageRecipeResponse);

        void changeStageFailed(String message);

        void getWeatherDataSuccess(ObjectDataForWidgetGraph templateRecipe);

        void getWeatherDataFailed(String error);

        void getTemplateRecipeSuccess(List<Data> listdata);

        void getTemplateRecipeFailed(String error);

        void tokenExpired();
    }


    interface Presenter {
        void getDetailField(int id);

        void getListFields();

        void onDestroy();

        void goToNextState(int ekFieldId, int recipeId, int growingStageId);

        void getWeatherData(String id);

        void getTemplateRecipe(String id);
    }
}
