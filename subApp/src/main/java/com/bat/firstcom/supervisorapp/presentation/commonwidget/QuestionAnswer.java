package com.bat.firstcom.supervisorapp.presentation.commonwidget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.App;
import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.QuestionType;
import com.bat.firstcom.supervisorapp.common.Strings;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Tung Phan on 03-Jul-17.
 */

public class QuestionAnswer extends LinearLayout {

    public final static int BAD = 1;
    public final static int AVERAGE = 2;
    public final static int GOOD = 3;
    public final static int NO = 0;
    public final static int YES = 1;
    public final static int NA = 2;
    private AppCompatTextView tvQuestion;
    private AppCompatButton btnBad;
    private AppCompatButton btnNormal;
    private AppCompatButton btnGood;
    private AppCompatEditText edtComment;
    private LinearLayout coachingAnswerWrapper;
    private RelativeLayout coachingResultWrapper;
    private RelativeLayout checkingAnswerWrapper;
    private AppCompatRadioButton radioBtnNA;
    private RadioGroup radioGroup;
    private LinearLayout reviewResultWrapper;
    private QuestionType questionType;
    private Context context;
    private RelativeLayout parentLayout;
    private boolean coachingAnswerPicked = false;//turn true if user click to 1 of 3 answer.
    private boolean checkingAnswerPicked = false;//turn true if user click to 1 of 2 radio buttons.
    private int questionId;
    //contain the answer list for one question, questionType == COACHING_UNEDITABLE
    //contain the answer id for one question
    private int answer;

    public boolean isCoachingAnswerPicked() {
        return coachingAnswerPicked;
    }

    public boolean isCheckingAnswerPicked() {
        return checkingAnswerPicked;
    }

    //check if the edtComment is visible then return false if it's empty
    public boolean isAnswerExplained() {
        return edtComment.getVisibility() != View.VISIBLE
                || !edtComment.getText().toString().equalsIgnoreCase(Strings.EMPTY);
    }

    public String getCoachingAnswerExplain() {
        return edtComment.getText().toString();
    }

    public QuestionAnswer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public QuestionAnswer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QuestionAnswer(Context context) {
        super(context);
    }

    public QuestionAnswer(Context context, QuestionType questionType) {
        this(context);
        this.context = context;
        this.questionType = questionType;
        initView();
    }

