package com.ekakashi.greenhouse.features.weather.widget_list.template_recipe;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.BaseResponse;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ObjectRequestUpdateSortNoTemplate;
import com.ekakashi.greenhouse.database.model_server_response.ObjectResponseUpdateSorNoTemplate;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.Data;
import com.ekakashi.greenhouse.database.model_server_response.template_recipe.TemplateRecipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nbhung on 3/13/2018.
 */

public class WidgetListPresenter extends BasePresenter implements WidgetListInterface.Presenter {
    private WidgetListInterface.View view;

    public WidgetListPresenter(WidgetListInterface.View view) {
        this.view = view;
    }

    public void onDetroy() {
        view = null;
    }

    @Override
    public void getListWidget(String id) {
        APIManager.getListTemplateRecipe("4667", new Callback<TemplateRecipe>() {
            @Override
            public void onResponse(@NonNull Call<TemplateRecipe> call, @NonNull Response<TemplateRecipe> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        if (response.body() != null && response.body().getData() != null) {
                            view.getListSuccess(response.body().getData());
                        } else {
                            view.getListFailed("empty data");
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                view.getListFailed(errorMessage);
                            } else {
                                view.getListFailed(BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<TemplateRecipe> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getListFailed(t.getMessage());
                }
            }
        });
    }

    @Override
    public void updateSortNo(List<Data> dataList) {
        int dem = 0;
        List<ObjectRequestUpdateSortNoTemplate.TemplateConfigList> listsort = new ArrayList<>();
        for (Data data : dataList) {
            dem++;
            listsort.add(new ObjectRequestUpdateSortNoTemplate.TemplateConfigList(dem, data.getId()));
        }
        ObjectRequestUpdateSortNoTemplate sortNoTemplate = new ObjectRequestUpdateSortNoTemplate(2909, listsort);
        APIManager.updateSortTemplate(sortNoTemplate, new Callback<ObjectResponseUpdateSorNoTemplate>() {
            @Override
            public void onResponse(@NonNull Call<ObjectResponseUpdateSorNoTemplate> call, @NonNull Response<ObjectResponseUpdateSorNoTemplate> response) {
                if (view != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        view.updateSortNoSuccess();
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            view.tokenExpired();
                        } else {
                            String message = getErrorMessage(response.errorBody());
                            if (message != null && !message.isEmpty()) {
                                view.getListFailed(message);
                            } else {
                                view.getListFailed(BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ObjectResponseUpdateSorNoTemplate> call, @NonNull Throwable t) {
                if (view != null) {
                    view.getListFailed(t.getMessage());
                }
            }
        });
    }
}
