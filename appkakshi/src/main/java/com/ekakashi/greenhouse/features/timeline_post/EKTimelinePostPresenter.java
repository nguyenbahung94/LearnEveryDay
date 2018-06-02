package com.ekakashi.greenhouse.features.timeline_post;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.BasePresenter;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_post.model.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EKTimelinePostPresenter extends BasePresenter implements EKTimelinePostInterface.Presenter {

    private EKTimelinePostInterface.View mCallback;

    EKTimelinePostPresenter(EKTimelinePostInterface.View mView) {
        this.mCallback = mView;
    }

    public void onDestroy() {
        mCallback = null;
    }

    @Override
    public List<String> getListImageStringWith(ArrayList<ImageViewer> images, ImageViewer.ImageType type) {
        ArrayList<String> results = new ArrayList<>();
        for (ImageViewer item : images) {
            if ((type == null && item.getType() != ImageViewer.ImageType.Diary) || (type != null && type == item.getType())) {
                results.add(item.getUrl());
            }
        }
        return results;
    }

    @Override
    public void getWorkplace(String token) {
        APIManager.getFilterPlace(new Callback<ArrayList<FilterField>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<FilterField>> call, @NonNull Response<ArrayList<FilterField>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ArrayList<FilterField> list = response.body();
                        mCallback.onWorkplaceSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onWorkplaceFail();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<FilterField>> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onWorkplaceFail();
                }
            }
        });
    }

    @Override
    public void createDiary(String workDescription, int workPlace, Integer workTypeId, String workTypeName, String targetCrop,
                            String diseaseType, String pesticideType, Integer fertilizerId, String fertilizerType,
                            String N, String P, String K, String issuedDate, String imageUrl1, String imageUrl2, String imageUrl3) {
        APIManager.createDiary(workDescription, workPlace, workTypeId, workTypeName, targetCrop, diseaseType, pesticideType, fertilizerId, fertilizerType, N, P, K,
                issuedDate, imageUrl1, imageUrl2, imageUrl3, new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (mCallback != null) {
                            if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                                mCallback.diaryPostSuccess();
                            } else {
                                if (response.code() == Utils.ErrorCode.ERROR_401) {
                                    mCallback.tokenExpired();
                                } else {
                                    String errorMessage = getErrorMessage(response.errorBody());
                                    if (errorMessage != null && !errorMessage.isEmpty()) {
                                        mCallback.diaryPostFail(errorMessage);
                                    } else {
                                        mCallback.diaryPostFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        if (mCallback != null) {
                            mCallback.diaryPostFail(t.getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateDiary(int diaryId, String workDescription, int workPlace,
                            Integer workTypeId, String workTypeName, String targetCrop,
                            String diseaseType, String pesticideType, Integer fertilizerId, String fertilizerType,
                            String N, String P, String K, String issuedDate, String imageList, String imageUrl1, String imageUrl2, String imageUrl3) {
        APIManager.updateDiary(diaryId, workDescription, workTypeId, workTypeName, targetCrop, workPlace, diseaseType, pesticideType, fertilizerId, fertilizerType, N, P, K,
                issuedDate, imageList, imageUrl1, imageUrl2, imageUrl3, new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                            mCallback.diaryUpdateSuccess();
                        } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                            mCallback.tokenExpired();
                        } else {
                            String errorMessage = getErrorMessage(response.errorBody());
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                mCallback.diaryUpdateFail(errorMessage);
                            } else {
                                mCallback.diaryUpdateFail(com.ekakashi.greenhouse.common.BaseResponse.messageResponse(response.code()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        if (mCallback != null) {
                            mCallback.diaryUpdateFail(t.getMessage());
                        }
                    }
                });
    }

    @Override
    public String convertImageArrayToString(List<String> list) {
        String result = "";
        String comma = ",";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                result = result.concat(list.get(i));
                if (i < list.size() - 1) {
                    result = result.concat(comma);
                }
            }
        }
        return result;
    }
}
