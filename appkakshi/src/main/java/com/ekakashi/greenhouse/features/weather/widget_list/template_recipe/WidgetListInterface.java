package com.ekakashi.greenhouse.features.weather.widget_list.template_recipe;

import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;

import java.util.List;

/**
 * Created by nbhung on 3/13/2018.
 */

public interface WidgetListInterface {
    interface View {
        void getListSuccess(List<Data> listData);

        void getListFailed(String message);

        void updateSortNoSuccess();

        void tokenExpired();

    }

    interface Presenter {
        void getListWidget(String id);

        void updateSortNo(List<Data> dataList);
    }
}
