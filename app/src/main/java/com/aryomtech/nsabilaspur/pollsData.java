package com.aryomtech.nsabilaspur;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class pollsData {

    private String polls_pic;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String option6;
    private int totalOptions;
    private String key;
    private long totalquestion;


    public pollsData() {
    }

    public pollsData(String polls_pic, String question, String option1, String option2, String option3, String option4, int totalOptions,String key,String option5,String option6,long totalquestion) {
        this.polls_pic = polls_pic;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.totalOptions = totalOptions;
        this.totalquestion=totalquestion;
        this.key=key;
        this.option5=option5;
        this.option6=option6;
    }

    public long getTotalquestion() {
        return totalquestion;
    }

    public void setTotalquestion(long totalquestion) {
        this.totalquestion = totalquestion;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getOption6() {
        return option6;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTotalOptions() {
        return totalOptions;
    }

    public void setTotalOptions(int totalOptions) {
        this.totalOptions = totalOptions;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPolls_pic() {
        return polls_pic;
    }

    public void setPolls_pic(String polls_pic) {
        this.polls_pic = polls_pic;
    }
}
