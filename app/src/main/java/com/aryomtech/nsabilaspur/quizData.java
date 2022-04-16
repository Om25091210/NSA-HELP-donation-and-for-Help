package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class quizData {

    private String q_pic;
    private String question;
    private String correct;
    private String key;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String total;
    private String questionNo;
    private String submit;

    public quizData() {
    }

    public quizData(String q_pic, String question, String correct, String key, String option1, String option2, String option3, String option4,String total,String questionNo,String submit) {
        this.q_pic = q_pic;
        this.question = question;
        this.correct = correct;
        this.key = key;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.total = total;
        this.questionNo=questionNo;
        this.submit=submit;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQ_pic() {
        return q_pic;
    }

    public void setQ_pic(String q_pic) {
        this.q_pic = q_pic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }
}
