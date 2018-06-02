package com.ekakashi.greenhouse.features.timeline;

import android.content.Context;

import com.ekakashi.greenhouse.database.model_server_response.ChangeStageRecipeResponse;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterModel;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/2/2018.
 */

public interface TimelineInterface {
    interface View {
        void initPresenter();

        void TimelineResult(ArrayList<TimelineResponse.ListTimeline> list);

        void TimelineFail(String message);

        void moveToNextStageSuccess(ChangeStageRecipeResponse.Data data);

        void moveToNextStageFail(String message);

        void tokenExpired();
    }

    interface Presenter {
        void getTimeline(Context context, FilterModel filterModel);

        void moveToNextStage(int fieldId, int recipeId, int growingStageId);
    }

    interface RecentSearchClick {
        void onRecentSearchClick(String search_text);
    }

    interface TimelineAdapterCallback {
        void onDiaryEdit(TimelineResponse.ListTimeline timelineObject);

        void onReadMore(TimelineResponse.ListTimeline timeline);

        void onMoveToNextStage(int position);
    }
}
