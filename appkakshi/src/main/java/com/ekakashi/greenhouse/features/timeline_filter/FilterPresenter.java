package com.ekakashi.greenhouse.features.timeline_filter;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 1/6/2018.
 */

public class FilterPresenter extends BasePresenter implements FilterInterface.Presenter {

    private FilterInterface.View mCallback;

    public FilterPresenter(FilterInterface.View mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onDestroy() {
        mCallback = null;
    }

    @Override
    public void callApiGetPlaceList() {
        APIManager.getFilterPlace(new Callback<ArrayList<FilterField>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilterField>> call, @NonNull Response<ArrayList<FilterField>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mCallback.onResponseGetPlaceListApi(response.body());
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mCallback.onFailGetPlaceApi(errorMessage);
                        } else {
                            mCallback.onFailGetPlaceApi(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilterField>> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onFailGetPlaceApi(t.getMessage());
                }
            }
        });

    }

    @Override
    public void callApiGetMemberList() {
        APIManager.getFilterMember(new Callback<ArrayList<FilterMember>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilterMember>> call, @NonNull Response<ArrayList<FilterMember>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        mCallback.onResponseGetMemberListApi(response.body());
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        String errorMessage = getErrorMessage(response.errorBody());
                        if (errorMessage != null && !errorMessage.isEmpty()) {
                            mCallback.onFailGetMemberApi(errorMessage);
                        } else {
                            mCallback.onFailGetMemberApi(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilterMember>> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onFailGetMemberApi(t.getMessage());
                }
            }
        });
    }

    @Override
    public void callApiGetCropList() {
        int fieldId = Prefs.getInstance(App.getsInstance()).getCurrentID_Field();
        if (fieldId != -1) {
            APIManager.getTargetCrop(fieldId, new Callback<ArrayList<String>>() {
                @Override
                public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                    if (mCallback != null) {
                        if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                            ArrayList<String> list = response.body();
                            mCallback.onResponseGetCropListApi(list);
                        } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mCallback.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mCallback.onFailGetCropApi(errorMessage);
                            } else {
                                mCallback.onFailGetCropApi(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                    if (mCallback != null) {
                        mCallback.onFailGetCropApi(t.getMessage());
                    }
                }
            });
        }
    }
}
