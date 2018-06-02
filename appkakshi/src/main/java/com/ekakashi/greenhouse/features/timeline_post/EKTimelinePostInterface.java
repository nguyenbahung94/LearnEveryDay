package com.ekakashi.greenhouse.features.timeline_post;

import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_post.model.ImageViewer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nquochuy on 1/15/2018.
 */

public interface EKTimelinePostInterface {
    interface View {

        void onWorkplaceSuccess(ArrayList<FilterField> list);

        void onWorkplaceFail();

        void diaryPostSuccess();

        void diaryPostFail(String message);

        void diaryUpdateSuccess();

        void diaryUpdateFail(String message);

        void tokenExpired();
    }

    interface Presenter {
        List<String> getListImageStringWith(ArrayList<ImageViewer> images, ImageViewer.ImageType type);

        void getWorkplace(String token);

        void createDiary(String workDescription, int workPlace, Integer workTypeId, String workTypeName, String targetCrop,
                         String diseaseType, String pesticideType, Integer fertilizerId,
                         String fertilizerType, String N, String P, String K, String issuedDate, String imageUrl1, String imageUrl2, String imageUrl3);

        void updateDiary(int diaryId, String workDescription, int workPlace,
                         Integer workTypeId, String workTypeName, String targetCrop,
                         String diseaseType, String pesticideType, Integer fertilizerId,
                         String fertilizerType, String N, String P, String K, String issuedDate, String imageList, String imageUrl1, String imageUrl2, String imageUrl3);

        String convertImageArrayToString(List<String> list);
    }
}
