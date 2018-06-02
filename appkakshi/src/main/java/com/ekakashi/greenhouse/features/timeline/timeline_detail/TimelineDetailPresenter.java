package com.ekakashi.greenhouse.features.timeline.timeline_detail;

import android.support.annotation.NonNull;

import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.common.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nquochuy on 4/4/2018.
 */

public class TimelineDetailPresenter implements TimelineDetailInterface.Presenter {

    private TimelineDetailInterface.View mCallback;

    public TimelineDetailPresenter(TimelineDetailInterface.View mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void deleteDiary(String timelineId) {
        APIManager.deleteDiary(timelineId, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                    mCallback.onDeleteSuccess();
                } else
                    mCallback.onDeleteFail();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                mCallback.onDeleteFail();
            }
        });
    }
}