    public void setQuestion(String question) {
        tvQuestion.setText(question);
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void updatecheckingQandAAnswerVisibility(Integer hasNA) {
        if (hasNA == 1) {
            radioBtnNA.setVisibility(View.VISIBLE);
        } else {
            radioBtnNA.setVisibility(View.GONE);
        }
    }

    public int getQuestionId() {
        return this.questionId;
    }

    //return answer for one question
    public int getAnswer() {
        return this.answer;
    }

    public String getReason() {
        return edtComment.getText().toString();
    }

    private void initView() {
        initViewId();
        initListener();
        initViewsVisibility();
        parentLayout.setOnClickListener(v -> App.getInstance().hideKeyboard(v));
    }

    private void initViewsVisibility() {
        switch (questionType) {
            case COACHING_EDITABLE:
                setCoachingAnswerWrapperVisibility(View.VISIBLE);
                setCoachingResultWrapperVisibility(View.INVISIBLE);
                setCheckingAnswerWrapperVisibility(View.INVISIBLE);
                break;
            case COACHING_UNEDITABLE:
                setCoachingAnswerWrapperVisibility(View.INVISIBLE);
                setCoachingResultWrapperVisibility(View.VISIBLE);
                setCheckingAnswerWrapperVisibility(View.INVISIBLE);
                break;
            case CHECKING_EDITABLE:
                setCoachingAnswerWrapperVisibility(View.INVISIBLE);
                setCoachingResultWrapperVisibility(View.INVISIBLE);
                setCheckingAnswerWrapperVisibility(View.VISIBLE);
                break;
            case CHECKING_UNEDITABLE:
                setCoachingAnswerWrapperVisibility(View.INVISIBLE);
                setCoachingResultWrapperVisibility(View.VISIBLE);
                setCheckingAnswerWrapperVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    //using this method to mapping data into view in case: COACHING_UNEDITABLE
    public void initViewDataForCoachingReport(List<Integer> answers) {
        if (answers.size() > 0) {
            for (Integer integer : answers) {
                reviewResultWrapper.addView(createResultButton(integer));
            }
        }
    }

    private AppCompatButton createResultButton(int answer) {
        int buttonSize = (int) context.getResources().getDimension(R.dimen.small_button_size);
        AppCompatButton appCompatButton = new AppCompatButton(context);
        appCompatButton.setTextColor(ContextCompat.getColor(context, R.color.white));
        if (questionType == QuestionType.COACHING_UNEDITABLE) {
            LayoutParams params = new LayoutParams(buttonSize, buttonSize);
            params.setMargins(0, 0, (int) context.getResources().getDimension(R.dimen.margin_small), 0);
            appCompatButton.setLayoutParams(params);
            switch (answer) {
                case BAD:
                    appCompatButton.setText(context.getString(R.string.btn_bad_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.red_result_button_shape));
                    break;
                case AVERAGE:
                    appCompatButton.setText(context.getString(R.string.btn_normal_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.yellow_result_button_shape));
                    break;
                case GOOD:
                    appCompatButton.setText(context.getString(R.string.btn_good_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.green_result_button_shape));
                    break;
            }
        } else if (questionType == QuestionType.CHECKING_UNEDITABLE) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, buttonSize);
            params.setMargins(0, 0, (int) context.getResources().getDimension(R.dimen.margin_small), 0);
            appCompatButton.setLayoutParams(params);
            switch (answer) {
                case YES:
                    appCompatButton.setText(context.getString(R.string.btn_yes_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.green_result_button_shape));
                    break;
                case NO:
                    appCompatButton.setText(context.getString(R.string.btn_no_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.red_result_button_shape));
                    break;
                case NA:
                    appCompatButton.setText(context.getString(R.string.btn_na_text));
                    appCompatButton.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.green_result_button_shape));
                    break;
            }
        }
        return appCompatButton;
    }

    private void setCoachingAnswerWrapperVisibility(int visibility) {
        coachingAnswerWrapper.setVisibility(visibility);
    }

    private void setCoachingResultWrapperVisibility(int visibility) {
        coachingResultWrapper.setVisibility(visibility);
    }

    private void setCheckingAnswerWrapperVisibility(int visibility) {
        checkingAnswerWrapper.setVisibility(visibility);
    }

    private void initViewId() {
        View view = inflate(getContext(), R.layout.marking_question_answer, this);
        tvQuestion = ButterKnife.findById(view, R.id.tvQuestion);
        btnBad = ButterKnife.findById(view, R.id.btnBad);
        btnNormal = ButterKnife.findById(view, R.id.btnNormal);
        btnGood = ButterKnife.findById(view, R.id.btnGood);
        edtComment = ButterKnife.findById(view, R.id.edtComment);
        parentLayout = ButterKnife.findById(view, R.id.parentLayout);
        coachingAnswerWrapper = ButterKnife.findById(view, R.id.coachingAnswerWrapper);
        coachingResultWrapper = ButterKnife.findById(view, R.id.coachingResultWrapper);
        checkingAnswerWrapper = ButterKnife.findById(view, R.id.checkingAnswerWrapper);
        reviewResultWrapper = ButterKnife.findById(view, R.id.reviewResultWrapper);
        radioBtnNA = ButterKnife.findById(checkingAnswerWrapper, R.id.radioBtnNA);
        radioGroup = ButterKnife.findById(checkingAnswerWrapper, R.id.radioGroup);
    }

    private void initListener() {
        btnBad.setOnClickListener(v -> {
            answer = BAD;
            coachingAnswerPicked = true;
            edtComment.setVisibility(View.VISIBLE);
            btnBad.setBackground(ContextCompat.getDrawable(context, R.drawable.red_button_fill_shape));
            btnNormal.setBackground(ContextCompat.getDrawable(context, R.drawable.yellow_button_shape));
            btnGood.setBackground(ContextCompat.getDrawable(context, R.drawable.green_button_shape));
        });
        btnNormal.setOnClickListener(v -> {
            answer = AVERAGE;
            coachingAnswerPicked = true;
            edtComment.setVisibility(View.VISIBLE);
            btnBad.setBackground(ContextCompat.getDrawable(context, R.drawable.red_button_shape));
            btnNormal.setBackground(ContextCompat.getDrawable(context, R.drawable.yello_button_fill_shape));
            btnGood.setBackground(ContextCompat.getDrawable(context, R.drawable.green_button_shape));
        });
        btnGood.setOnClickListener(v -> {
            answer = GOOD;
            coachingAnswerPicked = true;
            edtComment.setVisibility(View.GONE);
            btnBad.setBackground(ContextCompat.getDrawable(context, R.drawable.red_button_shape));
            btnNormal.setBackground(ContextCompat.getDrawable(context, R.drawable.yellow_button_shape));
            btnGood.setBackground(ContextCompat.getDrawable(context, R.drawable.green_button_fill_shape));
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioBtnYes:
                    answer = YES;
                    edtComment.setVisibility(View.GONE);
                    break;
                case R.id.radioBtnNo:
                    answer = NO;
                    edtComment.setVisibility(View.VISIBLE);
                    break;
                case R.id.radioBtnNA:
                    answer = NA;
                    edtComment.setVisibility(View.GONE);
                    break;
            }
            checkingAnswerPicked = true;
        });
    }
}
