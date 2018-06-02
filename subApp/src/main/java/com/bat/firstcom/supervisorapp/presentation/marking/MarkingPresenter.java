package com.bat.firstcom.supervisorapp.presentation.marking;

import com.bat.firstcom.supervisorapp.common.ErrorType;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingReportResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CheckingResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.ReportDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingReportResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingRequest;
import com.bat.firstcom.supervisorapp.datalayer.model.CoachingResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.GetQuestionResponse;
import com.bat.firstcom.supervisorapp.datalayer.model.QuestionDatum;
import com.bat.firstcom.supervisorapp.datalayer.repository.SupAppDataRepository;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCheckingQuestions;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCheckingReport;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCoachingQuestions;
import com.bat.firstcom.supervisorapp.domain.usecases.GetCoachingReport;
import com.bat.firstcom.supervisorapp.domain.usecases.PostChecking;
import com.bat.firstcom.supervisorapp.domain.usecases.PostCoaching;
import com.bat.firstcom.supervisorapp.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tung Phan on 30-Jun-17.
 */

public class MarkingPresenter extends BasePresenter<MarkingView> {

    private static final String TAG = MarkingPresenter.class.getSimpleName();
    private final int LACK_OF_BAID = 100;
    private final int NOT_ENOUGH_ANSWER_OR_ANSWER_IS_INVALID = 101;
    private final int NO_ERROR_CODE = 0;
    private GetCoachingQuestions getCoachingQuestions;
    private GetCheckingQuestions getCheckingQuestions;
    private PostCoaching postCoaching;
    private PostChecking postChecking;
    private GetCoachingReport getCoachingReport;
    private GetCheckingReport getCheckingReport;

    @Inject
    public MarkingPresenter(SupAppDataRepository dataRepository) {
        getCoachingQuestions = new GetCoachingQuestions(dataRepository);
        getCheckingQuestions = new GetCheckingQuestions(dataRepository);
        postCoaching = new PostCoaching(dataRepository);
        postChecking = new PostChecking(dataRepository);
        getCoachingReport = new GetCoachingReport(dataRepository);
        getCheckingReport = new GetCheckingReport(dataRepository);
    }

    void getCoachingQuestions(String token, int brand) {
        getView().showLoading();
        disposable.add(getCoachingQuestions.param(GetCoachingQuestions.Params
                .forGetCoachingQuestions(token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCoachingQuestionsObserver()));
    }

    //Important: if checking questions response data is difference, use another Observer
    void getCheckingQuestions(String token, int brand) {
        getView().showLoading();
        disposable.add(getCheckingQuestions.param(GetCheckingQuestions.Params
                .forGetCheckingQuestions(token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCoachingQuestionsObserver()));
    }

    void postCoaching(String token, int brand, CoachingRequest coachingRequest) {
        getView().showLoading();
        disposable.add(postCoaching.param(PostCoaching.Params
                .forPostCoaching(token, brand, coachingRequest))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new PostCoachingObserver()));
    }

    void postChecking(String token, int brand, CheckingRequest checkingRequest) {
        getView().showLoading();
        disposable.add(postChecking.param(PostChecking.Params
                .forPostChecking(token, brand, checkingRequest))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new PostCheckingObserver()));
    }

    void getCoachingReport(String id, String token, int brand) {
        getView().showLoading();
        disposable.add(getCoachingReport
                .param(GetCoachingReport.Params.forGetCoachingReport(id, token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCoachingReportObserver()));
    }

    void getCheckingReport(String id, String token, int brand) {
        getView().showLoading();
        disposable.add(getCheckingReport
                .param(GetCheckingReport.Params.forGetCheckingReport(id, token, brand))
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetCheckingReportObserver()));
    }

