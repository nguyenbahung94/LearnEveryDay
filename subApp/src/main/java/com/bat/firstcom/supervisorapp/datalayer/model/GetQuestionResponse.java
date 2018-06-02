package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tung Phan on 24-Jul-17.
 */

public class GetQuestionResponse {
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private List<QuestionDatum> questionData;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public  List<QuestionDatum>  getQuestionData() {
        if(questionData ==null){
            questionData = new ArrayList<>();
        }
        return questionData;
    }

    public void setQuestionData( List<QuestionDatum>  questionData) {
        this.questionData = questionData;
    }

}
