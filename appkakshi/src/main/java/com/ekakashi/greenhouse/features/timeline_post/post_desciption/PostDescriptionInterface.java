package com.ekakashi.greenhouse.features.timeline_post.post_desciption;

import com.ekakashi.greenhouse.database.model_server_response.FertilizerResponse;
import com.ekakashi.greenhouse.database.model_server_response.WorkTypeResponse;

import java.util.ArrayList;

public interface PostDescriptionInterface {

    interface View {
        void initPresenter();

        void onWorkTypeSuccess(ArrayList<WorkTypeResponse.WorkType> list);

        void onTargetCropSuccess(ArrayList<String> list);

        void onPesticideSuccess(ArrayList<String> list);

        void onFertilizerSuccess(ArrayList<FertilizerResponse.Fertilizer> list);

        void onDiseaseSuccess(ArrayList<String> list);

        void onWorkTypeFail();

        void onTargetCropFail();

        void onPesticideFail();

        void onFertilizerFail();

        void onDiseaseFail();

        void showLoadingDialog(String message);

        void hideLoadingDialog();

        void tokenExpired();

    }

    interface Presenter {
        void onDestroy();

        void getWorkType(int workPlaceId);

        void getTargetCrop(int workPlaceId);

        void getPesticide();

        void getFertilizer();

        void getDisease();
    }
}