    private final class GetCoachingQuestionsObserver extends DisposableObserver<GetQuestionResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_QUESTION_LIST);
        }

        @Override
        public void onNext(GetQuestionResponse response) {
            List<QuestionDatum> questionData = response.getQuestionData();
            if (response.getSuccess() && questionData.size() > 0) {
                getView().updateQuestionList(questionData
                        , getSmallestGroupId(questionData)
                        , getBiggestGroupId(questionData));
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_QUESTION_LIST);
            }
        }

        private int getSmallestGroupId(List<QuestionDatum> questionData) {
            final int[] result = {questionData.get(0).getGroupId()};
            for (QuestionDatum questionDatum : questionData) {
                if (result[0] > questionDatum.getGroupId()) {
                    result[0] = questionDatum.getGroupId();
                }
            }
            return result[0];
        }

        private int getBiggestGroupId(List<QuestionDatum> questionData) {
            final int[] result = {questionData.get(0).getGroupId()};
            for (QuestionDatum questionDatum : questionData) {
                if (result[0] < questionDatum.getGroupId()) {
                    result[0] = questionDatum.getGroupId();
                }
            }
            return result[0];
        }
    }

    private final class PostCoachingObserver extends DisposableObserver<CoachingResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_COACHING);
        }

        @Override
        public void onNext(CoachingResponse response) {
            switch (response.getErrorCode()){
                case LACK_OF_BAID:
                    getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_COACHING_MISS_FIELD);
                    break;
                case NOT_ENOUGH_ANSWER_OR_ANSWER_IS_INVALID:
                    getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_COACHING_MISS_ANSWER);
                    break;
                case NO_ERROR_CODE:
                    if(response.isSuccess()) {
                        getView().postCoachingSuccessfully();
                    }else {
                        getView().showErrorDialog(ErrorType.ERROR_RESPONSE_POST_COACHING);
                    }
                    break;
            }
        }
    }

    private final class GetCoachingReportObserver extends DisposableObserver<CoachingReportResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_COACHING_REPORT);
        }

        @Override
        public void onNext(CoachingReportResponse response) {
            if (response.getSuccess()) {
                getView().updateReportListAdapter(response.getReportData()
                        , getSmallestGroupId(response.getReportData())
                        , getBiggestGroupId(response.getReportData()));
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_COACHING_REPORT);
            }
        }

        private int getSmallestGroupId(List<ReportDatum> reportData) {
            if (reportData.size() > 0) {
                final int[] result = {reportData.get(0).getGroupId()};
                for (ReportDatum reportDatum : reportData) {
                    if (result[0] > reportDatum.getGroupId()) {
                        result[0] = reportDatum.getGroupId();
                    }
                }
                return result[0];
            }
            return 0;
        }

        private int getBiggestGroupId(List<ReportDatum> reportData) {
            if (reportData.size() > 0) {
                final int[] result = {reportData.get(0).getGroupId()};
                for (ReportDatum reportDatum : reportData) {
                    if (result[0] < reportDatum.getGroupId()) {
                        result[0] = reportDatum.getGroupId();
                    }
                }
                return result[0];
            }
            return 0;
        }
    }

    private final class PostCheckingObserver extends DisposableObserver<CheckingResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_CHECKING);
        }

        @Override
        public void onNext(CheckingResponse response) {
            switch (response.getErrorCode()){
                case LACK_OF_BAID:
                    getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_CHECKING_MISS_FIELD);
                    break;
                case NOT_ENOUGH_ANSWER_OR_ANSWER_IS_INVALID:
                    getView().showErrorDialog(ErrorType.ERROR_REQUEST_POST_CHECKING_MISS_ANSWER);
                    break;
                case NO_ERROR_CODE:
                    if(response.getSuccess()) {
                        getView().postCheckingSuccessfully();
                    }else {
                        getView().showErrorDialog(ErrorType.ERROR_RESPONSE_POST_CHECKING);
                    }
                    break;
            }
        }
    }

    private final class GetCheckingReportObserver extends DisposableObserver<CheckingReportResponse> {

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorType.ERROR_REQUEST_CHECKING_REPORT);
        }

        @Override
        public void onNext(CheckingReportResponse response) {
            if (response.isSuccess()) {
                getView().updateReportListAdapter(response.getReportData()
                        , getSmallestGroupId(response.getReportData())
                        , getBiggestGroupId(response.getReportData()));
            } else {
                getView().showErrorDialog(ErrorType.ERROR_RESPONSE_CHECKING_REPORT);
            }
        }

        private int getSmallestGroupId(List<ReportDatum> reportData) {
            if (reportData.size() > 0) {
                final int[] result = {reportData.get(0).getGroupId()};
                for (ReportDatum reportDatum : reportData) {
                    if (result[0] > reportDatum.getGroupId()) {
                        result[0] = reportDatum.getGroupId();
                    }
                }
                return result[0];
            }
            return 0;
        }

        private int getBiggestGroupId(List<ReportDatum> reportData) {
            if (reportData.size() > 0) {
                final int[] result = {reportData.get(0).getGroupId()};
                for (ReportDatum reportDatum : reportData) {
                    if (result[0] < reportDatum.getGroupId()) {
                        result[0] = reportDatum.getGroupId();
                    }
                }
                return result[0];
            }
            return 0;
        }
    }
}
