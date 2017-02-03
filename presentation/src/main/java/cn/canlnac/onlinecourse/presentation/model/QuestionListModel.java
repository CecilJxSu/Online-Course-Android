package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 小测列表数据模型.
 */

public class QuestionListModel {
    private int total;

    private List<QuestionModel> questions;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }
}
