package com.ekakashi.greenhouse.features.timeline_filter;

import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/6/2018.
 */

public interface FilterInterface {
    interface View {
        void initPresenter();

        void onFailGetPlaceApi(String message);

        void onFailGetMemberApi(String message);

        void onFailGetCropApi(String message);

        void onResponseGetPlaceListApi(ArrayList<FilterField> list);

        void onResponseGetMemberListApi(ArrayList<FilterMember> list);

        void onResponseGetCropListApi(ArrayList<String> list);

        void tokenExpired();
    }

    interface Presenter {
        void onDestroy();

        void callApiGetPlaceList();

        void callApiGetMemberList();

        void callApiGetCropList();
    }

    interface UnselectedAll {
        void onUnselectedAllCallback();
    }

    interface AutoTurnBack {
        void onAutoTurnBack();
    }
}
