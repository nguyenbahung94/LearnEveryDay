package com.bat.firstcom.supervisorapp.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tung phan on 7/25/17.
 */

public class Answer {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("Answer")
    @Expose
    public int Answer;
    @SerializedName("Reason")
    @Expose
    public String Reason;

    public Answer(int id, int Answer, String Reason) {
        this.id = id;
        this.Answer = Answer;
        this.Reason = Reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int Answer) {
        this.Answer = Answer;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

}
