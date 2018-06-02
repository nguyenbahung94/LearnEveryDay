package com.ekakashi.greenhouse.features.timeline_post.post_desciption;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.database.model_server_response.FertilizerResponse;
import com.ekakashi.greenhouse.database.model_server_response.WorkTypeResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDescriptionPresenter implements PostDescriptionInterface.Presenter {

    private PostDescriptionInterface.View mCallback;
    private Context context;

    public PostDescriptionPresenter(Context context, PostDescriptionInterface.View mCallback) {
        this.mCallback = mCallback;
        this.context = context;
    }

    @Override
    public void onDestroy() {
        mCallback = null;
    }

    @Override
    public void getWorkType(int workPlaceId) {
        mCallback.showLoadingDialog(context.getString(R.string.loading));
        APIManager.getWorkType( workPlaceId, new Callback<WorkTypeResponse>() {
            @Override
            public void onResponse(Call<WorkTypeResponse> call, Response<WorkTypeResponse> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        WorkTypeResponse workTypeResponse = response.body();
                        ArrayList<WorkTypeResponse.WorkType> list = null;
                        if (workTypeResponse != null) {
                            list = workTypeResponse.getData();
                        }
                        mCallback.onWorkTypeSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onWorkTypeFail();
                    }
                    mCallback.hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<WorkTypeResponse> call, Throwable t) {
                if (mCallback != null) {
                    mCallback.onWorkTypeFail();
                    mCallback.hideLoadingDialog();
                }
            }
        });
    }

    @Override
    public void getTargetCrop(int workPlaceId) {
        mCallback.showLoadingDialog(context.getString(R.string.loading));
        APIManager.getTargetCrop( workPlaceId, new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ArrayList<String> list = response.body();
                        mCallback.onTargetCropSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onTargetCropFail();
                    }
                    mCallback.hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                if (mCallback != null) {
                    mCallback.onTargetCropFail();
                    mCallback.hideLoadingDialog();
                }
            }
        });
    }


    @Override
    public void getPesticide() {
        mCallback.showLoadingDialog(context.getString(R.string.loading));
        APIManager.getPesticide( new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ArrayList<String> list = response.body();
                        mCallback.onPesticideSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onPesticideFail();
                    }
                    mCallback.hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onPesticideFail();
                    mCallback.hideLoadingDialog();
                }
            }
        });
    }

    @Override
    public void getFertilizer() {
        mCallback.showLoadingDialog(context.getString(R.string.loading));
        APIManager.getFertilizer( new Callback<FertilizerResponse>() {
            @Override
            public void onResponse(@NonNull Call<FertilizerResponse> call, @NonNull Response<FertilizerResponse> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        FertilizerResponse fertilizerResponse = response.body();
                        ArrayList<FertilizerResponse.Fertilizer> list = null;
                        if (fertilizerResponse != null) {
                            list = fertilizerResponse.getFertilizers();
                        }
                        mCallback.onFertilizerSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onFertilizerFail();
                    }
                    mCallback.hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FertilizerResponse> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onFertilizerFail();
                    mCallback.hideLoadingDialog();
                }
            }
        });
    }

    @Override
    public void getDisease() {
        mCallback.showLoadingDialog(context.getString(R.string.loading));
        APIManager.getDisease( new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
                if (mCallback != null) {
                    if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                        ArrayList<String> list = response.body();
                        mCallback.onDiseaseSuccess(list);
                    } else if (response.code() == Utils.ErrorCode.ERROR_401) {
                        mCallback.tokenExpired();
                    } else {
                        mCallback.onDiseaseFail();
                    }
                    mCallback.hideLoadingDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                if (mCallback != null) {
                    mCallback.onDiseaseFail();
                    mCallback.hideLoadingDialog();
                }
            }
        });
    }
}
