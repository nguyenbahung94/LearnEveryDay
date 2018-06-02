package com.ekakashi.greenhouse.features.timeline;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.features.advice.AdviceDescriptionObject;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelinePresenter extends BasePresenter implements TimelineInterface.Presenter {

    private TimelineInterface.View mCallback;

    public TimelinePresenter(TimelineInterface.View mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void getTimeline(Context context, FilterModel filterModel) {
        APIManager.getTimeline(context, Utils.Name.TIMELINE_LOAD_MORE_RECORD, filterModel, new Callback<TimelineResponse>() {
            @Override
            public void onResponse(@NonNull Call<TimelineResponse> call, @NonNull Response<TimelineResponse> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        TimelineResponse timelineResponse = response.body();
                        if (timelineResponse != null) {
                            ArrayList<TimelineResponse.ListTimeline> list = timelineResponse.getListTimeline();

                            Gson gson = new GsonBuilder().setLenient().create();
                            AdviceDescriptionObject descriptionObject;
                            int firstIndex;

                            for (TimelineResponse.ListTimeline timeline : list) {
                                if (timeline.getTimelineType() == Utils.Constant.TIMELINE_TYPE_ADVICE) {

                                    try {
                                        descriptionObject = gson.fromJson(timeline.getDescription(), AdviceDescriptionObject.class);
                                        String stringImage = descriptionObject.getImages();
                                        firstIndex = stringImage.indexOf("base64") + 7;
                                        if (firstIndex != -1) {
                                            descriptionObject.setImages(stringImage.substring(firstIndex));
                                        }
                                    } catch (Exception ex) {
                                        descriptionObject = null;
                                    }
                                    timeline.setDescriptionObject(descriptionObject);
                                }
                            }

                            mCallback.TimelineResult(list);
                        } else {
                            mCallback.TimelineResult(null);
                        }
                    } else {
                        if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mCallback.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mCallback.TimelineFail(errorMessage);
                            } else {
                                mCallback.TimelineFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TimelineResponse> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.TimelineFail(t.getMessage());
                }
            }
        });
    }

    @Override
    public void moveToNextStage(int fieldId, int recipeId, int growingStageId) {
        APIManager.changeStageRecipe(fieldId, recipeId, growingStageId, new Callback<ChangeStageRecipeResponse>() {
            @Override
            public void onResponse(Call<ChangeStageRecipeResponse> call, Response<ChangeStageRecipeResponse> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ChangeStageRecipeResponse changeStageRecipeResponse = response.body();
                        if (changeStageRecipeResponse != null) {
                            mCallback.moveToNextStageSuccess(changeStageRecipeResponse.getData());
                        } else {
                            mCallback.moveToNextStageFail(App.getsInstance().getString(R.string.cannot_go_to_next_stage));
                        }
                    } else {
                        if (response.code() == Utils.Constant.ERROR_401) {
                            mCallback.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mCallback.moveToNextStageFail(errorMessage);
                            } else {
                                mCallback.moveToNextStageFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangeStageRecipeResponse> call, Throwable t) {

            }
        });
    }
}
