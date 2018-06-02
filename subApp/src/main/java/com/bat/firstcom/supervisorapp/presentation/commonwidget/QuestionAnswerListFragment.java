package com.bat.firstcom.supervisorapp.presentation.commonwidget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.QuestionType;
import com.bat.firstcom.supervisorapp.datalayer.model.Answer;
import com.bat.firstcom.supervisorapp.datalayer.model.ReportDatum;
import com.bat.firstcom.supervisorapp.datalayer.model.QuestionDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Tung Phan on 03-Jul-17.
 */

public class QuestionAnswerListFragment extends Fragment {

    public static final String TAG = QuestionAnswerListFragment.class.getSimpleName();
    @BindView(R.id.questionAnswerLayout)
    LinearLayout questionAnswerLayout;
    private Unbinder unbinder;
    private int currentGroupId;
    private List<QuestionDatum> questionData;
    private List<ReportDatum> reportData;
    private QuestionType questionType;
    private List<QuestionAnswer> questionAnswers = new ArrayList<>(); //list question and answer view.
    private QAListFragmentListener listener;
    private AppCompatEditText edtSuggestion;
    private boolean hasSuggestionEdt;

    public void setHasSuggestionEdt(boolean hasSuggestionEdt){
        this.hasSuggestionEdt = hasSuggestionEdt;
    }

    public boolean hasSuggestionEdt(){
        return this.hasSuggestionEdt;
    }

    public static QuestionAnswerListFragment newInstance() {
        return new QuestionAnswerListFragment();
    }

    public AppCompatEditText getEdtSuggestion(){
        return edtSuggestion;
    }

    //empty the question and answer views array
    public void emptyQuestionAnswers(){
        this.questionAnswers.clear();
    }

    public boolean isAllCoachingAnswerChecked() {
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if (!questionAnswer.isCoachingAnswerPicked()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllCheckingAnswerChecked() {
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if (!questionAnswer.isCheckingAnswerPicked()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllAnswerExplained() {
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if (!questionAnswer.isAnswerExplained()) {
                return false;
            }
        }
        return true;
    }

    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        for (QuestionAnswer questionAnswer : questionAnswers) {
            answers.add(new Answer(questionAnswer.getQuestionId()
                    , questionAnswer.getAnswer()
                    , questionAnswer.getReason()));
        }
        return answers;
    }

    public void setCurrentGroupId(int currentGroupId) {
        this.currentGroupId = currentGroupId;
    }

    public void setQuestionData(List<QuestionDatum> questionData) {
        this.questionData = questionData;
    }

    public void setReportData(List<ReportDatum> reportData) {
        this.reportData = reportData;
    }

    public void setListener(QAListFragmentListener listener) {
        this.listener = listener;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_answer_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //TODO: this method is kinda long, apply design pattern to help it shoter later
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //using count to count the number of question on 1 fragment/screen
        int count = 1;
        if (questionType == QuestionType.COACHING_EDITABLE
                || questionType == QuestionType.CHECKING_EDITABLE) {
            for (QuestionDatum questionDatum : questionData) {
                if (questionDatum.getGroupId() == currentGroupId) {
                    final QuestionAnswer questionAnswer
                            = new QuestionAnswer(getContext(), questionType);
                    questionAnswer.setQuestion(count + ". " + questionDatum.getTitle());
                    questionAnswer.setQuestionId(questionDatum.getId());
                    questionAnswer.updatecheckingQandAAnswerVisibility(questionDatum.getHasNA());
                    questionAnswerLayout.addView(questionAnswer);
                    questionAnswers.add(questionAnswer);
                    count++;
                }
            }
        } else if (questionType == QuestionType.COACHING_UNEDITABLE
                || questionType == QuestionType.CHECKING_UNEDITABLE) {
            for (ReportDatum reportDatum : reportData) {
                if (reportDatum.getGroupId() == currentGroupId) {
                    final QuestionAnswer questionAnswer
                            = new QuestionAnswer(getContext(), questionType);
                    questionAnswer.setQuestion(count + ". " + reportDatum.getTitle());
                    questionAnswer.initViewDataForCoachingReport(reportDatum.getAnswers());
                    questionAnswerLayout.addView(questionAnswer);
                    questionAnswers.add(questionAnswer);
                    count++;
                }
            }
        }
        if(hasSuggestionEdt) {
            //add suggestion edittext
            AppCompatTextView suggestionHeader = new AppCompatTextView(getContext());
            suggestionHeader.setText(R.string.suggestion_text);
            suggestionHeader.setTypeface(suggestionHeader.getTypeface(), Typeface.BOLD);
            FrameLayout.LayoutParams tvLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                    , FrameLayout.LayoutParams.WRAP_CONTENT);
            edtSuggestion = new AppCompatEditText(getContext());
            FrameLayout.LayoutParams edtLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                    , (int) getResources().getDimension(R.dimen.suggestion_height));
            questionAnswerLayout.addView(suggestionHeader, tvLayoutParams);
            questionAnswerLayout.addView(edtSuggestion, edtLayoutParams);
        }
        listener.onLoadViewFinish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface QAListFragmentListener {
        void onLoadViewFinish();
    }
}
