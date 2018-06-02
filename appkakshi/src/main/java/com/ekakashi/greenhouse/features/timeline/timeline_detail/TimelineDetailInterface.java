package com.ekakashi.greenhouse.features.timeline.timeline_detail;

import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;

/**
 * Created by nquochuy on 4/4/2018.
 */

public interface TimelineDetailInterface {
    interface View {
        void initPresenter();

        void editDiary(TimelineResponse.ListTimeline timeline);

        void onDeleteSuccess();

        void onDeleteFail();

        void tokenExpired();
    }

    interface Presenter {
        void deleteDiary(String timelineId);
    }
}
