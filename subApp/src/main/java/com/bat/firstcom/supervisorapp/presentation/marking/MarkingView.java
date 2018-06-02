package com.bat.firstcom.supervisorapp.presentation.marking;

import com.bat.firstcom.supervisorapp.datalayer.model.ReportDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.QuestionDatum;
import com.bat.firstcom.supervisorapp.presentation.base.BaseView;

import java.util.List;

/**
 * Created by Tung Phan on 5/27/2017.
 */

public interface MarkingView extends BaseView {

    void postCoachingSuccessfully();

    void postCheckingSuccessfully();

    void updateQuestionList(List<QuestionDatum> questionData, int smallestGroupId, int biggestGroupId);

    void updateReportListAdapter(List<ReportDatum> reportData
            , int smallestGroupId, int biggestGroupId);


}
