package com.ekakashi.greenhouse.features.weather.widget_list.template_default;

import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;

import java.util.List;

/**
 * Created by nbhung on 3/21/2018.
 */

public class TemplateDefaultInterface {
    interface View {
        void getListSuccess(List<Data> listData);

        void getListFailed(String error);

        void TokenExpired();
    }

    interface presenter {
        void getListTemplateDefault();
    }
}
